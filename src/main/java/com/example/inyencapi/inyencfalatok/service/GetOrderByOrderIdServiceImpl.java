package com.example.inyencapi.inyencfalatok.service;

import com.example.inyencapi.inyencfalatok.dto.OrderDto;
import com.example.inyencapi.inyencfalatok.entity.*;
import com.example.inyencapi.inyencfalatok.mapper.GetOrderMapper;
import com.example.inyencapi.inyencfalatok.mapper.PostNewOrderMapper;
import com.example.inyencapi.inyencfalatok.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public OrderDto GetOrderFromRepository(UUID orderId) {

        List<Order> orders = ordersRepository.findAll();

        Order orderFromDb = null;
        for(Order o : orders) {
            if(o.getOrderId().equals(orderId)) orderFromDb = o;
        }

        OrderDto mappedOrder = getOrderMapper.toOrderDto(orderFromDb);
        //LOGGER.info(mappedOrder.toString());
        return mappedOrder;
    }


    @Override
    public List<Meal> GetMealsFromOrderItemList(UUID orderId) {
        List<Meal> mealsList = new ArrayList<>();

        List<OrderItem> orderItems = orderItemsRepository.findAll();

        LOGGER.info("1");
        List<OrderItem> orderItemsFromDb = new ArrayList<>();

        if(!orderItems.isEmpty()){
            for(OrderItem oi : orderItems){
                LOGGER.info(oi.getOrder().getOrderId().toString());
                if(oi.getOrder().getOrderId().toString().equals(orderId.toString())) {
                    LOGGER.info("innen kell folytatni");
                   // LOGGER.info(oi.toString());
                    mealsList.add(oi.getMeal());
                }
            }
        }

        LOGGER.info("3");
        return mealsList;
    }


    @Override
    public Meal GetMealFromRepository(UUID mealId) {
        return null;
    }

    @Override
    public Customer GetCustomerFromRepository(UUID customerId) {
        return null;
    }

    @Override
    public Address GetAddressFromRepository(UUID addressId) {
        return null;
    }
}
