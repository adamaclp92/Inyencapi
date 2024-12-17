package com.example.inyencapi.inyencfalatok.kafka;

import com.example.inyencapi.inyencfalatok.dto.GetOrderByOrderIdResponseBodyDto;
import com.example.inyencapi.inyencfalatok.dto.OrderDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.inyencapi.inyencfalatok.dto.PostNewOrderRequestBodyDto;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.async.DeferredResult;

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
    private final ConcurrentHashMap<String, DeferredResult<GetOrderByOrderIdResponseBodyDto>> pendingRequests = new ConcurrentHashMap<>();


    public KafkaProducer(KafkaTemplate<String, PostNewOrderRequestBodyDto> kafkaTemplatePostNewOrder, KafkaTemplate<String, String> kafkaTemplateGetOrder) {
        this.kafkaTemplatePostNewOrder = kafkaTemplatePostNewOrder;
        this.kafkaTemplateGetOrder = kafkaTemplateGetOrder;
    }


    //PostNewOrder
   /* public  CompletableFuture<String> sendNewOrderMessage(PostNewOrderRequestBodyDto postNewOrderRequestBody) {
    	 LOGGER.info(String.format("Message sent -> %s", postNewOrderRequestBody.toString()));

        CompletableFuture<String> future = new CompletableFuture<>();
        pendingRequests.put(postNewOrderRequestBody.getOrderId().toString(), future);

         Message<PostNewOrderRequestBodyDto> message = MessageBuilder
                 .withPayload(postNewOrderRequestBody)
                 .setHeader(KafkaHeaders.TOPIC, POST_NEW_ORDER_REQUEST_TOPIC)
                 .build();

        kafkaTemplatePostNewOrder.send(message);
        return future;
    }*/

    //GetOrderByOrderId
   /* public CompletableFuture<String> sendOrderStatusRequest(String orderId) {
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
    }*/

    public DeferredResult<GetOrderByOrderIdResponseBodyDto> getOrderByOrderIdRequest(String orderId) throws Exception {
        DeferredResult<GetOrderByOrderIdResponseBodyDto> deferredResult = new DeferredResult<>(5000L);
        pendingRequests.put(orderId, deferredResult);

        kafkaTemplateGetOrder.send(GET_ORDER_REQUEST_TOPIC, orderId);

        deferredResult.onTimeout(() -> {
            DeferredResult<GetOrderByOrderIdResponseBodyDto> pendingRequest = pendingRequests.remove(orderId);
            if (pendingRequest != null) {
                pendingRequest.setErrorResult(new Exception("Request timed out."));
            }
        });

        deferredResult.onCompletion(() -> {
            pendingRequests.remove(orderId);
        });

        return deferredResult;
    }

    @KafkaListener(topics = GET_ORDER_RESPONSE_TOPIC, groupId = KAFKA_GROUP_ID)
    public void getOrderByOrderIdResponse(ConsumerRecord<String, GetOrderByOrderIdResponseBodyDto> record) {
        String orderId = record.key();
        GetOrderByOrderIdResponseBodyDto response = record.value();

        DeferredResult<GetOrderByOrderIdResponseBodyDto> deferredResult = pendingRequests.remove(orderId);
        if (deferredResult != null) {
            deferredResult.setResult(response);
        }
    }
}
