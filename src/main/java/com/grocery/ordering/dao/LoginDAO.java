package com.grocery.ordering.dao;

import com.grocery.ordering.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LoginDAO {

    public void recordLogin(int customerId) throws SQLException {
        String sql = "INSERT INTO Login(CustomerID, LastLogin, is_now_logged_in) VALUES(?, ?, 'Y')";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();
        }
    }

    public void recordLogout(int customerId) throws SQLException {
        // This is a simplified approach: update the last login record to mark as logged out.
        String sql = "UPDATE Login SET LastLogout = ?, is_now_logged_in = 'N' WHERE CustomerID = ? AND is_now_logged_in = 'Y'";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(2, customerId);
            pstmt.executeUpdate();
        }
    }
} 