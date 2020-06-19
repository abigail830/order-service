package com.github.abigail830.ecommerce.ordercontext.infrastructure;

import com.github.abigail830.ecommerce.ordercontext.domain.order.OrderPaymentProxy;
import com.github.abigail830.ecommerce.ordercontext.infrastructure.client.WxClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderPaymentProxyImpl implements OrderPaymentProxy {

    @Autowired
    WxClient wxClient;

    @Override
    public void pay(String orderId, BigDecimal paidPrice) {
        wxClient.pay(orderId, paidPrice);
    }
}
