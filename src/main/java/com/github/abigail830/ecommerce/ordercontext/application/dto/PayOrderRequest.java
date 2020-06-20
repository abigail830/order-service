package com.github.abigail830.ecommerce.ordercontext.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderRequest {

    @NotNull(message = "支付金额不能为空")
    private BigDecimal paidPrice;

}
