package com.github.abigail830.ecommerce.ordercontext.domain.order;

import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;

public interface DeliveryTicketProxy {

    void create(Order order);
}
