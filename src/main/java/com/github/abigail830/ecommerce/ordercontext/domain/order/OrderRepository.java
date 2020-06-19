package com.github.abigail830.ecommerce.ordercontext.domain.order;

import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;

import java.util.Optional;

public interface OrderRepository {

    void save(Order order);

    Optional<Order> byId(String id);
}

