package com.github.abigail830.ecommerce.ordercontext.exception;

public interface ErrorCode {

    int getStatus();

    String getMessage();

    String getCode();
}
