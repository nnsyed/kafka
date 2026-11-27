package com.nnsyed.payments;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {

    @KafkaListener(topics = "orders")
    public void processPayment(OrderEvent event) {

        System.out.println("---------------------------");

        System.out.println("Processing payment");
        System.out.println("Order ID: " + event.orderId());
        System.out.println("Customer: " + event.customerName());
        System.out.println("Amount: $" + event.totalAmount());

        // Simulate payment processing
        System.out.println("Payment successful");

        System.out.println("---------------------------");
    }
}