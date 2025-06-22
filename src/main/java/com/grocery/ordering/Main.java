package com.grocery.ordering;

import com.grocery.ordering.model.Customer;
import com.grocery.ordering.model.Product;
import com.grocery.ordering.util.DatabaseManager;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ECommerceSystem system = new ECommerceSystem();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DatabaseManager.createTables();
        run();
    }

    public static void run() {
        System.out.println("Welcome to the Online Grocery Ordering System!");

        // Admin Login
        if (!adminLogin()) {
            System.out.println("Invalid credentials. Exiting.");
            return;
        }

        while (true) {
            printMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        registerCustomer();
                        break;
                    case 2:
                        updateCustomerDetails();
                        break;
                    case 3:
                        searchCustomer();
                        break;
                    case 4:
                        searchCustomer();
                        break;
                    case 5:
                        searchProduct();
                        break;
                    case 6:
                        registerProduct();
                        break;
                    case 7:
                        updateProduct();
                        break;
                    case 8:
                        deleteProduct();
                        break;
                    case 9:
                        deactivateCustomer();
                        break;
                    case 10:
                        activateCustomer();
                        break;
                    case 11:
                        searchCustomerByEmailDomain();
                        break;
                    case 12:
                        runReports();
                        break;
                    case 13:
                        System.out.println("Good Bye User!!. Terminating the Program.");
                        return;
                    default:
                        System.out.println("You have selected an inappropriate option. Kindly select an appropriate option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // consume invalid input
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static boolean adminLogin() {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();
        // In a real app, you'd check this against the Admins table.
        // The default is admin/admin123
        return "admin".equals(username) && "admin123".equals(password);
    }

    private static void printMenu() {
        System.out.println("\n--- Online Grocery Ordering Menu ---");
        System.out.println("1) Customer Registration");
        System.out.println("2) Update Customer Details");
        System.out.println("3) Get Customer Order Details (Search Customer for now)");
        System.out.println("4) Customer Search");
        System.out.println("5) Product Search");
        System.out.println("6) Register Product");
        System.out.println("7) Update Product");
        System.out.println("8) Delete Product");
        System.out.println("9) Deactivate Customer Profile");
        System.out.println("10) Activate Customer Profile");
        System.out.println("11) Search Customer by Email Domain");
        System.out.println("12) Run Admin Reports");
        System.out.println("13) Exit");
        System.out.print("Enter your choice: ");
    }

    private static void registerCustomer() throws SQLException {
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Contact Number: ");
        String contact = scanner.nextLine();

        Customer newCustomer = system.registerCustomer(name, email, password, address, contact);
        System.out.println("Customer registered successfully with ID: " + newCustomer.getCustomerId());
    }

    private static void updateCustomerDetails() throws SQLException {
        System.out.print("Enter Customer ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (system.findCustomerById(id).isEmpty()) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.print("Enter new Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter new Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter new Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter new Contact Number: ");
        String contact = scanner.nextLine();

        system.updateCustomerDetails(id, name, email, password, address, contact);
        System.out.println("Customer details updated successfully.");
    }

    private static void searchCustomer() throws SQLException {
        System.out.print("Enter Customer Name to search: ");
        String name = scanner.nextLine();
        List<Customer> customers = system.searchCustomerByName(name);
        if (customers.isEmpty()) {
            System.out.println("No customers found with that name.");
        } else {
            customers.forEach(System.out::println);
        }
    }
    
    private static void searchProduct() throws SQLException {
        System.out.print("Enter Product Name to search: ");
        String name = scanner.nextLine();
        List<Product> products = system.searchProductByName(name);
        if (products.isEmpty()) {
            System.out.println("No products found with that name.");
        } else {
            products.forEach(System.out::println);
        }
    }
    
    private static void registerProduct() throws SQLException {
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        Product newProduct = system.addProduct(name, price, quantity);
        System.out.println("Product registered successfully with ID: " + newProduct.getProductId());
    }

    private static void updateProduct() throws SQLException {
        System.out.print("Enter Product ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (system.findProductById(id).isEmpty()) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter new Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        
        system.updateProduct(id, name, price, quantity);
        System.out.println("Product details updated successfully.");
    }
    
    private static void deleteProduct() throws SQLException {
        System.out.print("Enter Product ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        system.deleteProduct(id);
        System.out.println("Product deleted successfully.");
    }

    private static void deactivateCustomer() throws SQLException {
        System.out.print("Enter Customer ID to deactivate: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        system.setCustomerStatus(id, "Inactive");
        System.out.println("Your Profile is Inactived Successfully.");
    }

    private static void activateCustomer() throws SQLException {
        System.out.print("Enter Customer ID to activate: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        system.setCustomerStatus(id, "Active");
        System.out.println("Your Profile is Activated Successfully.");
    }

    private static void searchCustomerByEmailDomain() throws SQLException {
        System.out.print("Enter email domain to search (e.g., gmail.com): ");
        String domain = scanner.nextLine();
        List<Customer> customers = system.searchCustomerByEmailDomain(domain);
        if (customers.isEmpty()) {
            System.out.println("No such customer is registered with " + domain);
        } else {
            customers.forEach(c -> System.out.printf("CustomerID: %d, CustomerName: %s, CustomerEmail: %s%n",
                    c.getCustomerId(), c.getCustomerName(), c.getEmail()));
        }
    }

    private static void runReports() throws SQLException {
        System.out.println("\n--- Running Reports ---");
        // US_SQL_001
        System.out.println("Report: First 50% Logged-In Customer IDs");
        List<Integer> ids = system.getLoggedInCustomerIdsReport();
        System.out.println(ids);

        // US_SQL_002
        System.out.println("\nReport: Updating customer names by country (US/India)...");
        system.updateCustomerNamesByCountry();
        System.out.println("Update complete. Check customer search to see results.");

        // US_SQL_004
        System.out.println("\nReport: Transactions sorted by total amount (descending)");
        system.getTransactionsSorted();
        
        System.out.println("\n--- Reports Complete ---");
    }
}
