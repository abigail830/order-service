package com.github.abigail830.ecommerce.ordercontext.infrastructure;

import com.github.abigail830.ecommerce.ordercontext.domain.order.OrderRepository;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Address;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Order;
import com.github.abigail830.ecommerce.ordercontext.domain.order.model.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<Order> rowMapper = new BeanPropertyRowMapper<>(Order.class);

    private RowMapper<OrderItem> orderItemRowMapper = new BeanPropertyRowMapper<>(OrderItem.class);

    @Override
    public void save(Order order) {
        String insertSql = "INSERT INTO ORDER_TBL (ID, STATUS, TOTAL_PRICE, CREATED_AT, PROVINCE, CITY, DETAIL) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE STATUS=?, TOTAL_PRICE=?, PROVINCE=?, CITY=?, DETAIL=?";
        jdbcTemplate.update(insertSql,
                order.getId(), order.getStatus().name(), order.getTotalPrice(), order.getCreatedAt(),
                order.getAddress().getProvince(), order.getAddress().getCity(), order.getAddress().getDetail(),
                order.getStatus().name(), order.getTotalPrice(), order.getAddress().getProvince(),
                order.getAddress().getCity(), order.getAddress().getDetail());

        String insertItemSql = "INSERT INTO ORDER_ITEM_TBL (ORDER_ID, PRODUCT_ID, COUNT, PRICE) " +
                "VALUES (?,?,?,?) " +
                "ON DUPLICATE KEY UPDATE COUNT=?, PRICE=?";
        jdbcTemplate.batchUpdate(insertItemSql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, order.getId());
                ps.setString(2, order.getItems().get(i).getProductId());
                ps.setInt(3, order.getItems().get(i).getCount());
                ps.setBigDecimal(4, order.getItems().get(i).getItemPrice());
                ps.setInt(5, order.getItems().get(i).getCount());
                ps.setBigDecimal(6, order.getItems().get(i).getItemPrice());
            }

            public int getBatchSize() {
                return order.getItems().size();
            }
        });

        log.info("Order {} saved", order);
    }

    @Override
    public Optional<Order> byId(String id) {
        String selectItemByIdSql = "SELECT * FROM ORDER_ITEM_TBL WHERE ORDER_ID = ?";
        final List<OrderItem> orderItems = jdbcTemplate.query(selectItemByIdSql, ((resultSet, i) -> OrderItem.create(
                resultSet.getString("PRODUCT_ID"),
                resultSet.getInt("COUNT"),
                resultSet.getBigDecimal("PRICE"))), id);

        String selectOrderByIdSql = "SELECT * FROM ORDER_TBL WHERE ID = ?";
        final List<Order> orders = jdbcTemplate.query(selectOrderByIdSql, (resultSet, i) -> Order.restore(
                resultSet.getString("ID"),
                orderItems,
                resultSet.getBigDecimal("TOTAL_PRICE"),
                resultSet.getString("STATUS"),
                Address.of(resultSet.getString("PROVINCE"),
                        resultSet.getString("CITY"),
                        resultSet.getString("DETAIL")),
                resultSet.getTimestamp("CREATED_AT")), id);

        return orders.stream().findFirst();
    }

    @Override
    public List<OrderItem> itemsByOrderId(String id) {
        String selectItemByIdSql = "SELECT * FROM ORDER_ITEM_TBL WHERE ORDER_ID = ?";
        return jdbcTemplate.query(selectItemByIdSql, ((resultSet, i) -> OrderItem.create(
                resultSet.getString("PRODUCT_ID"),
                resultSet.getInt("COUNT"),
                resultSet.getBigDecimal("PRICE"))), id);
    }


}
