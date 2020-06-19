package com.github.abigail830.ecommerce.ordercontext.domain.order;


import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderPaymentService {
    private final OrderPaymentProxy paymentProxy;

    public OrderPaymentService(OrderPaymentProxy paymentProxy) {
        this.paymentProxy = paymentProxy;
    }

    public void pay(Order order, BigDecimal paidPrice) {
        order.pay(paidPrice);
        paymentProxy.pay(order.getId(), paidPrice);
    }
}
