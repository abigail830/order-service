package com.github.abigail830.ecommerce.ordercontext.application;

import com.github.abigail830.ecommerce.ordercontext.application.dto.OrderSummaryResponse;
import com.github.abigail830.ecommerce.ordercontext.domain.order.OrderRepository;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 报表查询功能： 有时候除了业务逻辑相关的查询，还会需要导出报表，这时候经常涉及跨领域的查询操作
 * <p>
 * 处理方式一：依然经过Domain的repository查询，然后内存进行拼合和转换
 * 处理方式二：直接在ApplService就引入JdbcTemplate，越过domain层直接进行数据库join和查询
 * 处理方式三：更彻底读写分离，写库更新时候触发event更新读库，读操作指向读库直接在ApplService连数据库查询
 */
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


}
