package com.nnsyed.orders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderProducer producer;

    public OrderController(OrderProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<String> createOrder(
            @PathVariable String orderId) {

        producer.sendOrder(orderId);

        return ResponseEntity.accepted()
            .body("Order accepted: " + orderId);
    }
}