package com.github.abigail830.ecommerce.ordercontext.application.dto;

import com.github.abigail830.ecommerce.ordercontext.domain.order.Order;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
public class OrderSummaryResponse {
    private String id;
    private BigDecimal totalPrice;
    private String status;
    private Instant createdAt;
    private AddressDTO addressDTO;

    public OrderSummaryResponse(Order order) {
        this.id = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus().name();
        this.createdAt = order.getCreatedAt();
        this.addressDTO = AddressDTO.of(order.getAddress());
    }
}
