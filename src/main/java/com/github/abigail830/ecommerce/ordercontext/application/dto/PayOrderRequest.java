package com.github.abigail830.ecommerce.ordercontext.application.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class PayOrderRequest {

    @NotNull(message = "支付金额不能为空")
    private BigDecimal paidPrice;

}
