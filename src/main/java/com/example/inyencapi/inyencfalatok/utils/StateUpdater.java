package com.example.inyencapi.inyencfalatok.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StateUpdater {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateUpdater.class);

    @Scheduled(fixedRate = 1000)
    public void updateOrderState() {
        /*List<Order> delayedOrders = orderRepository.findPendingOrdersOlderThan(3); // 3 óra
        for (Order order : delayedOrders) {
            orderStatusProducer.sendOrderStatusUpdate(order.getId(), OrderStatus.DELAYED);
        }*/
        LOGGER.info("asd");

    }
}
