package com.nnsyed.notification;

import java.math.BigDecimal;

public record OrderEvent(
    String orderId,
    String customerName,
    String product,
    int quantity,
    BigDecimal totalAmount) 
{
}