package com.nnsyed.inventory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryConsumer {

    private final Map<String, Integer> inventory =
        new ConcurrentHashMap<>(Map.of(
            "Laptop", 100,
            "Monitor", 50,
            "Keyboard", 200
        ));

    @KafkaListener(topics = "orders")
    public void updateInventory(OrderEvent event) {

        System.out.println("---------------------------");

        System.out.println("Updating inventory");
        System.out.println("Order ID: " + event.orderId());
        System.out.println("Product: " + event.product());
        System.out.println("Quantity: " + event.quantity());

        if (event.quantity() <= 0) {
            System.out.println("Invalid quantity");
            return;
        }

        inventory.compute(event.product(), (product, stock) -> {

            if (stock == null || stock < event.quantity()) {

                System.out.println("Insufficient inventory");
                return stock;
            }

            int remaining = stock - event.quantity();

            System.out.println("Previous stock: " + stock);
            System.out.println("Remaining stock: " + remaining);
            System.out.println("Inventory updated");

            return remaining;
        });

        System.out.println("---------------------------");
    }
}