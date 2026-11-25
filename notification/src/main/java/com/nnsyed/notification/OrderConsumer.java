package com.nnsyed.notification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @KafkaListener(topics = "orders",
                   groupId = "notification-group")
    public void consume(String message) {

        System.out.println(
            "Received event: " + message);

        // Simulate sending a notification
        System.out.println(
            "Notification sent successfully");
    }
}