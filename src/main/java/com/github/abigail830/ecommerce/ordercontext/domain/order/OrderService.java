package com.github.abigail830.ecommerce.ordercontext.domain.order;


import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderService {
    private final OrderPaymentProxy paymentProxy;
    private InventoryProxy inventoryProxy;
    private DeliveryTicketProxy deliveryTicketProxy;

    @Autowired
    public OrderService(OrderPaymentProxy paymentProxy,
                        InventoryProxy inventoryProxy,
                        DeliveryTicketProxy deliveryTicketProxy) {
        this.paymentProxy = paymentProxy;
        this.inventoryProxy = inventoryProxy;
        this.deliveryTicketProxy = deliveryTicketProxy;
    }

    public void pay(Order order, BigDecimal paidPrice) {
        order.pay(paidPrice);
        paymentProxy.pay(order.getId(), paidPrice);
    }

    public void createDeliveryTicket(Order order) {
        deliveryTicketProxy.create(order);
    }

    public void reduceInventoryForItems(Order order) {
        inventoryProxy.reduceProductInventory(order.getItems());
    }
}
