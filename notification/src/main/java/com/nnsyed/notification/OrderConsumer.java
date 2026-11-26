package com.nnsyed.notification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class OrderConsumer 
{
    @KafkaListener(topics = "orders")
    public void consume(OrderEvent event) 
    {
        System.out.println("---------------------------");
        System.out.println("Order ID: " + event.orderId());
        System.out.println("Customer: " + event.customerName());
        System.out.println("Product: " + event.product());
        System.out.println("Quantity: " + event.quantity());
        System.out.println("Total Amount: " + event.totalAmount());
        System.out.println("Notification sent to " +
            event.customerName());

        System.out.println("---------------------------");
    }
}