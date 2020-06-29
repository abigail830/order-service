package com.github.abigail830.ecommerce.ordercontext.exception;

import com.github.abigail830.ecommerce.ordercontext.domain.order.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static com.google.common.collect.ImmutableMap.of;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> notFoundHandler(OrderNotFoundException e) {
        return e.getErrorResponse();
    }

    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> bizExceptionHandler(BizException e) {
        log.warn("Exception with error {}", e.getError());
        return e.getErrorResponse();
    }

    @ExceptionHandler(value = InterruptedException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public Map<String, Object> asyncTimeoutException(InterruptedException e) {
        log.warn("Exception with async timeout error {}", e);
        return of("errorMessage", "InterruptedException: " + e.getMessage());
    }
}
