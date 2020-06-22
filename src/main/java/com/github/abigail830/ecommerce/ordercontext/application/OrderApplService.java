package com.github.abigail830.ecommerce.ordercontext.application;

import com.github.abigail830.ecommerce.ordercontext.application.dto.ChangeAddressDetailRequest;
import com.github.abigail830.ecommerce.ordercontext.application.dto.CreateOrderRequest;
import com.github.abigail830.ecommerce.ordercontext.application.dto.PayOrderRequest;
import com.github.abigail830.ecommerce.ordercontext.domain.order.Order;
import com.github.abigail830.ecommerce.ordercontext.domain.order.OrderFactory;
import com.github.abigail830.ecommerce.ordercontext.domain.order.OrderItem;
import com.github.abigail830.ecommerce.ordercontext.domain.order.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class OrderApplService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderFactory orderFactory;
    @Autowired
    DeliveryTicketProxy deliveryTicketProxy;
    @Autowired
    InventoryProxy inventoryProxy;
    @Autowired
    PaymentProxy paymentProxy;

    @Transactional
    public String createOrder(CreateOrderRequest createOrderRequest) {
        List<OrderItem> items = createOrderRequest.toOrderItems();
        Order order = orderFactory.create(items, createOrderRequest.toAddress());
        inventoryProxy.reduceProductInventory(order.getItems());

        orderRepository.save(order);
        log.info("Created order[{}].", order.getId());
        return order.getId();
    }

    @Transactional
    public void pay(String id, PayOrderRequest payOrderRequest) {
        Order order = orderRepository.byId(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        order.pay(payOrderRequest.getPaidPrice());
        paymentProxy.pay(order.getId(), payOrderRequest.getPaidPrice());
        deliveryTicketProxy.create(order);

        orderRepository.save(order);
        log.info("Order[{}] paid.", order.getId());
    }

    @Transactional
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

    @Transactional
    public void changeAddress(String id, ChangeAddressDetailRequest changeAddressDetailRequest) {
        Order order = orderRepository.byId(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.changeAddress(changeAddressDetailRequest.toAddress());
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
