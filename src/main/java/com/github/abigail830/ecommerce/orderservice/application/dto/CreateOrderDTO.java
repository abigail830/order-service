package com.github.abigail830.ecommerce.orderservice.application.dto;

import com.github.abigail830.ecommerce.orderservice.domain.order.Address;
import com.github.abigail830.ecommerce.orderservice.domain.order.OrderItem;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class CreateOrderDTO {
    @Valid
    @NotEmpty(message = "订单项不能为空")
    private List<OrderItemDTO> items;

    @NotNull(message = "订单地址不能为空")
    private Address address;

    public List<OrderItem> getOrderItems() {
        return items.stream()
                .map(item -> OrderItem.create(item.getProductId(),
                        item.getCount(),
                        item.getItemPrice()))
                .collect(Collectors.toList());
    }

}
