package com.nnsyed.orders;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderProducer(
            KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(String orderId) {

        String message = "Order created: " + orderId;

        // Asynchronously publish message to Kafka
        kafkaTemplate.send("orders", orderId, message)
            .whenComplete((result, exception) -> {

                if (exception == null) {
                    System.out.println(
                        "Message published: " + message);
                } else {
                    System.err.println(
                        "Publishing failed: " +
                        exception.getMessage());
                }
            });
    }
}