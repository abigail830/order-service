package com.github.abigail830.ecommerce.ordercontext.api;

import com.github.abigail830.ecommerce.ordercontext.application.OrderPresentationApplService;
import com.github.abigail830.ecommerce.ordercontext.application.dto.OrderSummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportingController {


    @Autowired
    OrderPresentationApplService orderPresentationApplService;

    @GetMapping("/order-summary")
    public List<OrderSummaryResponse> pagedOrderSummary(@RequestParam(required = false, defaultValue = "1") int pageIndex,
                                                        @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return orderPresentationApplService.listOrders(pageIndex, pageSize);
    }
}
