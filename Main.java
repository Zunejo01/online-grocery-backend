package com.ecommerce;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ECommerceSystem system = new ECommerceSystem();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMainMenu();
            int choice = getIntInput("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        registerCustomer();
                        break;
                    case 2:
                        updateCustomer();
                        break;
                    case 3:
                        addProduct();
                        break;
                    case 4:
                        updateProduct();
                        break;
                    case 5:
                        searchCustomerByEmail();
                        break;
                    case 6:
                        findHighestPricedProduct();
                        break;
                    case 7:
                        sortProductsByQuantity();
                        break;
                    case 8:
                        System.out.println("Thank you for using the system. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n=== E-Commerce System ===");
        System.out.println("1. Register Customer");
        System.out.println("2. Update Customer Details");
        System.out.println("3. Add Product");
        System.out.println("4. Update Product");
        System.out.println("5. Search Customer by Email");
        System.out.println("6. Find Highest Priced Product");
        System.out.println("7. Sort Products by Quantity");
        System.out.println("8. Exit");
    }

    private static void registerCustomer() {
        System.out.println("\n=== Customer Registration ===");
        String name = getStringInput("Enter customer name (max 50 chars): ");
        String email = getStringInput("Enter email: ");
        String password = getStringInput("Enter password (6-12 chars): ");
        String address = getStringInput("Enter address (max 100 chars): ");
        String contactNumber = getStringInput("Enter contact number (10 digits): ");

        int customerId = system.registerCustomer(name, email, password, address, contactNumber);
        System.out.println("Customer Registration is successful for " + customerId);
    }

    private static void updateCustomer() {
        System.out.println("\n=== Update Customer Details ===");
        String email = getStringInput("Enter email to update: ");
        String name = getStringInput("Enter new customer name (max 50 chars): ");
        String password = getStringInput("Enter new password (6-12 chars): ");
        String address = getStringInput("Enter new address (max 100 chars): ");
        String contactNumber = getStringInput("Enter new contact number (10 digits): ");

        system.updateCustomerDetails(email, name, password, address, contactNumber);
        System.out.println("Your Details updated successfully");
    }

    private static void addProduct() {
        System.out.println("\n=== Add Product ===");
        String name = getStringInput("Enter product name (max 50 chars): ");
        String description = getStringInput("Enter product description (max 100 chars): ");
        int quantity = getIntInput("Enter available quantity: ");
        double price = getDoubleInput("Enter price: ");

        String productId = system.addProduct(name, description, quantity, price);
        System.out.println("Product added successfully with ID: " + productId);
    }

    private static void updateProduct() {
        System.out.println("\n=== Update Product ===");
        String productId = getStringInput("Enter product ID to update: ");
        String name = getStringInput("Enter new product name (max 50 chars): ");
        String description = getStringInput("Enter new product description (max 100 chars): ");
        int quantity = getIntInput("Enter new available quantity: ");
        double price = getDoubleInput("Enter new price: ");

        system.updateProduct(productId, name, description, quantity, price);
        System.out.println("Product updated successfully");
    }

    private static void searchCustomerByEmail() {
        System.out.println("\n=== Search Customer by Email ===");
        String email = getStringInput("Enter email to search: ");
        
        Customer customer = system.findCustomerByEmail(email);
        if (customer != null) {
            System.out.println("Customer found: " + customer);
        } else {
            System.out.println("No Such Customer Exist with the Given Email");
        }
    }

    private static void findHighestPricedProduct() {
        System.out.println("\n=== Highest Priced Product ===");
        Product product = system.findProductWithHighestPrice();
        if (product != null) {
            System.out.println("Highest priced product: " + product);
        } else {
            System.out.println("Product List is Empty");
        }
    }

    private static void sortProductsByQuantity() {
        System.out.println("\n=== Products Sorted by Quantity ===");
        List<Product> sortedProducts = system.getProductsSortedByQuantity();
        if (!sortedProducts.isEmpty()) {
            for (Product product : sortedProducts) {
                System.out.println(product);
            }
        } else {
            System.out.println("Product List is Empty");
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
} 