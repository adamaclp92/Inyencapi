package com.example.inyencapi.inyencfalatok.service;

import java.util.UUID;

import com.example.inyencapi.inyencfalatok.dto.PostNewOrderRequestBodyDto;
import com.example.inyencapi.inyencfalatok.dto.UpdatableOrderDto;
import com.example.inyencapi.inyencfalatok.dto.UpdateOrderStateResponseBodyDto;
import com.example.inyencapi.inyencfalatok.entity.Address;
import com.example.inyencapi.inyencfalatok.entity.Customer;
import com.example.inyencapi.inyencfalatok.entity.Meal;
import com.example.inyencapi.inyencfalatok.entity.Order;

public interface RestaurantOrderService {

	Address SaveAddressIfNotExist(PostNewOrderRequestBodyDto body);
	
	boolean IsAddressExistInRepository(Address address);

	
	Customer SaveCustomerIfNotExist(PostNewOrderRequestBodyDto dto, Address address);
	
	boolean IsCustomerExistInRepository(Customer customer);

	 Customer GetCustomerFromRepository(PostNewOrderRequestBodyDto dto);

	
	Meal GetMealFromRepository(UUID mealId);
	
	
	void SaveOrderItems(PostNewOrderRequestBodyDto dto,  Order newOrder);
	
	
	Order SaveOrder(PostNewOrderRequestBodyDto body);

	UpdateOrderStateResponseBodyDto UpdateOrderState(UpdatableOrderDto orderDatas);
}
