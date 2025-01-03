package com.example.inyencapi.inyencfalatok.controller;

import com.example.inyencapi.inyencfalatok.dto.GetOrderByOrderIdResponseBodyDto;
import com.example.inyencapi.inyencfalatok.dto.PostNewOrderResponseBodyDto;
import com.example.inyencapi.inyencfalatok.exception.OrderNotFoundException;
import com.example.inyencapi.inyencfalatok.kafka.OrderProducer;
import com.example.inyencapi.inyencfalatok.service.GetOrderByOrderIdService;
import com.example.inyencapi.inyencfalatok.service.GetOrderByOrderIdServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.inyencapi.inyencfalatok.dto.PostNewOrderRequestBodyDto;

import jakarta.validation.Valid;

import java.util.UUID;


@RequestMapping("/api")
@RestController
public class OrderController {

	private final OrderProducer orderGwProducer;

	private final GetOrderByOrderIdServiceImpl getOrderByOrderIdService;

	public OrderController(OrderProducer producer, GetOrderByOrderIdServiceImpl getOrderByOrderIdService) {
		this.orderGwProducer = producer;
        this.getOrderByOrderIdService = getOrderByOrderIdService;
    }
	
	@PostMapping("/orders")
	public ResponseEntity<PostNewOrderResponseBodyDto> postNewOrder(@RequestBody @Valid PostNewOrderRequestBodyDto body) throws Exception{
		PostNewOrderResponseBodyDto postNewOrderResponseBodyDto =  orderGwProducer.postNewOrderRequest(body);
		return ResponseEntity.ok(postNewOrderResponseBodyDto);

	}

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<GetOrderByOrderIdResponseBodyDto> getOrderByOrderId(@PathVariable UUID orderId) throws Exception {
		GetOrderByOrderIdResponseBodyDto getOrderByOrderIdResponseBodyDto =  getOrderByOrderIdService.GetResponseBodyDto(orderId);

		return ResponseEntity.ok(getOrderByOrderIdResponseBodyDto);
	}
}
