package com.github.abigail830.ecommerce.orderservice.domain.order.exception;

import com.github.abigail830.ecommerce.orderservice.exception.BizException;

import static com.github.abigail830.ecommerce.orderservice.domain.order.exception.OrderErrorCode.PRODUCT_NOT_IN_ORDER;
import static com.google.common.collect.ImmutableMap.of;

public class ProductNotInOrderException extends BizException {
    public ProductNotInOrderException(String productId, String orderId) {
        super(PRODUCT_NOT_IN_ORDER, of("productId", productId,
                "orderId", orderId));
    }
}
