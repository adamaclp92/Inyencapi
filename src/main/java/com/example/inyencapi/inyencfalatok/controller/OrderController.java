package com.example.inyencapi.inyencfalatok.controller;

import com.example.inyencapi.inyencfalatok.dto.GetOrderByOrderIdResponseBodyDto;
import com.example.inyencapi.inyencfalatok.dto.PostNewOrderResponseBodyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.inyencapi.inyencfalatok.dto.PostNewOrderRequestBodyDto;
import com.example.inyencapi.inyencfalatok.kafka.KafkaProducer;

import jakarta.validation.Valid;
import org.springframework.web.context.request.async.DeferredResult;

@RequestMapping("/api")
@RestController
public class OrderController {

	private final KafkaProducer kafkaProducer;

	public OrderController(KafkaProducer producer) {
		this.kafkaProducer = producer;
		
	}
	
	@PostMapping("/orders")
	public DeferredResult<ResponseEntity<?>> postNewOrder(@RequestBody @Valid PostNewOrderRequestBodyDto body) throws Exception{
		DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>(5000L);
		kafkaProducer.postNewOrderRequest(body, deferredResult);
		deferredResult.onError(throwable -> {
			deferredResult.setErrorResult(new Exception("An error occurred while processing the order: " + throwable.getMessage()));
		});

		deferredResult.onTimeout(() -> {
			deferredResult.setErrorResult(new Exception("Request timeout"));
		});


		return deferredResult;
	}

	@GetMapping("/orders/{orderId}")
	public DeferredResult<GetOrderByOrderIdResponseBodyDto> getOrderByOrderId(@PathVariable String orderId) throws Exception {
		DeferredResult<GetOrderByOrderIdResponseBodyDto> deferredResult = new DeferredResult<>(5000L);

		if (!orderId.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
			deferredResult.setErrorResult(new IllegalArgumentException("Invalid orderId format!"));
			return deferredResult;
		}

		kafkaProducer.getOrderByOrderIdRequest(orderId, deferredResult);
		deferredResult.onError(throwable -> {
			deferredResult.setErrorResult(new Exception("An error occurred while processing the order: " + throwable.getMessage()));
		});

		deferredResult.onTimeout(() -> {
			deferredResult.setErrorResult(new Exception("Request timeout"));
		});

		return deferredResult;

	}
}
