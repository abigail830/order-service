package com.github.abigail830.ecommerce.ordercontext.application;

import com.github.abigail830.ecommerce.ordercontext.domain.order.Order;
import com.github.abigail830.ecommerce.ordercontext.domain.order.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    void save(Order order);

    Optional<Order> byId(String id);

    List<OrderItem> itemsByOrderId(String id);

    List<Order> listOrdersWithPaging(int pageIndex, int pageSize);

}

