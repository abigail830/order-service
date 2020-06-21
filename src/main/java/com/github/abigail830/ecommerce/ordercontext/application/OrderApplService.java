package com.github.abigail830.ecommerce.ordercontext.application;

import com.github.abigail830.ecommerce.ordercontext.application.dto.ChangeAddressDetailRequest;
import com.github.abigail830.ecommerce.ordercontext.application.dto.CreateOrderRequest;
import com.github.abigail830.ecommerce.ordercontext.application.dto.PayOrderRequest;
import com.github.abigail830.ecommerce.ordercontext.domain.order.OrderRepository;
import com.github.abigail830.ecommerce.ordercontext.domain.order.OrderService;
import com.github.abigail830.ecommerce.ordercontext.domain.order.exception.OrderNotFoundException;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.OrderFactory;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.OrderItem;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderApplService {

    OrderRepository orderRepository;

    OrderFactory orderFactory;

    private final ScheduledExecutorService scheduledExecutorService
            = Executors.newScheduledThreadPool(1,
            new ThreadFactoryBuilder().setNameFormat("order-auto-scan-thread-%d").build());
    OrderService orderService;

    @Autowired
    public OrderApplService(OrderRepository orderRepository,
                            OrderFactory orderFactory,
                            OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderFactory = orderFactory;
        this.orderService = orderService;
        scheduledExecutorService.scheduleWithFixedDelay(this::scanExpiredOrder, 1, 2, TimeUnit.HOURS);
    }

    private void scanExpiredOrder() {

    }

    @Transactional
    public String createOrder(CreateOrderRequest createOrderRequest) {
        List<OrderItem> items = createOrderRequest.toOrderItems();
        Order order = orderFactory.create(items, createOrderRequest.toAddress());
        orderService.reduceInventoryForItems(order);
        orderRepository.save(order);
        log.info("Created order[{}].", order.getId());
        return order.getId();
    }

    @Transactional
    public void pay(String id, PayOrderRequest payOrderRequest) {
        Order order = orderRepository.byId(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderService.pay(order, payOrderRequest.getPaidPrice());
        orderService.createDeliveryTicket(order);
        orderRepository.save(order);
        log.info("Order[{}] payed.", order.getId());
    }

    @Transactional
    public void changeProductCount(String id, String productId, Integer count) {
        Order order = orderRepository.byId(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.changeProductCount(productId, count);
        orderRepository.save(order);
    }


    @Transactional
    public void changeAddress(String id, ChangeAddressDetailRequest changeAddressDetailRequest) {
        Order order = orderRepository.byId(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.changeAddress(changeAddressDetailRequest.toAddress());
        orderRepository.save(order);
    }

    public void cancel(String id) {

    }

    public void signForReceive(String id) {

    }

    public void confirmForReceive(String id) {

    }
}
