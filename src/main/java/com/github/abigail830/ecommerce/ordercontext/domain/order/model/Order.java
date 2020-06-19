package com.github.abigail830.ecommerce.ordercontext.domain.order.model;


import com.github.abigail830.ecommerce.ordercontext.domain.order.exception.OrderCannotBeModifiedException;
import com.github.abigail830.ecommerce.ordercontext.domain.order.exception.PaidPriceNotSameWithOrderPriceException;
import com.github.abigail830.ecommerce.ordercontext.domain.order.exception.ProductNotInOrderException;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static com.github.abigail830.ecommerce.ordercontext.domain.order.model.OrderStatus.CREATED;
import static com.github.abigail830.ecommerce.ordercontext.domain.order.model.OrderStatus.PAID;
import static java.math.BigDecimal.ZERO;
import static java.time.Instant.now;

@Getter
@Builder
public class Order {

    private String id;
    private List<OrderItem> items;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private Address address;
    private Instant createdAt;

    public static Order create(String id, List<OrderItem> items, Address address) {
        return Order.builder()
                .id(id)
                .items(items)
                .totalPrice(calculateTotalPrice(items))
                .status(CREATED)
                .address(address)
                .createdAt(now())
                .build();
    }

    private static BigDecimal calculateTotalPrice(List<OrderItem> items) {
        return items.stream()
                .map(OrderItem::totalPrice)
                .reduce(ZERO, BigDecimal::add);
    }


    public void changeOrderItemCount(String productId, int count) {
        if (this.status == PAID) {
            throw new OrderCannotBeModifiedException(this.id);
        }
        OrderItem orderItem = retrieveItem(productId);
        orderItem.updateCount(count);
        this.totalPrice = calculateTotalPrice(items);
    }

    private OrderItem retrieveItem(String productId) {
        return items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotInOrderException(productId, id));
    }

    public void pay(BigDecimal paidPrice) {
        if (!this.totalPrice.equals(paidPrice)) {
            throw new PaidPriceNotSameWithOrderPriceException(id);
        }
        this.status = PAID;

    }

    public void changeAddressDetail(String detail) {
        if (this.status == PAID) {
            throw new OrderCannotBeModifiedException(this.id);
        }
        this.address = this.address.changeDetailTo(detail);
    }

//    public OrderRepresentation toRepresentation() {
//        List<com.ecommerce.order.sdk.representation.order.OrderItem> itemRepresentations = this.getItems().stream()
//                .map(orderItem -> new com.ecommerce.order.sdk.representation.order.OrderItem(orderItem.getProductId(),
//                        orderItem.getCount(),
//                        orderItem.getItemPrice()))
//                .collect(Collectors.toList());
//
//        return new OrderRepresentation(this.getId(),
//                itemRepresentations,
//                this.getTotalPrice(),
//                this.getStatus().name(),
//                this.getAddress(),
//                this.getCreatedAt());
//    }
//
//
//    public OrderSummaryRepresentation toSummary() {
//        return new OrderSummaryRepresentation(this.getId(),
//                this.getTotalPrice(),
//                this.getStatus().name(),
//                this.getCreatedAt(),
//                this.getAddress());
//    }

}
