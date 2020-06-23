package com.github.abigail830.ecommerce.ordercontext.application;

import com.github.abigail830.ecommerce.ordercontext.application.dto.ChangeAddressDetailRequest;
import com.github.abigail830.ecommerce.ordercontext.application.dto.CreateOrderRequest;
import com.github.abigail830.ecommerce.ordercontext.application.dto.PayOrderRequest;
import com.github.abigail830.ecommerce.ordercontext.domain.order.OrderService;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class OrderApplService {

    @Autowired
    OrderService orderService;

    @Autowired
    DeliveryTicketProxy deliveryTicketProxy;
    @Autowired
    InventoryProxy inventoryProxy;
    @Autowired
    PaymentProxy paymentProxy;

    @Transactional
    public String createOrder(CreateOrderRequest createOrderRequest) {
        List<OrderItem> items = createOrderRequest.toOrderItems();
        final Order order = orderService.createOrder(items, createOrderRequest.toAddress());
        inventoryProxy.reduceProductInventory(order.getItems());
        log.info("Created order[{}].", order.getId());
        return order.getId();
    }

    @Transactional
    public void pay(String id, PayOrderRequest payOrderRequest) {
        final Order paidOrder = orderService.pay(id, payOrderRequest.getPaidPrice());
        paymentProxy.pay(paidOrder.getId(), payOrderRequest.getPaidPrice());
        deliveryTicketProxy.create(paidOrder);
        log.info("Order[{}] paid.", paidOrder.getId());
    }

    @Transactional
    public void changeProductCount(String id, String productId, Integer count) {
        orderService.changeProductCount(id, productId, count);
        log.info("Order[{}] updated productId[{}] with count{}.", id, productId, count);
    }

    @Transactional
    public void cancel(String id) {
        orderService.cancel(id);
        log.info("Order[{}] cancelled", id);
    }

    @Transactional
    public void changeAddress(String id, ChangeAddressDetailRequest changeAddressDetailRequest) {
        orderService.changeAddress(id, changeAddressDetailRequest.toAddress());
        log.info("Order[{}] address changed to Address[{}]", id, changeAddressDetailRequest.toAddress());
    }

    public void signForReceive(String id) {
        orderService.signForReceive(id);
        log.info("Order[{}] signed for receive ", id);
    }

    public void confirmForReceive(String id) {
        orderService.confirmForReceive(id);
        log.info("Order[{}] confirmed for receive ", id);

    }
}
