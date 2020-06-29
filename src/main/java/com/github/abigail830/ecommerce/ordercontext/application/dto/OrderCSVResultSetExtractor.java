package com.github.abigail830.ecommerce.ordercontext.application.dto;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class OrderCSVResultSetExtractor implements ResultSetExtractor {

    final private static char DELIMITER = ',';

    private OutputStream outputStream;

    public OrderCSVResultSetExtractor(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    private static void writeHeader(final PrintWriter printWriter) throws SQLException {
        printWriter.write("订单号");
        printWriter.append(DELIMITER);
        printWriter.write("订单总价");
        printWriter.append(DELIMITER);
        printWriter.write("订单状态");
        printWriter.append(DELIMITER);
        printWriter.write("订单创建时间");
        printWriter.append(DELIMITER);
        printWriter.write("订单寄件地址");
        printWriter.println();
    }

    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            writeHeader(printWriter);
            while (resultSet.next()) {
                String orderId = resultSet.getString("ID");
                String totalPrice = resultSet.getString("TOTAL_PRICE");
                String status = resultSet.getString("STATUS");
                String province = resultSet.getString("PROVINCE");
                String city = resultSet.getString("CITY");
                String detailAddress = resultSet.getString("DETAIL");
                String createdAt = resultSet.getString("CREATED_AT");

                printWriter.write(orderId != null ? orderId : "");
                printWriter.append(DELIMITER);
                printWriter.write(totalPrice != null ? totalPrice : "");
                printWriter.append(DELIMITER);
                printWriter.write(status != null ? status : "");
                printWriter.append(DELIMITER);
                printWriter.write(createdAt != null ? createdAt : "");
                printWriter.append(DELIMITER);
                printWriter.write(getAddress(province, city, detailAddress));
                printWriter.println();
            }
            printWriter.flush();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    private String getAddress(String province, String city, String detailAddress) {
        final String provinceText = province != null ? province : "";
        final String cityText = city != null ? city : "";
        final String detailAddrText = detailAddress != null ? detailAddress : "";
        return provinceText + "," + cityText + "," + detailAddrText;
    }
}
