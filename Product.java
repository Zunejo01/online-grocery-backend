package com.ecommerce;

import java.text.DecimalFormat;

public class Product {
    private String productId;
    private String productName;
    private String productDescription;
    private int availableQuantities;
    private double price;

    public Product(String productName, String productDescription, int availableQuantities, double price) {
        validateInputs(productName, productDescription, availableQuantities, price);
        this.productId = generateProductId();
        this.productName = productName;
        this.productDescription = productDescription;
        this.availableQuantities = availableQuantities;
        this.price = price;
    }

    private void validateInputs(String productName, String productDescription, int availableQuantities, double price) {
        if (productName == null || productName.length() > 50) {
            throw new IllegalArgumentException("Product name must not be null and should be maximum 50 characters");
        }
        if (productDescription == null || productDescription.length() > 100) {
            throw new IllegalArgumentException("Product description must not be null and should be maximum 100 characters");
        }
        if (availableQuantities < 0) {
            throw new IllegalArgumentException("Available quantities cannot be negative");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    private String generateProductId() {
        // Generate random 10-digit number in format (1-4028-9462-7)
        int firstDigit = 1 + (int)(Math.random() * 9);
        int secondPart = 1000 + (int)(Math.random() * 9000);
        int thirdPart = 1000 + (int)(Math.random() * 9000);
        int lastDigit = (int)(Math.random() * 10);
        
        return String.format("%d-%04d-%04d-%d", firstDigit, secondPart, thirdPart, lastDigit);
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (productName != null && productName.length() <= 50) {
            this.productName = productName;
        } else {
            throw new IllegalArgumentException("Product name must not be null and should be maximum 50 characters");
        }
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        if (productDescription != null && productDescription.length() <= 100) {
            this.productDescription = productDescription;
        } else {
            throw new IllegalArgumentException("Product description must not be null and should be maximum 100 characters");
        }
    }

    public int getAvailableQuantities() {
        return availableQuantities;
    }

    public void setAvailableQuantities(int availableQuantities) {
        if (availableQuantities >= 0) {
            this.availableQuantities = availableQuantities;
        } else {
            throw new IllegalArgumentException("Available quantities cannot be negative");
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", availableQuantities=" + availableQuantities +
                ", price=" + df.format(price) +
                '}';
    }
} 