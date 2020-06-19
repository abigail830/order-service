package com.github.abigail830.ecommerce.ordercontext.infrastructure;

import com.github.abigail830.ecommerce.ordercontext.domain.order.OrderRepository;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

//    @Autowired
//    OrderMapper orderMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<Order> rowMapper = new BeanPropertyRowMapper<>(Order.class);

    @Override
    public void save(Order order) {
        String insertSql = "INSERT INTO ORDER (ID, STATUS, TOTAL_PRICE, CREATED_AT) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertSql,
                order.getId(), order.getStatus(), order.getTotalPrice(), order.getCreatedAt());
        log.info("Order {} saved", order);
    }

    @Override
    public Optional<Order> byId(String id) {
        String selectByIdSql = "SELECT * FROM ORDER WHERE ID = ?";
        final List<Order> orders = jdbcTemplate.query(selectByIdSql, rowMapper, id);
        return orders.stream().findFirst();
    }
}
