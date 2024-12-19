package com.example.inyencapi.inyencfalatok.service;

import com.example.inyencapi.inyencfalatok.dto.AddressDto;
import com.example.inyencapi.inyencfalatok.dto.CustomerDto;
import com.example.inyencapi.inyencfalatok.dto.MealQuantityDto;
import com.example.inyencapi.inyencfalatok.dto.OrderDto;
import com.example.inyencapi.inyencfalatok.entity.*;
import com.example.inyencapi.inyencfalatok.mapper.GetOrderMapper;
import com.example.inyencapi.inyencfalatok.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GetOrderByOrderIdServiceImpl implements GetOrderByOrderIdService{
    private static final Logger LOGGER = LoggerFactory.getLogger(GetOrderByOrderIdServiceImpl.class);

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
    GetOrderMapper getOrderMapper;

    @Override
    public Order GetOrderFromRepository(UUID orderId) {

        List<Order> orders = ordersRepository.findAll();

        Order orderFromDb = null;
        for(Order o : orders) {
            if(o.getOrderId().equals(orderId)) orderFromDb = o;
        }
        return orderFromDb;
    }

    @Override
    public OrderDto MapOrderToOrderDto(Order orderFromDb){
        return getOrderMapper.toOrderDto(orderFromDb);
    }


    @Override
    public List<MealQuantityDto> GetMealsFromOrderItemList(UUID orderId) {
        List<MealQuantityDto> mealsList = new ArrayList<>();

        List<OrderItem> orderItems = orderItemsRepository.findAll();

        if(!orderItems.isEmpty()){
            for(OrderItem oi : orderItems){
                if(oi.getOrder().getOrderId().toString().equals(orderId.toString())) {
                    MealQuantityDto actualMealQuantity = getOrderMapper.toMealQuantityDto(oi);
                    mealsList.add(actualMealQuantity);
                }
            }
        }
        return mealsList;
    }

    @Override
    public Customer GetCustomerFromRepository(UUID customerId) {
        List<Customer> customers = customerRepository.findAll();

        Customer customer = null;
        if(!customers.isEmpty()){
            for(Customer c : customers){
                if(c.getId().equals(customerId)) {
                    customer = c;
                }
            }
        }
        return customer;
    }

    @Override
    public CustomerDto MapCustomerToCustomerDto(Customer customer) {
        return getOrderMapper.toCustomerDto(customer);
    }

    @Override
    public AddressDto GetAddressFromRepository(UUID addressId) {

        List<Address> addresses = addressesRepository.findAll();

        Address address = null;
        if(!addresses.isEmpty()){
            for(Address a : addresses){
                if(a.getId().equals(addressId)) {
                    address = a;
                }
            }
        }

        return getOrderMapper.toAddressDto(address);
    }





}
