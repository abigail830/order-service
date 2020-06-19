package com.github.abigail830.ecommerce.ordercontext.domain.order.exception;

import com.github.abigail830.ecommerce.ordercontext.exception.BizException;

import static com.github.abigail830.ecommerce.ordercontext.domain.order.exception.OrderErrorCode.ORDER_CANNOT_BE_MODIFIED;
import static com.google.common.collect.ImmutableMap.of;

public class OrderCannotBeModifiedException extends BizException {
    public OrderCannotBeModifiedException(String id) {
        super(ORDER_CANNOT_BE_MODIFIED, of("orderId", id));
    }
}
