package com.example.inyencapi.inyencfalatok.kafka;

import com.example.inyencapi.inyencfalatok.dto.PostNewOrderResponseBodyDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.inyencapi.inyencfalatok.dto.PostNewOrderRequestBodyDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;

@Service
public class KafkaProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private static final String KAFKA_GROUP_ID = "inyenc_group_id";

	private static final String POST_NEW_ORDER_REQUEST_TOPIC = "PostNewOrderRequest_topic";
    private static final String POST_NEW_ORDER_RESPONSE_TOPIC = "PostNewOrderResponse_topic";

    private static final String GET_ORDER_REQUEST_TOPIC = "GetOrderRequest_topic";
    private static final String GET_ORDER_RESPONSE_TOPIC = "GetOrderResponse_topic";

	@Autowired
    private final KafkaTemplate<String, PostNewOrderRequestBodyDto> kafkaTemplatePostNewOrder;

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplateGetOrder;
    private final ConcurrentHashMap<String, CompletableFuture<String>> pendingRequests = new ConcurrentHashMap<>();



    public KafkaProducer(KafkaTemplate<String, PostNewOrderRequestBodyDto> kafkaTemplatePostNewOrder, KafkaTemplate<String, String> kafkaTemplateGetOrder) {
        this.kafkaTemplatePostNewOrder = kafkaTemplatePostNewOrder;
        this.kafkaTemplateGetOrder = kafkaTemplateGetOrder;
    }

    public  CompletableFuture<String> sendNewOrderMessage(PostNewOrderRequestBodyDto postNewOrderRequestBody) {
    	 LOGGER.info(String.format("Message sent -> %s", postNewOrderRequestBody.toString()));

        CompletableFuture<String> future = new CompletableFuture<>();
        pendingRequests.put(postNewOrderRequestBody.getOrderId().toString(), future);

         Message<PostNewOrderRequestBodyDto> message = MessageBuilder
                 .withPayload(postNewOrderRequestBody)
                 .setHeader(KafkaHeaders.TOPIC, POST_NEW_ORDER_REQUEST_TOPIC)
                 .build();

        kafkaTemplatePostNewOrder.send(message);
        return future;
    }


    public CompletableFuture<String> sendOrderStatusRequest(String orderId) {
        CompletableFuture<String> future = new CompletableFuture<>();
        pendingRequests.put(orderId, future);
        kafkaTemplateGetOrder.send(GET_ORDER_REQUEST_TOPIC, orderId);
        return future;
    }


    @KafkaListener(topics = GET_ORDER_RESPONSE_TOPIC, groupId = KAFKA_GROUP_ID)
    public void handleResponse(ConsumerRecord<String, String> record) {
        String orderId = record.key();
        String orderStatus = record.value();

        CompletableFuture<String> future = pendingRequests.remove(orderId);
        if (future != null) {
            future.complete(orderStatus);
        }
    }
}
