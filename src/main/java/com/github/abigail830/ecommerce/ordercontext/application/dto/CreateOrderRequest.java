package com.github.abigail830.ecommerce.ordercontext.application.dto;

import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Address;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.OrderItem;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class CreateOrderRequest {

    @Valid
    @NotEmpty(message = "订单项不能为空")
    private List<OrderItemDTO> items;

    @NotNull(message = "订单地址不能为空")
    private AddressDTO addressDTO;

    public List<OrderItem> toOrderItems() {
        return items.stream().map(OrderItemDTO::toOrderItem).collect(Collectors.toList());
    }

    public Address toAddress() {
        return addressDTO.toAddress();
    }


}
