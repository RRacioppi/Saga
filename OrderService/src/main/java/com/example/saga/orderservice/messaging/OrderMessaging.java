package com.example.saga.orderservice.messaging;

import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

@Service
@Slf4j
public class OrderMessaging {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value( "${orderservice.order.topic.create}" )
    private String topic;

    public void sendOrderCreate(Order order) {
        log.info("Event Sourcing eventType='{}' asset='{}'", "ORDER_CREATED", order);
        kafkaTemplate.send(topic, order.toString());
    }
}