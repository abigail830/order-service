package com.github.abigail830.ecommerce.ordercontext.domain.order.exception;

import com.github.abigail830.ecommerce.ordercontext.exception.BizException;

import static com.github.abigail830.ecommerce.ordercontext.domain.order.exception.OrderErrorCode.ORDER_STATUS_INVALID;
import static com.google.common.collect.ImmutableMap.of;

public class OrderStatusInvalidException extends BizException {
    public OrderStatusInvalidException(String id) {
        super(ORDER_STATUS_INVALID, of("orderId", id));
    }
}
