package com.example.inyencapi.inyencfalatok.controller;

import com.example.inyencapi.inyencfalatok.dto.GetOrderByOrderIdResponseBodyDto;
import com.example.inyencapi.inyencfalatok.dto.OrderDto;
import com.example.inyencapi.inyencfalatok.dto.PostNewOrderResponseBodyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.inyencapi.inyencfalatok.dto.PostNewOrderRequestBodyDto;
import com.example.inyencapi.inyencfalatok.kafka.KafkaProducer;

import jakarta.validation.Valid;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


@RequestMapping("/api")
@RestController
public class OrderController {

	private final KafkaProducer kafkaProducer;

	public OrderController(KafkaProducer producer) {
		this.kafkaProducer = producer;
		
	}
	
	/*@PostMapping("/orders")
	public ResponseEntity<String> writeMessageToTopic(@RequestBody @Valid PostNewOrderRequestBodyDto body) throws Exception{
		try {
			return ResponseEntity.ok(String.format((kafkaProducer.sendNewOrderMessage(body).get(10, java.util.concurrent.TimeUnit.SECONDS))));
		} catch (Exception e) {
			throw new RuntimeException("Request timeout");
		}
		/*kafkaProducer.sendNewOrderMessage(body);
		PostNewOrderResponseBodyDto response = new PostNewOrderResponseBodyDto();
		response.setOrderId(body.getOrderId());
		response.setStatus("Feldolgozas_alatt");

		 return ResponseEntity.ok(String.format(response.toString()));

	}*/

	@GetMapping("/orders/{orderId}")
	public DeferredResult<GetOrderByOrderIdResponseBodyDto> getOrderByOrderId(@PathVariable String orderId) throws Exception {
		/*try {
			return ResponseEntity.ok(String.format((kafkaProducer.sendOrderStatusRequest(orderId).get(10, java.util.concurrent.TimeUnit.SECONDS))));
		} catch (Exception e) {
			throw new RuntimeException("Request timeout");
		}*/

		/*deferredResult.onError((Throwable throwable) -> {
			deferredResult.setErrorResult("An error occurred while processing the order: " + throwable.getMessage());
		});*/

		return kafkaProducer.getOrderByOrderIdRequest(orderId);
	}
}
