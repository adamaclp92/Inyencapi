package com.example.inyencapi.inyencfalatok.kafka;

import com.example.inyencapi.inyencfalatok.dto.ErrorResponseDto;
import com.example.inyencapi.inyencfalatok.entity.Address;
import com.example.inyencapi.inyencfalatok.entity.Customer;
import com.example.inyencapi.inyencfalatok.entity.Order;
import com.example.inyencapi.inyencfalatok.exception.ProductNotAvailableException;
import com.example.inyencapi.inyencfalatok.service.RestaurantOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.example.inyencapi.inyencfalatok.dto.PostNewOrderRequestBodyDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RestaurantConsumer {
   private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    private static final String KAFKA_GROUP_ID = "inyenc_group_id";

    private static final String POST_NEW_ORDER_REQUEST_TOPIC = "PostNewOrderRequest_topic";

    @Autowired
    private final RestaurantOrderServiceImpl restaurantOrderServiceImpl;

    @Autowired
    private final RestaurantProducer restaurantProducer;


    public RestaurantConsumer(RestaurantOrderServiceImpl restaurantOrderServiceImpl, RestaurantProducer restaurantProducer) {
        this.restaurantOrderServiceImpl = restaurantOrderServiceImpl;
        this.restaurantProducer = restaurantProducer;
    }

    @KafkaListener(topics=POST_NEW_ORDER_REQUEST_TOPIC, groupId=KAFKA_GROUP_ID)
    public void postNewOrderConsumer(PostNewOrderRequestBodyDto postNewOrderRequestBody) {
        Address newAddress = restaurantOrderServiceImpl.SaveAddressIfNotExist(postNewOrderRequestBody);
        Customer newCustomer = restaurantOrderServiceImpl.SaveCustomerIfNotExist(postNewOrderRequestBody, newAddress);
        Order newOrder = restaurantOrderServiceImpl.SaveOrder(postNewOrderRequestBody);
        String savableOrder = newOrder.toString();
        try{
            restaurantOrderServiceImpl.SaveOrderItems(postNewOrderRequestBody, newOrder);
        }catch(ProductNotAvailableException exc){
            ErrorResponseDto error = new ErrorResponseDto(HttpStatus.BAD_REQUEST.toString(), exc.getMessage());
            savableOrder = error.toString();
         }

        restaurantProducer.PostNewOrderResponse(newOrder.getOrderId(), savableOrder);

    }
}
