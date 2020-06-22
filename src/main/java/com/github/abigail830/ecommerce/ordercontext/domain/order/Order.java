package com.github.abigail830.ecommerce.ordercontext.domain.order;


import com.github.abigail830.ecommerce.ordercontext.domain.order.exception.OrderCannotBeModifiedException;
import com.github.abigail830.ecommerce.ordercontext.domain.order.exception.OrderStatusInvalidException;
import com.github.abigail830.ecommerce.ordercontext.domain.order.exception.PaidPriceNotSameWithOrderPriceException;
import com.github.abigail830.ecommerce.ordercontext.domain.order.exception.ProductNotInOrderException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static com.github.abigail830.ecommerce.ordercontext.domain.order.OrderStatus.*;
import static java.math.BigDecimal.ZERO;
import static java.time.Instant.now;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {

    private String id;
    private List<OrderItem> items;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private Address address;
    private Instant createdAt;

    public Order(String id, List<OrderItem> items, BigDecimal totalPrice, OrderStatus status,
                 Address address, Instant createdAt) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
        this.status = status;
        this.address = address;
        this.createdAt = createdAt;
    }

    public static Order create(String id, List<OrderItem> items, Address address) {
        return new Order(id, items, calculateTotalPrice(items), CREATED, address, now());
    }

    public static Order restore(String id, List<OrderItem> items, BigDecimal totalPrice, String status,
                                Address address, Timestamp createAt) {
        return new Order(id, items, totalPrice, OrderStatus.valueOf(status), address, createAt.toInstant());
    }

    public static Order restoreSummary(String id, BigDecimal totalPrice, String status,
                                       Address address, Timestamp createAt) {
        return new Order(id, null, totalPrice, OrderStatus.valueOf(status), address, createAt.toInstant());
    }

    private static BigDecimal calculateTotalPrice(List<OrderItem> items) {
        return items.stream()
                .map(OrderItem::totalPrice)
                .reduce(ZERO, BigDecimal::add);
    }


    public void changeProductCount(String productId, int count) {
        if (this.status == PAID || this.status == DELIVER_SIGN || this.status == DELIVER_CONFIRM) {
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

    public void changeAddress(Address address) {
        if (this.status == PAID) {
            throw new OrderCannotBeModifiedException(this.id);
        }
        this.address = address;
    }

    public void cancel() {
        if (this.status == PAID || this.status == DELIVER_SIGN || this.status == DELIVER_CONFIRM) {
            throw new OrderCannotBeModifiedException(this.id);
        }
        this.status = CANCELLED;
    }

    public void signForDelivered() {
        if (this.status != PAID) {
            throw new OrderStatusInvalidException(this.id);
        }
        this.status = DELIVER_SIGN;
    }

    public void confirmForReceive() {
        if (this.status == DELIVER_SIGN || this.status == PAID) {
            this.status = DELIVER_CONFIRM;
        } else {
            throw new OrderStatusInvalidException(this.id);
        }
    }
}
