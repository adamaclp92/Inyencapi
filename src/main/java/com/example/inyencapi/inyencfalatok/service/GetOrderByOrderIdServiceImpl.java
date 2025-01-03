package com.example.inyencapi.inyencfalatok.service;

import com.example.inyencapi.inyencfalatok.dto.*;
import com.example.inyencapi.inyencfalatok.entity.*;
import com.example.inyencapi.inyencfalatok.exception.ObjectNotFoundException;
import com.example.inyencapi.inyencfalatok.exception.OrderNotFoundException;
import com.example.inyencapi.inyencfalatok.mapper.GetOrderMapper;
import com.example.inyencapi.inyencfalatok.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        return ordersRepository.findById(orderId)
                .orElseThrow(() -> new ObjectNotFoundException("Order not found with ID: " + orderId));
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
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException("Customer not found with ID: " + customerId));

    }

    @Override
    public CustomerDto MapCustomerToCustomerDto(Customer customer) {
        return getOrderMapper.toCustomerDto(customer);
    }

    @Override
    public AddressDto GetAddressFromRepository(UUID addressId) {
        Address address = addressesRepository.findById(addressId)
                .orElseThrow(() -> new ObjectNotFoundException("Address not found with ID: " + addressId));

        return getOrderMapper.toAddressDto(address);
    }

    @Override
    public GetOrderByOrderIdResponseBodyDto GetResponseBodyDto(UUID orderId) {
        GetOrderByOrderIdResponseBodyDto response = new GetOrderByOrderIdResponseBodyDto();

        Order orderFromDb = GetOrderFromRepository(orderId);
        if(orderFromDb == null){
            throw new OrderNotFoundException("Order with ID " + orderId + " not found.");
        }

        OrderDto responseOrderDto =  MapOrderToOrderDto(orderFromDb);

        List<MealQuantityDto> responseMealsList =  GetMealsFromOrderItemList(orderId);

        Customer customerFromDb = GetCustomerFromRepository(orderFromDb.getCustomer().getId());
        CustomerDto responseCustomerDto = MapCustomerToCustomerDto(customerFromDb);

        AddressDto responseAddressDto = GetAddressFromRepository(customerFromDb.getAddress().getId());

        response.setOrderDatas(responseOrderDto);
        response.setMealItems(responseMealsList);
        response.setCustomerDatas(responseCustomerDto);
        response.setCustomerAddress(responseAddressDto);

        return response;

    }

}
