package com.github.abigail830.ecommerce.orderservice.application;

import com.github.abigail830.ecommerce.orderservice.application.dto.ChangeProductCountDTO;
import com.github.abigail830.ecommerce.orderservice.application.dto.CreateOrderDTO;
import com.github.abigail830.ecommerce.orderservice.application.dto.PayOrderDTO;
import com.github.abigail830.ecommerce.orderservice.domain.order.Order;
import com.github.abigail830.ecommerce.orderservice.domain.order.OrderFactory;
import com.github.abigail830.ecommerce.orderservice.domain.order.OrderItem;
import com.github.abigail830.ecommerce.orderservice.domain.order.OrderRepository;
import com.github.abigail830.ecommerce.orderservice.domain.payment.OrderPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderApplService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderFactory orderFactory;

    @Autowired
    OrderPaymentService orderPaymentService;

    public String createOrder(CreateOrderDTO createOrderDTO) {
        List<OrderItem> items = createOrderDTO.getOrderItems();
        Order order = orderFactory.create(items, createOrderDTO.getAddress());
        orderRepository.save(order);
        log.info("Created order[{}].", order.getId());
        return order.getId();
    }

    public void changeProductCount(String id, ChangeProductCountDTO changeProductCountDTO) {
        Order order = orderRepository.byId(id);
        order.changeProductCount(changeProductCountDTO.getProductId(), changeProductCountDTO.getCount());
        orderRepository.save(order);
    }

    public void pay(String id, PayOrderDTO payOrderDTO) {
        Order order = orderRepository.byId(id);
        orderPaymentService.pay(order, payOrderDTO.getPaidPrice());
        orderRepository.save(order);
    }

    public void changeAddressDetail(String id, String detail) {
        Order order = orderRepository.byId(id);
        order.changeAddressDetail(detail);
        orderRepository.save(order);
    }
}
