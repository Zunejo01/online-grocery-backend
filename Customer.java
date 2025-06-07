package com.ecommerce;

public class Customer {
    private int customerId;
    private String customerName;
    private String email;
    private String password;
    private String address;
    private String contactNumber;

    public Customer(String customerName, String email, String password, String address, String contactNumber) {
        validateInputs(customerName, email, password, address, contactNumber);
        this.customerId = generateCustomerId();
        this.customerName = customerName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.contactNumber = contactNumber;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        if (customerName != null && customerName.length() <= 50) {
            this.customerName = customerName;
        } else {
            throw new IllegalArgumentException("Customer name must not be null and should be maximum 50 characters");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (email != null && email.matches(emailRegex)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null && password.length() >= 6 && password.length() <= 12) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Password must be between 6 and 12 characters");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address != null && address.length() <= 100) {
            this.address = address;
        } else {
            throw new IllegalArgumentException("Address must not be null and should be maximum 100 characters");
        }
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        if (contactNumber != null && contactNumber.matches("\\d{10}")) {
            this.contactNumber = contactNumber;
        } else {
            throw new IllegalArgumentException("Contact number must be exactly 10 digits");
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
} 