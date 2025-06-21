package com.grocery.ordering.model;

import java.text.DecimalFormat;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private int quantity;

    public Product() {
    }

    public Product(int productId, String productName, double price, int quantity) {
        this.productId = productId;
        this.setProductName(productName);
        this.setPrice(price);
        this.setQuantity(quantity);
    }

    public Product(String productName, double price, int quantity) {
        this.setProductName(productName);
        this.setPrice(price);
        this.setQuantity(quantity);
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (productName == null || productName.trim().isEmpty() || productName.length() > 50) {
            throw new IllegalArgumentException("Product name must not be null/empty and should be maximum 50 characters");
        }
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Available quantities cannot be negative");
        }
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + df.format(price) +
                '}';
    }
} 