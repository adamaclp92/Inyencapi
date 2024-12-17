package com.example.inyencapi.inyencfalatok.kafka;

import com.example.inyencapi.inyencfalatok.entity.Address;
import com.example.inyencapi.inyencfalatok.entity.Customer;
import com.example.inyencapi.inyencfalatok.entity.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.inyencapi.inyencfalatok.dto.PostNewOrderRequestBodyDto;
import com.example.inyencapi.inyencfalatok.service.PostNewOrderServiceImpl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class KafkaConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

	@Autowired 
	private final PostNewOrderServiceImpl postNewOrderServiceImpl;

	@Autowired
	private final KafkaTemplate<String, String> kafkaTemplateGetOrder;


	public KafkaConsumer(
            PostNewOrderServiceImpl postNewOrderServiceImpl, KafkaTemplate<String, String> kafkaTemplateGetOrder) {
		super();
		this.postNewOrderServiceImpl = postNewOrderServiceImpl;
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


	@KafkaListener(topics = "GetOrderRequest_topic", groupId = "inyenc_group_id")
	public void processOrderStatusRequest(ConsumerRecord<String, String> record) {
		String orderId = record.value();
		String orderStatus = getOrderStatusFromDatabase(orderId);
		kafkaTemplateGetOrder.send("GetOrderResponse_topic", orderId, orderStatus);
	}

	private String getOrderStatusFromDatabase(String orderId) {
		// Itt jönne az adatbázis-lekérdezés logikája
		return "Shipped"; // Ez csak egy minta adat
	}

}
