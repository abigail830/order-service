package com.github.abigail830.ecommerce.ordercontext.application;

import com.github.abigail830.ecommerce.ordercontext.application.dto.*;
import com.github.abigail830.ecommerce.ordercontext.domain.order.OrderPaymentService;
import com.github.abigail830.ecommerce.ordercontext.domain.order.OrderRepository;
import com.github.abigail830.ecommerce.ordercontext.domain.order.exception.OrderNotFoundException;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.OrderFactory;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderApplService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderFactory orderFactory;

    @Autowired
    OrderPaymentService orderPaymentService;

    @Transactional
    public String createOrder(CreateOrderRequest createOrderRequest) {
        List<OrderItem> items = createOrderRequest.toOrderItems();
        Order order = orderFactory.create(items, createOrderRequest.toAddress());
        orderRepository.save(order);
        log.info("Created order[{}].", order.getId());
        return order.getId();
    }

    @Transactional
    public void changeProductCount(String id, String productId, Integer count) {
        Order order = orderRepository.byId(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.changeProductCount(productId, count);
        orderRepository.save(order);
    }

    @Transactional
    public void pay(String id, PayOrderRequest payOrderRequest) {
        Order order = orderRepository.byId(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderPaymentService.pay(order, payOrderRequest.getPaidPrice());
        orderRepository.save(order);
    }

    @Transactional
    public void changeAddress(String id, ChangeAddressDetailRequest changeAddressDetailRequest) {
        Order order = orderRepository.byId(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.changeAddress(changeAddressDetailRequest.toAddress());
        orderRepository.save(order);
    }

    @Transactional
    public List<OrderSummaryResponse> listOrders(int pageIndex, int pageSize) {
        final List<Order> orders = orderRepository.listOrdersWithPaging(pageIndex, pageSize);
        return orders.stream().map(OrderSummaryResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public List<OrderItemDTO> getItemsByOrderId(String id) {
        return orderRepository.itemsByOrderId(id).stream()
                .map(OrderItemDTO::of).collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse getOrderById(String id) {
        final Order order = orderRepository.byId(id).orElseThrow(() -> new OrderNotFoundException(id));
        return OrderResponse.of(order);
    }

}
