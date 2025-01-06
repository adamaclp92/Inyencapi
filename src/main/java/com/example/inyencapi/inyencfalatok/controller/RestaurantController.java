package com.example.inyencapi.inyencfalatok.controller;

import com.example.inyencapi.inyencfalatok.dto.*;
import com.example.inyencapi.inyencfalatok.kafka.OrderProducer;
import com.example.inyencapi.inyencfalatok.service.GetOrderByOrderIdServiceImpl;
import com.example.inyencapi.inyencfalatok.service.RestaurantOrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.UUID;


@RequestMapping("/api")
@RestController
public class RestaurantController {

    private final RestaurantOrderServiceImpl restaurantOrderService;

    public RestaurantController(RestaurantOrderServiceImpl restaurantOrderService) {
        this.restaurantOrderService = restaurantOrderService;
    }


    @PutMapping("/orders/state")
    public ResponseEntity<UpdateOrderStateResponseBodyDto> updateOrderState(@RequestBody @Valid UpdateOrderStateRequestBodyDto body) throws Exception{
        UpdateOrderStateResponseBodyDto updateResponseMessage = restaurantOrderService.UpdateOrderState(body.getOrderDatas());
        return ResponseEntity.ok(updateResponseMessage);
    }
}
