package com.github.abigail830.ecommerce.ordercontext.infrastructure.client;

import com.github.abigail830.ecommerce.ordercontext.application.InventoryProxy;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class InventoryProxyImpl implements InventoryProxy {

    @Override
    public void reduceProductInventory(List<OrderItem> items) {
        //TODO: 扣减商城库存
        log.info("Reduce inventory for items {}", items);
    }
}
