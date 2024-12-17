package com.example.inyencapi.inyencfalatok.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inyencapi.inyencfalatok.dto.PostNewOrderRequestBodyDto;
import com.example.inyencapi.inyencfalatok.entity.Address;
import com.example.inyencapi.inyencfalatok.entity.Customer;
import com.example.inyencapi.inyencfalatok.entity.Meal;
import com.example.inyencapi.inyencfalatok.entity.Order;
import com.example.inyencapi.inyencfalatok.entity.OrderItem;

public interface PostNewOrderService {

	Address SaveAddressIfNotExist(PostNewOrderRequestBodyDto body);
	
	boolean IsAddressExistInRepository(Address address);
	
	Address GetAddressFromRepository(Address address);
	
	//UUID GetAddressIdFromRepository(Address address);
	
	
	
	Customer SaveCustomerIfNotExist(PostNewOrderRequestBodyDto dto, Address address);
	
	boolean IsCustomerExistInRepository(Customer customer);
	
	Customer GetCustomerFromRepository(PostNewOrderRequestBodyDto dto);
	
	
	
	Meal GetMealFromRepository(UUID mealId);
	
	
	void SaveOrderItems(PostNewOrderRequestBodyDto dto,  Order newOrder);
	
	
	Order SaveOrder(PostNewOrderRequestBodyDto body);
	
	
}
