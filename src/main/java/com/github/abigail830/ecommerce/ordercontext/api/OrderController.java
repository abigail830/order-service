package com.github.abigail830.ecommerce.ordercontext.api;

import com.github.abigail830.ecommerce.ordercontext.application.OrderApplService;
import com.github.abigail830.ecommerce.ordercontext.application.dto.ChangeAddressDetailRequest;
import com.github.abigail830.ecommerce.ordercontext.application.dto.CreateOrderRequest;
import com.github.abigail830.ecommerce.ordercontext.application.dto.PayOrderRequest;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableMap.of;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderApplService orderApplService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Map<String, String> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        return of("id", orderApplService.createOrder(createOrderRequest));
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable(name = "id") String id) {
        return orderApplService.getOrderById(id);
    }

    @GetMapping("/{id}/items")
    public List<OrderItem> getOrderItemsByOrderId(@PathVariable(name = "id") String id) {
        return orderApplService.getOrderItemsByOrderId(id);
    }

    @PostMapping("/{id}/items/{productId}")
    public void changeProductCount(@PathVariable(name = "id") String id,
                                   @PathVariable(name = "productId") String productId,
                                   @RequestBody Integer count) {
        orderApplService.changeProductCount(id, productId, count);
    }

    @PostMapping("/{id}/payment")
    public void pay(@PathVariable(name = "id") String id,
                    @RequestBody @Valid PayOrderRequest payOrderRequest) {
        orderApplService.pay(id, payOrderRequest);
    }

    @PostMapping("/{id}/address")
    public void changeAddressDetail(@PathVariable(name = "id") String id,
                                    @RequestBody @Valid ChangeAddressDetailRequest changeAddressDetailRequest) {
        orderApplService.changeAddress(id, changeAddressDetailRequest);
    }

//    @GetMapping("/{id}")
//    public OrderRepresentation byId(@PathVariable(name = "id") String id) {
//        return representationService.byId(id);
//    }
//
//    @GetMapping
//    public PagedResource<OrderSummaryRepresentation> pagedProducts(@RequestParam(required = false, defaultValue = "1") int pageIndex,
//                                                                   @RequestParam(required = false, defaultValue = "10") int pageSize) {
//        return representationService.listOrders(pageIndex, pageSize);
//    }
}
