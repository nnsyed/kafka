package com.nnsyed.orders;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer 
{
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) 
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(OrderEvent event) 
    {
        kafkaTemplate.send("orders", event.orderId(), event)
            .whenComplete((result, exception) -> {
                if (exception == null) 
                {
                    System.out.println("Published: " + event);
                    System.out.println("Partition: " + result.getRecordMetadata().partition());
                    System.out.println("Offset: " + result.getRecordMetadata().offset());
                } 
                else 
                {
                    System.err.println("Publishing failed: " + exception.getMessage());
                }
            });
    }
}

