package com.github.abigail830.ecommerce.ordercontext.domain.order.exception;

import com.github.abigail830.ecommerce.ordercontext.exception.BizException;

import java.util.Collections;

public class GenericOrderException extends BizException {
    public GenericOrderException(OrderErrorCode error) {
        super(error, Collections.emptyMap());
    }
}
