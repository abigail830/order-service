package com.github.abigail830.ecommerce.ordercontext.infrastructure.client;

import com.github.abigail830.ecommerce.ordercontext.domain.order.DeliveryTicketProxy;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeliveryTicketProxyImpl implements DeliveryTicketProxy {

    @Override
    public void create(Order order) {
        //TODO: 生成出货单
        log.info("Create delivery ticket for Order[{}]", order.getId());
    }
}
