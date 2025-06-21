package com.grocery.ordering.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:grocery.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void createTables() {
        String customerTableSql = "CREATE TABLE IF NOT EXISTS Customers (" +
                "CustomerID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CustomerName VARCHAR(50) NOT NULL," +
                "Email VARCHAR(100) NOT NULL UNIQUE," +
                "Password VARCHAR(100) NOT NULL," +
                "Address VARCHAR(100) NOT NULL," +
                "ContactNumber VARCHAR(10) NOT NULL," +
                "Status VARCHAR(20) NOT NULL DEFAULT 'Active'" +
                ");";

        String productTableSql = "CREATE TABLE IF NOT EXISTS Products (" +
                "ProductID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ProductName VARCHAR(50) NOT NULL," +
                "Price DECIMAL(10, 2) NOT NULL," +
                "Quantity INTEGER NOT NULL" +
                ");";

        String orderTableSql = "CREATE TABLE IF NOT EXISTS Orders (" +
                "OrderID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CustomerID INTEGER NOT NULL," +
                "TotalAmount DECIMAL(10, 2) NOT NULL," +
                "OrderDate DATETIME NOT NULL," +
                "FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)" +
                ");";

        String orderItemTableSql = "CREATE TABLE IF NOT EXISTS OrderItems (" +
                "OrderItemID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "OrderID INTEGER NOT NULL," +
                "ProductID INTEGER NOT NULL," +
                "Quantity INTEGER NOT NULL," +
                "Price DECIMAL(10, 2) NOT NULL," +
                "FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)," +
                "FOREIGN KEY (ProductID) REFERENCES Products(ProductID)" +
                ");";

        String adminTableSql = "CREATE TABLE IF NOT EXISTS Admins (" +
                "AdminID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Username VARCHAR(50) NOT NULL UNIQUE," +
                "Password VARCHAR(100) NOT NULL" +
                ");";

        // As per US_SQL_001
        String loginTableSql = "CREATE TABLE IF NOT EXISTS Login (" +
                "LoginID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CustomerID INTEGER NOT NULL," +
                "LastLogin DATETIME NOT NULL," +
                "LastLogout DATETIME," +
                "is_now_logged_in VARCHAR(1) NOT NULL," +
                "FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)" +
                ");";


        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(customerTableSql);
            stmt.execute(productTableSql);
            stmt.execute(orderTableSql);
            stmt.execute(orderItemTableSql);
            stmt.execute(adminTableSql);
            stmt.execute(loginTableSql);
            
            // Add a default admin user if not exists
            String checkAdminSql = "SELECT COUNT(*) FROM Admins WHERE Username = 'admin'";
            var rs = stmt.executeQuery(checkAdminSql);
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO Admins(Username, Password) VALUES('admin', 'admin123')");
            }


        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
} 