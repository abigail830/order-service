package com.github.abigail830.ecommerce.orderservice.api;

import com.github.abigail830.ecommerce.orderservice.application.OrderApplService;
import com.github.abigail830.ecommerce.orderservice.application.dto.ChangeAddressDetailDTO;
import com.github.abigail830.ecommerce.orderservice.application.dto.ChangeProductCountDTO;
import com.github.abigail830.ecommerce.orderservice.application.dto.CreateOrderDTO;
import com.github.abigail830.ecommerce.orderservice.application.dto.PayOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Map<String, String> createOrder(@RequestBody @Valid CreateOrderDTO createOrderDTO) {
        return of("id", orderApplService.createOrder(createOrderDTO));
    }

    @PostMapping("/{id}/products")
    public void changeProductCount(@PathVariable(name = "id") String id,
                                   @RequestBody @Valid ChangeProductCountDTO changeProductCountDTO) {
        orderApplService.changeProductCount(id, changeProductCountDTO);
    }

    @PostMapping("/{id}/payment")
    public void pay(@PathVariable(name = "id") String id,
                    @RequestBody @Valid PayOrderDTO payOrderDTO) {
        orderApplService.pay(id, payOrderDTO);
    }


    @PostMapping("/{id}/address/detail")
    public void changeAddressDetail(@PathVariable(name = "id") String id,
                                    @RequestBody @Valid ChangeAddressDetailDTO changeAddressDetailDTO) {
        orderApplService.changeAddressDetail(id, changeAddressDetailDTO.getDetail());
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
