package com.github.abigail830.ecommerce.orderservice.domain.payment;

import com.github.abigail830.ecommerce.orderservice.domain.order.Order;

import java.math.BigDecimal;

public interface OrderPaymentService {
    void pay(Order order, BigDecimal paidPrice);
}
