package com.grocery.ordering.servlet;

import com.grocery.ordering.dao.CustomerDAO;
import com.grocery.ordering.model.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerName = request.getParameter("customerName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String contactNumber = request.getParameter("contactNumber");

        try {
            if (customerDAO.findByEmail(email).isPresent()) {
                request.setAttribute("errorMessage", "Email id not valid or already exists.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            Customer customer = new Customer(customerName, email, password, address, contactNumber);
            customerDAO.registerCustomer(customer);

            request.setAttribute("successMessage", "Customer Registration successful.");
            request.getRequestDispatcher("register.jsp").forward(request, response);

        } catch (IllegalArgumentException | SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
} 