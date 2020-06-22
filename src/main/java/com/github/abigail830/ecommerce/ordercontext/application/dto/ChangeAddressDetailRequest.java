package com.github.abigail830.ecommerce.ordercontext.application.dto;

import com.github.abigail830.ecommerce.ordercontext.domain.order.Address;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ChangeAddressDetailRequest {

    @NotNull(message = "详细地址不能为空")
    private String detail;

    @NotNull(message = "城市不能为空")
    private String city;

    @NotNull(message = "省份不能为空")
    private String province;

    public Address toAddress() {
        return Address.of(province, city, detail);
    }

}
