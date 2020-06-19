package com.github.abigail830.ecommerce.orderservice.application.dto;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class OrderItemDTO {
    @NotBlank(message = "产品ID不能为空")
    private String productId;

    @Min(value = 1, message = "产品数量必须大于0")
    private int count;

    @NotNull(message = "产品单价不能为空")
    private BigDecimal itemPrice;

}
