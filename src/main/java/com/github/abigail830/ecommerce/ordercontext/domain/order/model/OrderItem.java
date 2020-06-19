package com.github.abigail830.ecommerce.ordercontext.domain.order.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItem {
    private String productId;
    private int count;
    private BigDecimal itemPrice;

    public OrderItem(String productId, int count, BigDecimal itemPrice) {
        this.productId = productId;
        this.count = count;
        this.itemPrice = itemPrice;
    }


    public static OrderItem create(String productId, int count, BigDecimal itemPrice) {
        return new OrderItem(productId, count, itemPrice);
    }

    BigDecimal totalPrice() {
        return itemPrice.multiply(BigDecimal.valueOf(count));
    }

    public void updateCount(int count) {
        this.count = count;
    }

    public String getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }
}
