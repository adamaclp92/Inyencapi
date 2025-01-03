package com.example.inyencapi.inyencfalatok.kafka;

import com.example.inyencapi.inyencfalatok.dto.PostNewOrderResponseBodyDto;
import com.example.inyencapi.inyencfalatok.entity.OrderSave;
import com.example.inyencapi.inyencfalatok.repository.OrderSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import com.example.inyencapi.inyencfalatok.dto.PostNewOrderRequestBodyDto;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.inyencapi.inyencfalatok.enums.OrderStatus.Feldolgozas_alatt;

@Service
public class OrderProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    private static final String KAFKA_GROUP_ID = "inyenc_group_id";

	private static final String POST_NEW_ORDER_REQUEST_TOPIC = "PostNewOrderRequest_topic";

    @Autowired
    OrderSaveRepository orderSaveRepository;

	@Autowired
    private final KafkaTemplate<String, ResponseEntity<?>> kafkaTemplatePostNewOrder;


    public OrderProducer(KafkaTemplate<String, ResponseEntity<?>> kafkaTemplatePostNewOrder) {
        this.kafkaTemplatePostNewOrder = kafkaTemplatePostNewOrder;
    }


    //PostNewOrder
    public PostNewOrderResponseBodyDto postNewOrderRequest(PostNewOrderRequestBodyDto postNewOrderRequestBody) throws Exception {
        UUID postOrderId = postNewOrderRequestBody.getOrderId();
        OrderSave orderSaveObject = new OrderSave(postOrderId, postNewOrderRequestBody.toString());
        orderSaveRepository.save(orderSaveObject);

         Message<PostNewOrderRequestBodyDto> message = MessageBuilder
                 .withPayload(postNewOrderRequestBody)
                 .setHeader(KafkaHeaders.TOPIC, POST_NEW_ORDER_REQUEST_TOPIC)
                 .build();

        kafkaTemplatePostNewOrder.send(message);

        return new PostNewOrderResponseBodyDto(Feldolgozas_alatt, postNewOrderRequestBody.getOrderId());
    }

}
