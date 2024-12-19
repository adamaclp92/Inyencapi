package com.example.inyencapi.inyencfalatok.service;


import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.example.inyencapi.inyencfalatok.dto.MealQuantityDto;
import com.example.inyencapi.inyencfalatok.dto.PostNewOrderResponseBodyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inyencapi.inyencfalatok.dto.PostNewOrderRequestBodyDto;
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
public class PostNewOrderServiceImpl implements PostNewOrderService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PostNewOrderServiceImpl.class);
	
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

	@Override
	public Address SaveAddressIfNotExist(PostNewOrderRequestBodyDto body) {
		Address newAddress = postNewOrderMapper.toAddressEntity(body);

		if(!IsAddressExistInRepository(newAddress)) {
			addressesRepository.save(newAddress);
		}
		
		return newAddress;
	}

	@Override
	public boolean IsAddressExistInRepository(Address address) {
		return Objects.nonNull(GetAddressFromRepository(address));
	}

	@Override
	public Address GetAddressFromRepository(Address address) {
		List<Address> allAddress = addressesRepository.findAll();
			
			Address addressFromDB = null;
			for(Address a : allAddress) {
				if(a.equals(address)) addressFromDB = a;
			}
			return addressFromDB;
	}

	@Override
	public Customer SaveCustomerIfNotExist(PostNewOrderRequestBodyDto dto, Address address) {
		Customer newCustomer = postNewOrderMapper.toCustomerEntity(dto);
		
		if(!IsCustomerExistInRepository(newCustomer)) {
			newCustomer.setAddress(address);
			customerRepository.save(newCustomer);
		}
		
		return newCustomer;
	}

	@Override
	public boolean IsCustomerExistInRepository(Customer customer) {
		List<Customer> allCustomer = customerRepository.findAll();
		boolean isCustomerExist = false;
		for(Customer c : allCustomer) {
            if (c.equals(customer)) {
                isCustomerExist = true;
                break;
            }
		}
		
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
		List<Meal> meals = mealsRepository.findAll();
		Meal mealFromDb = null;
		for(Meal m : meals) {
			if(m.getId().equals(mealId)) mealFromDb = m;
		}
		return mealFromDb;
	}

	@Override
	public void SaveOrderItems(PostNewOrderRequestBodyDto dto, Order newOrder) {
		List<MealQuantityDto> mealItemsFromDto = dto.getMealItems();
		for(MealQuantityDto d : mealItemsFromDto) {

			OrderItem newOrderItem = new OrderItem();
			newOrderItem.setOrder(newOrder);

			Meal actualMeal = GetMealFromRepository(d.getMealId());
			newOrderItem.setMeal(actualMeal);
			newOrderItem.setQuantity(d.getMealQuantity());
			orderItemsRepository.save(newOrderItem);

			//LOGGER.info(newOrderItem.toString());
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

	public PostNewOrderResponseBodyDto responseBodyMapper(Order newOrder) {
		return postNewOrderMapper.toResponseBodyDto(newOrder);
	}
}
