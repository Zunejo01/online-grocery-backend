package com.grocery.ordering.dao;

import com.grocery.ordering.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    /**
     * US_SQL_001: List of First 50% logged in IDs.
     */
    public List<Integer> getLoggedInCustomerIdsReport() throws SQLException {
        List<Integer> ids = new ArrayList<>();
        // Note: The 50% logic can be database specific. For SQLite, we can use LIMIT with a subquery.
        String sql = "SELECT CustomerID FROM Login WHERE is_now_logged_in = 'Y' ORDER BY LastLogin DESC LIMIT (SELECT COUNT(*)/2 FROM Login WHERE is_now_logged_in = 'Y')";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getInt("CustomerID"));
            }
        }
        return ids;
    }

    /**
     * US_SQL_002: Update customer name based on residency.
     */
    public void updateCustomerNamesByCountry() throws SQLException {
        String sqlUpdateUS = "UPDATE Customers SET CustomerName = 'US_' || CustomerName WHERE Address LIKE '%US%' AND CustomerName NOT LIKE 'US_%'";
        String sqlUpdateIndia = "UPDATE Customers SET CustomerName = 'IN_' || CustomerName WHERE Address LIKE '%India%' AND CustomerName NOT LIKE 'IN_%'";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmtUS = conn.prepareStatement(sqlUpdateUS);
             PreparedStatement pstmtIndia = conn.prepareStatement(sqlUpdateIndia)) {
            pstmtUS.executeUpdate();
            pstmtIndia.executeUpdate();
        }
    }

    /**
     * US_SQL_004: Get all transactions sorted in descending order of total amount.
     */
    public void getTransactionsSorted() throws SQLException {
        String sql = "SELECT * FROM Orders ORDER BY TotalAmount DESC";
        // This method will just print for demonstration in the console app
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("--- Sorted Transactions Report ---");
            while (rs.next()) {
                System.out.printf("OrderID: %d, CustomerID: %d, TotalAmount: %.2f, Date: %s%n",
                        rs.getInt("OrderID"), rs.getInt("CustomerID"), rs.getDouble("TotalAmount"), rs.getString("OrderDate"));
            }
            System.out.println("---------------------------------");
        }
    }
} 