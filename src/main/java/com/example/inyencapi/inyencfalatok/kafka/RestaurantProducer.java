package com.example.inyencapi.inyencfalatok.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class RestaurantProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    private static final String KAFKA_GROUP_ID = "inyenc_group_id";
    private static final String POST_NEW_ORDER_RESPONSE_TOPIC = "PostNewOrderResponse_topic";


    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplatePostNewOrder;

    public RestaurantProducer(KafkaTemplate<String, String> kafkaTemplatePostNewOrder) {
        this.kafkaTemplatePostNewOrder = kafkaTemplatePostNewOrder;
    }


    public void PostNewOrderResponse(UUID postOrderId, String savableOrder) {
        String stringBuilder = postOrderId.toString() +
                ";" +
                savableOrder;

        Message<String> message = MessageBuilder
                .withPayload(stringBuilder)
                .setHeader(KafkaHeaders.TOPIC, POST_NEW_ORDER_RESPONSE_TOPIC)
                .build();

        kafkaTemplatePostNewOrder.send(message);

    }
}
