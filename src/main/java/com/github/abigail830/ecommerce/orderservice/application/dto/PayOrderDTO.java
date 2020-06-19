package com.github.abigail830.ecommerce.orderservice.application.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class PayOrderDTO {

    @NotNull(message = "支付金额不能为空")
    private BigDecimal paidPrice;

}
