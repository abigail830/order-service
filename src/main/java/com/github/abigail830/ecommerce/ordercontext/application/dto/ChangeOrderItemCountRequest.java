package com.github.abigail830.ecommerce.ordercontext.application.dto;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Value
public class ChangeOrderItemCountRequest {

    @NotBlank(message = "产品ID不能为空")
    private String orderItemId;

    @Min(value = 1, message = "产品数量必须大于0")
    private int count;

}
