package com.github.abigail830.ecommerce.ordercontext.api;

import com.github.abigail830.ecommerce.ordercontext.application.OrderPresentationApplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/csv/reports")
public class CsvReportingController {

    @Autowired
    OrderPresentationApplService orderPresentationApplService;

    @GetMapping(value = "/order-summary", produces = "text/csv")
    public ResponseEntity<StreamingResponseBody> csvOrderSummaryReport(final HttpServletResponse response) {
        response.setContentType("text/csv");
        response.setHeader(
                "Content-Disposition",
                "attachment;filename=download.csv");
        return new ResponseEntity(orderPresentationApplService.getAllOrderAsCSV(), HttpStatus.OK);
    }
}
