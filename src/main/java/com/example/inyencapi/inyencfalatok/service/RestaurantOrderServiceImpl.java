package com.example.inyencapi.inyencfalatok.service;


import java.util.*;

import com.example.inyencapi.inyencfalatok.dto.*;
import com.example.inyencapi.inyencfalatok.exception.ObjectNotFoundException;
import com.example.inyencapi.inyencfalatok.exception.OrderNotFoundException;
import com.example.inyencapi.inyencfalatok.exception.ProductNotAvailableException;
import com.example.inyencapi.inyencfalatok.mapper.UpdateOrderStateMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inyencapi.inyencfalatok.entity.Address;
import com.example.inyencapi.inyencfalatok.entity.Customer;
import com.example.inyencapi.inyencfalatok.entity.Meal;
import com.example.inyencapi.inyencfalatok.entity.Order;
import com.example.inyencapi.inyencfalatok.entity.OrderItem;
import com.example.inyencapi.inyencfalatok.mapper.PostNewOrderMapper;
import com.example.inyencapi.inyencfalatok.repository.AddressesRepository;
import com.example.inyencapi.inyencfalatok.repository.CustomersRepository;
import com.example.inyencapi.inyencfalatok.repository.MealsRepository;
import com.example.inyencapi.inyencfalatok.repository.Order_ItemsRepository;
import com.example.inyencapi.inyencfalatok.repository.OrdersRepository;


@Service
public class RestaurantOrderServiceImpl implements RestaurantOrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantOrderServiceImpl.class);
	
	@Autowired
	AddressesRepository addressesRepository;
	
	@Autowired
	CustomersRepository customerRepository;

	@Autowired
	MealsRepository mealsRepository;
	
	@Autowired
	OrdersRepository ordersRepository;
	
	@Autowired
	Order_ItemsRepository orderItemsRepository;

	@Autowired
	PostNewOrderMapper postNewOrderMapper;

	@Autowired
	UpdateOrderStateMapper updateOrderStateMapper;

	@Override
	public Address SaveAddressIfNotExist(PostNewOrderRequestBodyDto body) {
		LOGGER.info("1");
		LOGGER.info(body.toString());
		Address newAddress = postNewOrderMapper.toAddressEntity(body);

		if(!IsAddressExistInRepository(newAddress)) {
			addressesRepository.save(newAddress);
		}
		
		return newAddress;
	}

	@Override
	public boolean IsAddressExistInRepository(Address address) {
		List<Address> allAddress = addressesRepository.findAll();

		Address addressFromDB = null;
		for(Address a : allAddress) {
			if(a.equals(address)) addressFromDB = a;
		}
		return Objects.nonNull(addressFromDB);
	}

	@Override
	public Customer SaveCustomerIfNotExist(PostNewOrderRequestBodyDto dto, Address address) {
		LOGGER.info("4");
		Customer newCustomer = postNewOrderMapper.toCustomerEntity(dto);
		LOGGER.info("newCustomer.toString()");
		
		if(!IsCustomerExistInRepository(newCustomer)) {
			LOGGER.info("newCustomer.toString()2");
			newCustomer.setAddress(address);
			customerRepository.save(newCustomer);
		}
		
		return newCustomer;
	}

	@Override
	public boolean IsCustomerExistInRepository(Customer customer) {
		LOGGER.info("asd");
		List<Customer> allCustomer = customerRepository.findAll();
		boolean isCustomerExist = false;
		LOGGER.info("esd");
		for(Customer c : allCustomer) {
			LOGGER.info(c.getId().toString());
			LOGGER.info(c.getCustomerName().toString());
			LOGGER.info(c.getCustomerEmail().toString());
			if (c.equals(customer)) {
				isCustomerExist = true;
				break;
			}
		}
		LOGGER.info("usd");
		LOGGER.info(String.valueOf(isCustomerExist));
		return isCustomerExist;
	}

	@Override
	public Customer GetCustomerFromRepository(PostNewOrderRequestBodyDto dto) {
		Customer mappedCustomer = postNewOrderMapper.toCustomerEntity(dto);

		List<Customer> allCustomer = customerRepository.findAll();

		Customer customerFromDb = null;
		for(Customer c : allCustomer) {
			if(c.equals(mappedCustomer)) customerFromDb = c;
		}

		return customerFromDb;
	}

	@Override
	public Meal GetMealFromRepository(UUID mealId) {

		Meal meal = mealsRepository.findById(mealId)
				.orElseThrow(() -> new ObjectNotFoundException("Meal not found with ID: " + mealId));

		if(meal.getMealAvailability() == Meal.MealAvailability.kifogyott){
			return null;
		}else{
			return meal;
		}

	}

	@Override
	public void SaveOrderItems(PostNewOrderRequestBodyDto dto, Order newOrder) {
		List<MealQuantityDto> mealItemsFromDto = dto.getMealItems();
		List<OrderItem> orderItemList = new ArrayList<>();
		for(MealQuantityDto d : mealItemsFromDto) {

			OrderItem newOrderItem = new OrderItem();
			newOrderItem.setOrder(newOrder);

			Meal actualMeal = GetMealFromRepository(d.getMealId());
			if(actualMeal == null){
				ordersRepository.delete(newOrder);
				throw new ProductNotAvailableException("Item is out of stock: " + d.getMealId());
			}

			newOrderItem.setMeal(actualMeal);
			newOrderItem.setQuantity(d.getMealQuantity());
			orderItemList.add(newOrderItem);
		}
		for(OrderItem o : orderItemList){
			orderItemsRepository.save(o);
		}
    }

	@Override
	public Order SaveOrder(PostNewOrderRequestBodyDto dto) {
		Order newOrder = new Order();
		newOrder.setOrderId(dto.getOrderId());
		newOrder.setCustomer(GetCustomerFromRepository(dto));
		ordersRepository.save(newOrder);
		return newOrder;
	}

	@Override
	public UpdateOrderStateResponseBodyDto UpdateOrderState(UpdatableOrderDto orderDatas) {

		Optional<Order> originalOrder = ordersRepository.findById(orderDatas.getOrderId());

		if (originalOrder.isPresent()) {
			Order updatableOrder = updateOrderStateMapper.toOrder(orderDatas);
			updatableOrder.setCustomer(originalOrder.get().getCustomer());
			ordersRepository.save(updatableOrder);

			return new UpdateOrderStateResponseBodyDto("Record updated", updatableOrder.getOrderId().toString());
		} else {
			throw new OrderNotFoundException("Order not found with id " + originalOrder);
		}
	}

}


