package com.grocery.ordering.model;

public class Customer {
    private int customerId;
    private String customerName;
    private String email;
    private String password;
    private String address;
    private String contactNumber;
    private String status; // For soft delete

    public Customer() {
    }

    public Customer(int customerId, String customerName, String email, String password, String address, String contactNumber, String status) {
        this.customerId = customerId;
        this.setCustomerName(customerName);
        this.setEmail(email);
        this.setPassword(password);
        this.setAddress(address);
        this.setContactNumber(contactNumber);
        this.setStatus(status);
    }

    public Customer(String customerName, String email, String password, String address, String contactNumber) {
        this.setCustomerName(customerName);
        this.setEmail(email);
        this.setPassword(password);
        this.setAddress(address);
        this.setContactNumber(contactNumber);
        this.status = "Active";
    }

    private void validateInputs(String customerName, String email, String password, String address, String contactNumber) {
        if (customerName == null || customerName.length() > 50) {
            throw new IllegalArgumentException("Customer name must not be null and should be maximum 50 characters");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (password == null || password.length() < 6 || password.length() > 12) {
            throw new IllegalArgumentException("Password must be between 6 and 12 characters");
        }
        if (address == null || address.length() > 100) {
            throw new IllegalArgumentException("Address must not be null and should be maximum 100 characters");
        }
        if (contactNumber == null || !contactNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Contact number must be exactly 10 digits");
        }
    }

    private int generateCustomerId() {
        return 10000 + (int)(Math.random() * 90000); // Generates a random 5-digit number
    }

    // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        if (customerName == null || customerName.trim().isEmpty() || customerName.length() > 50 || !customerName.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Customer Name must have alphabets only, not be null/empty, and be maximum 50 characters");
        }
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 5 || !password.matches(".*[A-Z].*") || !password.matches(".*[0-9].*") || !password.matches(".*[!@#$%^&*()].*")) {
            throw new IllegalArgumentException("Password's length must not be less than 5 character and it must be a combination of atleast one uppercase alphabet,  one number and one special character.");
        }
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty() || address.length() > 100) {
            throw new IllegalArgumentException("Address must not be null/empty and should be maximum 100 characters");
        }
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        if (contactNumber == null || !contactNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Contact number must be exactly 10 digits");
        }
        this.contactNumber = contactNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
} 