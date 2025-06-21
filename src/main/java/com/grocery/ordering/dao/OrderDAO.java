package com.grocery.ordering.dao;

import com.grocery.ordering.model.Order;
import com.grocery.ordering.model.OrderItem;
import com.grocery.ordering.util.DatabaseManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public Order createOrder(Order order) throws SQLException {
        String orderSql = "INSERT INTO Orders(CustomerID, TotalAmount, OrderDate) VALUES(?, ?, ?)";
        String orderItemSql = "INSERT INTO OrderItems(OrderID, ProductID, Quantity, Price) VALUES(?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = DatabaseManager.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Insert into Orders table
            try (PreparedStatement orderPstmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                orderPstmt.setInt(1, order.getCustomerId());
                orderPstmt.setDouble(2, order.getTotalAmount());
                orderPstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                orderPstmt.executeUpdate();

                try (ResultSet generatedKeys = orderPstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setOrderId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating order failed, no ID obtained.");
                    }
                }
            }

            // Insert into OrderItems table
            try (PreparedStatement orderItemPstmt = conn.prepareStatement(orderItemSql)) {
                for (OrderItem item : order.getItems()) {
                    orderItemPstmt.setInt(1, order.getOrderId());
                    orderItemPstmt.setInt(2, item.getProductId());
                    orderItemPstmt.setInt(3, item.getQuantity());
                    orderItemPstmt.setDouble(4, item.getPrice());
                    orderItemPstmt.addBatch();
                }
                orderItemPstmt.executeBatch();
            }

            conn.commit(); // Commit transaction
            return order;

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Rollback on error
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public List<Order> findByCustomerId(int customerId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String orderSql = "SELECT * FROM Orders WHERE CustomerID = ? ORDER BY OrderDate DESC";
        String itemSql = "SELECT * FROM OrderItems WHERE OrderID = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement orderPstmt = conn.prepareStatement(orderSql)) {

            orderPstmt.setInt(1, customerId);
            try (ResultSet rs = orderPstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("OrderID"));
                    order.setCustomerId(rs.getInt("CustomerID"));
                    order.setTotalAmount(rs.getDouble("TotalAmount"));
                    order.setOrderDate(rs.getTimestamp("OrderDate").toLocalDateTime());
                    orders.add(order);
                }
            }

            // For each order, fetch its items
            for (Order order : orders) {
                List<OrderItem> items = new ArrayList<>();
                try (PreparedStatement itemPstmt = conn.prepareStatement(itemSql)) {
                    itemPstmt.setInt(1, order.getOrderId());
                    try (ResultSet itemRs = itemPstmt.executeQuery()) {
                        while (itemRs.next()) {
                            OrderItem item = new OrderItem();
                            item.setOrderItemId(itemRs.getInt("OrderItemID"));
                            item.setOrderId(itemRs.getInt("OrderID"));
                            item.setProductId(itemRs.getInt("ProductID"));
                            item.setQuantity(itemRs.getInt("Quantity"));
                            item.setPrice(itemRs.getDouble("Price"));
                            items.add(item);
                        }
                    }
                }
                order.setItems(items);
            }
        }
        return orders;
    }
} 