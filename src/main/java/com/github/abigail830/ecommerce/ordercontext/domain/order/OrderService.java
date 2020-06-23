package com.github.abigail830.ecommerce.ordercontext.domain.order;

import com.github.abigail830.ecommerce.ordercontext.domain.order.exception.OrderNotFoundException;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Address;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.OrderFactory;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderFactory orderFactory;

    public Order createOrder(List<OrderItem> items, Address address) {
        Order order = orderFactory.create(items, address);
        orderRepository.save(order);
        return order;
    }

    public Order pay(String id, BigDecimal paidPrice) {
        Order order = orderRepository.byId(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.pay(paidPrice);
        orderRepository.save(order);
        return order;
    }

    public void changeProductCount(String id, String productId, Integer count) {
        Order order = orderRepository.byId(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.changeProductCount(productId, count);
        orderRepository.save(order);
    }

    public void cancel(String id) {
        Order order = orderRepository.byId(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.cancel();
        orderRepository.save(order);
    }

    public void changeAddress(String id, Address address) {
        Order order = orderRepository.byId(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.changeAddress(address);
        orderRepository.save(order);
    }

    public void signForReceive(String id) {
        Order order = orderRepository.byId(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.signForDelivered();
        orderRepository.save(order);
    }

    public void confirmForReceive(String id) {
        Order order = orderRepository.byId(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.confirmForReceive();
        orderRepository.save(order);
    }
}
