package com.github.abigail830.ecommerce.ordercontext.domain.order.exception;

import com.github.abigail830.ecommerce.ordercontext.exception.BizException;

import static com.github.abigail830.ecommerce.ordercontext.domain.order.exception.OrderErrorCode.ORDER_NOT_FOUND;
import static com.google.common.collect.ImmutableMap.of;

public class OrderNotFoundException extends BizException {
    public OrderNotFoundException(String orderId) {
        super(ORDER_NOT_FOUND, of("orderId", orderId));
    }
}
