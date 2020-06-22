package com.github.abigail830.ecommerce.ordercontext.application;

import com.github.abigail830.ecommerce.ordercontext.domain.order.Order;

public interface DeliveryTicketProxy {

    void create(Order order);
}
