package com.github.abigail830.ecommerce.orderservice.domain.order;

public interface OrderRepository {

    void save(Order order);

    Order byId(String id);
}

