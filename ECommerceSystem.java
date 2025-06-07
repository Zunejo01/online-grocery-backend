package com.ecommerce;

import java.util.*;

public class ECommerceSystem {
    private List<Customer> customers;
    private List<Product> products;

    public ECommerceSystem() {
        this.customers = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    // US001: Customer Registration
    public int registerCustomer(String customerName, String email, String password, String address, String contactNumber) {
        if (customers.stream().anyMatch(c -> c.getEmail().equals(email))) {
            throw new IllegalArgumentException("Email already exists");
        }

        Customer customer = new Customer(customerName, email, password, address, contactNumber);
        customers.add(customer);
        return customer.getCustomerId();
    }

    // US002: Update Customer Details
    public void updateCustomerDetails(String email, String customerName, String password, String address, String contactNumber) {
        Customer customer = findCustomerByEmail(email);
        if (customer == null) {
            throw new IllegalArgumentException("No customer found with email: " + email);
        }

        customer.setCustomerName(customerName);
        customer.setPassword(password);
        customer.setAddress(address);
        customer.setContactNumber(contactNumber);
    }

    // US003: Add/Update Product Details
    public String addProduct(String productName, String productDescription, int availableQuantities, double price) {
        Product product = new Product(productName, productDescription, availableQuantities, price);
        products.add(product);
        return product.getProductId();
    }

    public void updateProduct(String productId, String productName, String productDescription, int availableQuantities, double price) {
        Product product = findProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("No product found with ID: " + productId);
        }

        product.setProductName(productName);
        product.setProductDescription(productDescription);
        product.setAvailableQuantities(availableQuantities);
        product.setPrice(price);
    }

    // US004: Search Customer by Email
    public Customer findCustomerByEmail(String email) {
        return customers.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    // US005: Find Product with Highest Price
    public Product findProductWithHighestPrice() {
        if (products.isEmpty()) {
            return null;
        }
        return products.stream()
                .max(Comparator.comparingDouble(Product::getPrice))
                .orElse(null);
    }

    // US006: Sort Products by Quantity
    public List<Product> getProductsSortedByQuantity() {
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        List<Product> sortedProducts = new ArrayList<>(products);
        sortedProducts.sort(Comparator.comparingInt(Product::getAvailableQuantities));
        return sortedProducts;
    }

    private Product findProductById(String productId) {
        return products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
} 