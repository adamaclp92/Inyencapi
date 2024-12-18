package com.example.inyencapi.inyencfalatok.kafka;

import com.example.inyencapi.inyencfalatok.dto.*;
import com.example.inyencapi.inyencfalatok.entity.*;
import com.example.inyencapi.inyencfalatok.service.GetOrderByOrderIdServiceImpl;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.inyencapi.inyencfalatok.service.PostNewOrderServiceImpl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@Service
public class KafkaConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

	private static final String KAFKA_GROUP_ID = "inyenc_group_id";

	private static final String POST_NEW_ORDER_REQUEST_TOPIC = "PostNewOrderRequest_topic";
	private static final String POST_NEW_ORDER_RESPONSE_TOPIC = "PostNewOrderResponse_topic";

	private static final String GET_ORDER_REQUEST_TOPIC = "GetOrderRequest_topic";
	private static final String GET_ORDER_RESPONSE_TOPIC = "GetOrderResponse_topic";

	@Autowired 
	private final PostNewOrderServiceImpl postNewOrderServiceImpl;

	@Autowired
	private final GetOrderByOrderIdServiceImpl getOrderByOrderIdServiceImpl;

	@Autowired
	private final KafkaTemplate<String, GetOrderByOrderIdResponseBodyDto> kafkaTemplateGetOrder;


	public KafkaConsumer(
            PostNewOrderServiceImpl postNewOrderServiceImpl, GetOrderByOrderIdServiceImpl getOrderByOrderIdServiceImpl, KafkaTemplate<String, GetOrderByOrderIdResponseBodyDto> kafkaTemplateGetOrder) {
		super();
		this.postNewOrderServiceImpl = postNewOrderServiceImpl;
        this.getOrderByOrderIdServiceImpl = getOrderByOrderIdServiceImpl;
        this.kafkaTemplateGetOrder = kafkaTemplateGetOrder;
    }



	@KafkaListener(topics="PostNewOrderRequest_topic", groupId="inyenc_group_id")
	 public void consume(PostNewOrderRequestBodyDto postNewOrderRequestBody) {
		LOGGER.info(String.format("Json message recieved -> %s", postNewOrderRequestBody.toString()));

		Address newAddress = postNewOrderServiceImpl.SaveAddressIfNotExist(postNewOrderRequestBody);
		//LOGGER.info(newAddress.toString());

		Customer newCustomer = postNewOrderServiceImpl.SaveCustomerIfNotExist(postNewOrderRequestBody, newAddress);
		//LOGGER.info(newCustomer.toString());

		Order newOrder = postNewOrderServiceImpl.SaveOrder(postNewOrderRequestBody);
		//LOGGER.info(newOrder.toString());

		postNewOrderServiceImpl.SaveOrderItems(postNewOrderRequestBody, newOrder);
    }


	@KafkaListener(topics = GET_ORDER_REQUEST_TOPIC, groupId = KAFKA_GROUP_ID)
	public void getOrderByOrderIdResponse(ConsumerRecord<String, String> record) {
		String orderId = record.value();
		UUID orderUuid = null;
		LOGGER.info(orderId);
		try{
			orderUuid = UUID.fromString(orderId);
		}catch (IllegalArgumentException e) {
			LOGGER.info("Invalid UUID format: {}", orderId);
			throw new IllegalArgumentException(e);
		}
		LOGGER.info(orderId);

		LOGGER.info("asd");
		Order order = getOrderByOrderIdServiceImpl.GetOrderFromRepository(orderUuid);
		LOGGER.info("esd");
		OrderDto orderDto = getOrderByOrderIdServiceImpl.MapOrderToOrderDto(order);

		List<MealQuantityDto> mealQuantityDto = getOrderByOrderIdServiceImpl.GetMealsFromOrderItemList(orderUuid);

		Customer customer = getOrderByOrderIdServiceImpl.GetCustomerFromRepository(order.getCustomer().getId());
		CustomerDto customerDto = getOrderByOrderIdServiceImpl.MapCustomerToCustomerDto(customer);

		AddressDto addressDto = getOrderByOrderIdServiceImpl.GetAddressFromRepository(customer.getAddress().getId());

		GetOrderByOrderIdResponseBodyDto response = new GetOrderByOrderIdResponseBodyDto();
		response.setOrderDatas(orderDto);
		response.setMealItems(mealQuantityDto);
		response.setCustomerDatas(customerDto);
		response.setCustomerAddress(addressDto);

		kafkaTemplateGetOrder.send(GET_ORDER_RESPONSE_TOPIC, orderId, response);
	}

}
