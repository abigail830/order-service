package com.github.abigail830.ecommerce.ordercontext.infrastructure.client;

import com.github.abigail830.ecommerce.ordercontext.domain.order.OrderPaymentProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class OrderPaymentProxyImpl implements OrderPaymentProxy {

    @Override
    public void pay(String orderId, BigDecimal paidPrice) {
        //TODO: 对接支付宝进行支付
        log.info("Order[{}] payed with amount {}", orderId, paidPrice);
    }
}
