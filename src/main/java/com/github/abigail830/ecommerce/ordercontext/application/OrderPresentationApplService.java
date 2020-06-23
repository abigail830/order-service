package com.github.abigail830.ecommerce.ordercontext.application;

import com.github.abigail830.ecommerce.ordercontext.application.dto.OrderItemDTO;
import com.github.abigail830.ecommerce.ordercontext.application.dto.OrderResponse;
import com.github.abigail830.ecommerce.ordercontext.application.dto.OrderSummaryResponse;
import com.github.abigail830.ecommerce.ordercontext.domain.order.OrderRepository;
import com.github.abigail830.ecommerce.ordercontext.domain.order.exception.OrderNotFoundException;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderPresentationApplService {

    @Autowired
    OrderRepository orderRepository;

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
