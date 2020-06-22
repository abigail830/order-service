package com.github.abigail830.ecommerce.ordercontext.application;

import java.math.BigDecimal;

public interface PaymentProxy {
    void pay(String orderId, BigDecimal paidPrice);
}
