package com.example.inyencapi.inyencfalatok.service;

import com.example.inyencapi.inyencfalatok.dto.OrderDto;
import com.example.inyencapi.inyencfalatok.entity.*;

import java.util.List;
import java.util.UUID;

public interface GetOrderByOrderIdService {

    OrderDto GetOrderFromRepository(UUID orderId);

    List<Meal> GetMealsFromOrderItemList(UUID orderId);

    Meal GetMealFromRepository(UUID mealId);

    Customer GetCustomerFromRepository(UUID customerId);

    Address GetAddressFromRepository(UUID addressId);

}
