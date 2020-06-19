package com.github.abigail830.ecommerce.ordercontext.domain.order.exception;

import com.github.abigail830.ecommerce.ordercontext.exception.BizException;

import static com.google.common.collect.ImmutableMap.of;

public class PaidPriceNotSameWithOrderPriceException extends BizException {
    public PaidPriceNotSameWithOrderPriceException(String id) {
        super(OrderErrorCode.PAID_PRICE_NOT_SAME_WITH_ORDER_PRICE, of("orderId", id));
    }
}
