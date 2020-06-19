package com.github.abigail830.ecommerce.ordercontext.domain.order;

import java.math.BigDecimal;

public interface OrderPaymentProxy {
    void pay(String orderId, BigDecimal paidPrice);
}
