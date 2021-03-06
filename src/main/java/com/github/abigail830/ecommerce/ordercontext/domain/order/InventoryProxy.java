package com.github.abigail830.ecommerce.ordercontext.domain.order;

import com.github.abigail830.ecommerce.ordercontext.domain.order.model.OrderItem;

import java.util.List;

public interface InventoryProxy {

    void reduceProductInventory(List<OrderItem> items);
}
