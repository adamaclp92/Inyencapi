package com.example.inyencapi.inyencfalatok.controller;

import com.example.inyencapi.inyencfalatok.dto.PostNewOrderResponseBodyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.inyencapi.inyencfalatok.dto.PostNewOrderRequestBodyDto;
import com.example.inyencapi.inyencfalatok.kafka.KafkaProducer;

import jakarta.validation.Valid;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


@RequestMapping("/api")
@RestController
public class OrderController {

	private final KafkaProducer kafkaProducer;

	public OrderController(KafkaProducer producer) {
		this.kafkaProducer = producer;
		
	}
	
	@PostMapping("/orders")
	public ResponseEntity<String> writeMessageToTopic(@RequestBody @Valid PostNewOrderRequestBodyDto body) throws ExecutionException, InterruptedException, TimeoutException {
		 kafkaProducer.sendNewOrderMessage(body);
		PostNewOrderResponseBodyDto response = new PostNewOrderResponseBodyDto();
		response.setOrderId(body.getOrderId());
		response.setStatus("Feldolgozas_alatt");

		 return ResponseEntity.ok(String.format(response.toString()));

	}

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<String> getOrderStatus(@PathVariable String orderId) throws Exception {
		try {
			return ResponseEntity.ok(String.format((kafkaProducer.sendOrderStatusRequest(orderId).get(10, java.util.concurrent.TimeUnit.SECONDS))));
		} catch (Exception e) {
			throw new RuntimeException("Request timeout");
		}
	}



	
}
