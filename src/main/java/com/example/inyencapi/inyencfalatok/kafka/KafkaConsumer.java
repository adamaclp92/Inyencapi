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
	private final KafkaTemplate<String, PostNewOrderResponseBodyDto> kafkaTemplatePostNewOrder;

	@Autowired
	private final KafkaTemplate<String, GetOrderByOrderIdResponseBodyDto> kafkaTemplateGetOrder;

	public KafkaConsumer(
            PostNewOrderServiceImpl postNewOrderServiceImpl, GetOrderByOrderIdServiceImpl getOrderByOrderIdServiceImpl, KafkaTemplate<String, PostNewOrderResponseBodyDto> kafkaTemplatePostNewOrder, KafkaTemplate<String, GetOrderByOrderIdResponseBodyDto> kafkaTemplateGetOrder) {
		super();
		this.postNewOrderServiceImpl = postNewOrderServiceImpl;
        this.getOrderByOrderIdServiceImpl = getOrderByOrderIdServiceImpl;
        this.kafkaTemplatePostNewOrder = kafkaTemplatePostNewOrder;
        this.kafkaTemplateGetOrder = kafkaTemplateGetOrder;
    }



	@KafkaListener(topics=POST_NEW_ORDER_REQUEST_TOPIC, groupId="inyenc_group_id")
	 public void postNewOrderConsumer(PostNewOrderRequestBodyDto postNewOrderRequestBody) {
		Address newAddress = postNewOrderServiceImpl.SaveAddressIfNotExist(postNewOrderRequestBody);
		Customer newCustomer = postNewOrderServiceImpl.SaveCustomerIfNotExist(postNewOrderRequestBody, newAddress);
		Order newOrder = postNewOrderServiceImpl.SaveOrder(postNewOrderRequestBody);
		postNewOrderServiceImpl.SaveOrderItems(postNewOrderRequestBody, newOrder);

		PostNewOrderResponseBodyDto response = postNewOrderServiceImpl.responseBodyMapper(newOrder);

		kafkaTemplatePostNewOrder.send(POST_NEW_ORDER_RESPONSE_TOPIC, response.getOrderId().toString(), response);
    }


	@KafkaListener(topics = GET_ORDER_REQUEST_TOPIC, groupId = KAFKA_GROUP_ID)
	public void getOrderByOrderIdResponse(ConsumerRecord<String, String> record) {
		UUID orderId = UUID.fromString(record.value());

		Order order = getOrderByOrderIdServiceImpl.GetOrderFromRepository(orderId);
		OrderDto orderDto = getOrderByOrderIdServiceImpl.MapOrderToOrderDto(order);

		List<MealQuantityDto> mealQuantityDto = getOrderByOrderIdServiceImpl.GetMealsFromOrderItemList(orderId);

		Customer customer = getOrderByOrderIdServiceImpl.GetCustomerFromRepository(order.getCustomer().getId());
		CustomerDto customerDto = getOrderByOrderIdServiceImpl.MapCustomerToCustomerDto(customer);

		AddressDto addressDto = getOrderByOrderIdServiceImpl.GetAddressFromRepository(customer.getAddress().getId());

		GetOrderByOrderIdResponseBodyDto response = new GetOrderByOrderIdResponseBodyDto();
		response.setOrderDatas(orderDto);
		response.setMealItems(mealQuantityDto);
		response.setCustomerDatas(customerDto);
		response.setCustomerAddress(addressDto);

		kafkaTemplateGetOrder.send(GET_ORDER_RESPONSE_TOPIC, orderId.toString(), response);
	}

}
