package com.nnsyed.orders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController 
{
    private final OrderProducer producer;

    public OrderController(OrderProducer producer) 
    {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderEvent event) 
    {
        producer.sendOrder(event);
        return ResponseEntity.accepted().body("Order accepted: " + event.orderId());
    }
}
