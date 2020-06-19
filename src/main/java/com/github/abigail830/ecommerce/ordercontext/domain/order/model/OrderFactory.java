package com.github.abigail830.ecommerce.ordercontext.domain.order.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderFactory {

    public Order create(List<OrderItem> items, Address address) {
        String orderId = generate();
        return Order.create(orderId, items, address);
    }

    public String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
