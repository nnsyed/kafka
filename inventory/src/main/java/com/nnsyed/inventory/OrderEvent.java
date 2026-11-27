package com.nnsyed.inventory;

import java.math.BigDecimal;

public record OrderEvent(
    String orderId,
    String customerName,
    String product,
    int quantity,
    BigDecimal totalAmount
) {}