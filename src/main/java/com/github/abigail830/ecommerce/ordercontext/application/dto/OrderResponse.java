package com.github.abigail830.ecommerce.ordercontext.application.dto;

import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;
import lombok.Value;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Value
public class OrderResponse {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                    .withLocale(Locale.CHINA)
                    .withZone(ZoneId.systemDefault());
    private String id;
    private List<OrderItemDTO> itemDTOs;
    private BigDecimal totalPrice;
    private String status;
    private String address;
    private String createdAt;

    public static OrderResponse of(Order order) {
        return new OrderResponse(order.getId(),
                order.getItems().stream().map(OrderItemDTO::of).collect(Collectors.toList()),
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getAddress().combine(),
                formatter.format(order.getCreatedAt()));
    }
}
