package com.github.abigail830.ecommerce.ordercontext.application;

import com.github.abigail830.ecommerce.ordercontext.application.dto.OrderCSVResultSetExtractor;
import com.github.abigail830.ecommerce.ordercontext.application.dto.OrderSummaryResponse;
import com.github.abigail830.ecommerce.ordercontext.domain.order.OrderRepository;
import com.github.abigail830.ecommerce.ordercontext.domain.order.exception.GenericOrderException;
import com.github.abigail830.ecommerce.ordercontext.domain.order.exception.OrderErrorCode;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;
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

    private static final Integer MAX_ORDER_QUERY_LIMIT = 1000000;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 方式一：经过domain的repository
     */
    @Transactional
    public List<OrderSummaryResponse> listOrders(int pageIndex, int pageSize) {
        final List<Order> orders = orderRepository.listOrdersWithPaging(pageIndex, pageSize);
        return orders.stream().map(OrderSummaryResponse::new).collect(Collectors.toList());
    }

    /**
     * 方式二：直接使用JdbcTemplate
     * <p>
     * 另外，这例子里面把数据库查询结果导出为CSV，从数据库直接"select *"导出大量数据到内存可能导致OOM：
     * 优化思路：
     * - 先查询Count数量再进行查询，对Max count有限制或使用分页查询
     * - 返回结果使用StreamingResponseBody, 每个resultSet flush到output
     */
    public StreamingResponseBody getAllOrderAsCSV() {
        validateQueryCount();
        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                log.info("Going to query all orders");
                String sql = "SELECT * FROM ORDER_TBL";
                jdbcTemplate.query(sql, new OrderCSVResultSetExtractor(outputStream));
            }
        };
    }

    private void validateQueryCount() {
        String countSql = "SELECT count(ID) FROM ORDER_TBL";
        final Integer orderCount = jdbcTemplate.queryForObject(countSql, Integer.class);
        if (orderCount > MAX_ORDER_QUERY_LIMIT) {
            throw new GenericOrderException(OrderErrorCode.EXCEED_MAX_ORDER_FOR_REPORT);
        }
    }
}
