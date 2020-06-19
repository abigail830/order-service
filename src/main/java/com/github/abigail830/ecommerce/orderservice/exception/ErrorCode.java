package com.github.abigail830.ecommerce.orderservice.exception;

public interface ErrorCode {

    int getStatus();

    String getMessage();

    String getCode();
}
