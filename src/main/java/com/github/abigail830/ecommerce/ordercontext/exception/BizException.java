package com.github.abigail830.ecommerce.ordercontext.exception;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static org.springframework.util.StringUtils.isEmpty;

public class BizException extends RuntimeException {

    private final ErrorCode error;
    private final Map<String, Object> data = newHashMap();

    protected BizException(ErrorCode error, Map<String, Object> data) {
        super(format(error.getCode(), error.getMessage(), data));
        this.error = error;
        if (!isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    protected BizException(ErrorCode code, Map<String, Object> data, Throwable cause) {
        super(format(code.toString(), code.getMessage(), data), cause);
        this.error = code;
        if (!isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    private static String format(String code, String message, Map<String, Object> data) {
        return String.format("[%s]%s:%s.", code, message, isEmpty(data) ? "" : data.toString());
    }

    public ErrorCode getError() {
        return error;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Map<String, Object> getErrorResponse() {
        Map<String, Object> result = data;
        result.put("errorCode", error.getCode());
        result.put("errorMessage", error.getMessage());
        return result;
    }
}
