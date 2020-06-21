package com.github.abigail830.ecommerce.ordercontext.domain.order.exception;


import com.github.abigail830.ecommerce.ordercontext.exception.ErrorCode;

public enum OrderErrorCode implements ErrorCode {
    ORDER_CANNOT_BE_MODIFIED("订单无法变更"),
    ORDER_NOT_FOUND("没有找到订单"),
    PAID_PRICE_NOT_SAME_WITH_ORDER_PRICE("支付价格与订单实际价格不符"),
    PRODUCT_NOT_IN_ORDER("订单不包含产品"),
    ORDER_STATUS_INVALID("订单状态不符");

    private String message;

    OrderErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
