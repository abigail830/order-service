package com.github.abigail830.ecommerce.ordercontext.infrastructure.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class WxClient {

    public void pay(String orderId, BigDecimal paidPrice) {
        //TODO: http connect with wx for payment
        log.info("Wx Payed {} for oder {}", paidPrice, orderId);
    }
}
