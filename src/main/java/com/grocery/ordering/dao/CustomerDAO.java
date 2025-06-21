package com.grocery.ordering.dao;

import com.grocery.ordering.model.Customer;
import com.grocery.ordering.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAO {

    public Customer registerCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customers(CustomerName, Email, Password, Address, ContactNumber, Status) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPassword());
            pstmt.setString(4, customer.getAddress());
            pstmt.setString(5, customer.getContactNumber());
            pstmt.setString(6, customer.getStatus());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setCustomerId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating customer failed, no ID obtained.");
                }
            }
            return customer;
        }
    }

    public Optional<Customer> findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Customers WHERE Email = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToCustomer(rs));
                }
            }
        }
        return Optional.empty();
    }
    
    public Optional<Customer> findById(int id) throws SQLException {
        String sql = "SELECT * FROM Customers WHERE CustomerID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToCustomer(rs));
                }
            }
        }
        return Optional.empty();
    }

    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE Customers SET CustomerName = ?, Email = ?, Password = ?, Address = ?, ContactNumber = ?, Status = ? WHERE CustomerID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPassword());
            pstmt.setString(4, customer.getAddress());
            pstmt.setString(5, customer.getContactNumber());
            pstmt.setString(6, customer.getStatus());
            pstmt.setInt(7, customer.getCustomerId());
            pstmt.executeUpdate();
        }
    }

    public List<Customer> searchByName(String name) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers WHERE lower(CustomerName) LIKE lower(?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapRowToCustomer(rs));
                }
            }
        }
        return customers;
    }

    public List<Customer> searchByEmailDomain(String domain) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers WHERE Email LIKE ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%@" + domain);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapRowToCustomer(rs));
                }
            }
        }
        return customers;
    }

    private Customer mapRowToCustomer(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("CustomerID"),
                rs.getString("CustomerName"),
                rs.getString("Email"),
                rs.getString("Password"),
                rs.getString("Address"),
                rs.getString("ContactNumber"),
                rs.getString("Status")
        );
    }
} 