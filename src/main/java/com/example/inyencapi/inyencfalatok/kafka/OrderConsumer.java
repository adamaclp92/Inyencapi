package com.example.inyencapi.inyencfalatok.kafka;

import com.example.inyencapi.inyencfalatok.entity.OrderSave;
import com.example.inyencapi.inyencfalatok.repository.OrderSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

@Service
public class OrderConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

	private static final String KAFKA_GROUP_ID = "inyenc_group_id";

	private static final String POST_NEW_ORDER_RESPONSE_TOPIC = "PostNewOrderResponse_topic";


	@Autowired
	OrderSaveRepository orderSaveRepository;

	public OrderConsumer() {
		super();
    }


	@KafkaListener(topics = POST_NEW_ORDER_RESPONSE_TOPIC, groupId = KAFKA_GROUP_ID)
	public void postNewOrderResponse(String savableResponse) {
		LOGGER.info("savableResponse");
		LOGGER.info(savableResponse);
		String[] array = savableResponse.split(";");
		String orderId = array[0];
		String content = array[1];
		OrderSave orderSaveObject = new OrderSave(UUID.fromString(orderId), content);
		orderSaveRepository.save(orderSaveObject);
	}

}
