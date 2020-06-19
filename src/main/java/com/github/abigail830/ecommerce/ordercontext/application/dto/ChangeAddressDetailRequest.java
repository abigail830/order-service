package com.github.abigail830.ecommerce.ordercontext.application.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ChangeAddressDetailRequest {

    @NotNull(message = "详细地址不能为空")
    private String detail;

}
