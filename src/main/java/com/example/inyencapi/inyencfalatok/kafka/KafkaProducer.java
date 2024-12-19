package com.example.inyencapi.inyencfalatok.kafka;

import com.example.inyencapi.inyencfalatok.dto.GetOrderByOrderIdResponseBodyDto;
import com.example.inyencapi.inyencfalatok.dto.PostNewOrderResponseBodyDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
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
    private final KafkaTemplate<String, ResponseEntity<?>> kafkaTemplatePostNewOrder;

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplateGetOrder;
    private final ConcurrentHashMap<String, DeferredResult<ResponseEntity<?>>> pendingRequestsPost = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, DeferredResult<GetOrderByOrderIdResponseBodyDto>> pendingRequestsGet = new ConcurrentHashMap<>();


    public KafkaProducer(KafkaTemplate<String, ResponseEntity<?>> kafkaTemplatePostNewOrder, KafkaTemplate<String, String> kafkaTemplateGetOrder) {
        this.kafkaTemplatePostNewOrder = kafkaTemplatePostNewOrder;
        this.kafkaTemplateGetOrder = kafkaTemplateGetOrder;
    }


    //PostNewOrder
    public DeferredResult<ResponseEntity<?>> postNewOrderRequest(PostNewOrderRequestBodyDto postNewOrderRequestBody, DeferredResult<ResponseEntity<?>> deferredResult) throws Exception {
    	 LOGGER.info(String.format("Message sent -> %s", postNewOrderRequestBody.toString()));
        String postOrderId = postNewOrderRequestBody.getOrderId().toString();
        LOGGER.info(postOrderId);

        pendingRequestsPost.put(postNewOrderRequestBody.getOrderId().toString(), deferredResult);

         Message<PostNewOrderRequestBodyDto> message = MessageBuilder
                 .withPayload(postNewOrderRequestBody)
                 .setHeader(KafkaHeaders.TOPIC, POST_NEW_ORDER_REQUEST_TOPIC)
                 .build();

        kafkaTemplatePostNewOrder.send(message);

        deferredResult.onCompletion(() -> {
            pendingRequestsPost.remove(postOrderId);
        });

        return deferredResult;
    }

    @KafkaListener(topics = POST_NEW_ORDER_RESPONSE_TOPIC, groupId = KAFKA_GROUP_ID)
    public void postNewOrderResponse(ConsumerRecord<String, PostNewOrderResponseBodyDto> record) {
        LOGGER.info(record.key());
        String orderId = record.key();
        PostNewOrderResponseBodyDto response = record.value();

        DeferredResult<ResponseEntity<?>> deferredResult = pendingRequestsPost.remove(orderId);
        if (deferredResult != null) {
            deferredResult.setResult(ResponseEntity.ok(response));
        }
    }

    //GetOrderByOrderId
    public DeferredResult<GetOrderByOrderIdResponseBodyDto> getOrderByOrderIdRequest(String orderId, DeferredResult<GetOrderByOrderIdResponseBodyDto> deferredResult) throws Exception {
        LOGGER.info("asd");
        pendingRequestsGet.put(orderId, deferredResult);

        kafkaTemplateGetOrder.send(GET_ORDER_REQUEST_TOPIC, orderId);

        deferredResult.onCompletion(() -> {
            pendingRequestsGet.remove(orderId);
        });

        return deferredResult;
    }

    @KafkaListener(topics = GET_ORDER_RESPONSE_TOPIC, groupId = KAFKA_GROUP_ID)
    public void getOrderByOrderIdResponse(ConsumerRecord<String, GetOrderByOrderIdResponseBodyDto> record) {
        String orderId = record.key();
        GetOrderByOrderIdResponseBodyDto response = record.value();

        DeferredResult<GetOrderByOrderIdResponseBodyDto> deferredResult = pendingRequestsGet.remove(orderId);
        if (deferredResult != null) {
            deferredResult.setResult(response);
        }
    }
}
