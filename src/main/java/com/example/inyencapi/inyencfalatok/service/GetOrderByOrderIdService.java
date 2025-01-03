package com.example.inyencapi.inyencfalatok.service;

import com.example.inyencapi.inyencfalatok.dto.*;
import com.example.inyencapi.inyencfalatok.entity.*;

import java.util.List;
import java.util.UUID;

public interface GetOrderByOrderIdService {

    Order GetOrderFromRepository(UUID orderId);

    OrderDto MapOrderToOrderDto(Order orderFromDb);

    List<MealQuantityDto> GetMealsFromOrderItemList(UUID orderId);

    Customer GetCustomerFromRepository(UUID customerId);

    CustomerDto MapCustomerToCustomerDto(Customer customer);

    AddressDto GetAddressFromRepository(UUID addressId);

    GetOrderByOrderIdResponseBodyDto GetResponseBodyDto(UUID orderId);

}
