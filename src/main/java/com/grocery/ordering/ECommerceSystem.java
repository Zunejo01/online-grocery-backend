package com.grocery.ordering;

import com.grocery.ordering.dao.CustomerDAO;
import com.grocery.ordering.dao.ProductDAO;
import com.grocery.ordering.dao.ReportDAO;
import com.grocery.ordering.model.Customer;
import com.grocery.ordering.model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ECommerceSystem {
    private final CustomerDAO customerDAO;
    private final ProductDAO productDAO;
    private final ReportDAO reportDAO;

    public ECommerceSystem() {
        this.customerDAO = new CustomerDAO();
        this.productDAO = new ProductDAO();
        this.reportDAO = new ReportDAO();
    }

    public Customer registerCustomer(String customerName, String email, String password, String address, String contactNumber) throws SQLException {
        if (customerDAO.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        Customer customer = new Customer(customerName, email, password, address, contactNumber);
        return customerDAO.registerCustomer(customer);
    }

    public void updateCustomerDetails(int customerId, String customerName, String email, String password, String address, String contactNumber) throws SQLException {
        Optional<Customer> customerOpt = customerDAO.findById(customerId);
        if (customerOpt.isEmpty()) {
            throw new IllegalArgumentException("No customer found with ID: " + customerId);
        }

        Customer customer = customerOpt.get();
        customer.setCustomerName(customerName);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setAddress(address);
        customer.setContactNumber(contactNumber);
        customerDAO.updateCustomer(customer);
    }

    public Product addProduct(String productName, double price, int quantity) throws SQLException {
        Product product = new Product(productName, price, quantity);
        return productDAO.addProduct(product);
    }

    public void updateProduct(int productId, String productName, double price, int quantity) throws SQLException {
        Optional<Product> productOpt = productDAO.findById(productId);
        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("No product found with ID: " + productId);
        }

        Product product = productOpt.get();
        product.setProductName(productName);
        product.setPrice(price);
        product.setQuantity(quantity);
        productDAO.updateProduct(product);
    }

    public Optional<Customer> findCustomerByEmail(String email) throws SQLException {
        return customerDAO.findByEmail(email);
    }
    
    public Optional<Customer> findCustomerById(int id) throws SQLException {
        return customerDAO.findById(id);
    }
    
    public List<Customer> searchCustomerByName(String name) throws SQLException {
        return customerDAO.searchByName(name);
    }
    
    public void deleteProduct(int productId) throws SQLException {
        productDAO.deleteProduct(productId);
    }
    
    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }
    
    public Optional<Product> findProductById(int productId) throws SQLException {
        return productDAO.findById(productId);
    }

    public List<Product> searchProductByName(String name) throws SQLException {
        return productDAO.searchByName(name);
    }

    public void setCustomerStatus(int customerId, String status) throws SQLException {
        Optional<Customer> customerOpt = customerDAO.findById(customerId);
        if (customerOpt.isEmpty()) {
            throw new IllegalArgumentException("No customer found with ID: " + customerId);
        }
        Customer customer = customerOpt.get();
        customer.setStatus(status);
        customerDAO.updateCustomer(customer);
    }
    
    public List<Customer> searchCustomerByEmailDomain(String domain) throws SQLException {
        return customerDAO.searchByEmailDomain(domain);
    }

    // Report Methods
    public List<Integer> getLoggedInCustomerIdsReport() throws SQLException {
        return reportDAO.getLoggedInCustomerIdsReport();
    }

    public void updateCustomerNamesByCountry() throws SQLException {
        reportDAO.updateCustomerNamesByCountry();
    }

    public void getTransactionsSorted() throws SQLException {
        reportDAO.getTransactionsSorted();
    }
} 