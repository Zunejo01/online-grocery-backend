package com.grocery.ordering.servlet;

import com.grocery.ordering.dao.CustomerDAO;
import com.grocery.ordering.model.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customer") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Customer customer = (Customer) session.getAttribute("customer");
        
        String customerName = request.getParameter("customerName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String contactNumber = request.getParameter("contactNumber");

        try {
            customer.setCustomerName(customerName);
            customer.setEmail(email);
            customer.setPassword(password);
            customer.setAddress(address);
            customer.setContactNumber(contactNumber);

            customerDAO.updateCustomer(customer);

            session.setAttribute("customer", customer); // Update session attribute
            request.setAttribute("successMessage", "Profile updated successfully.");

        } catch (IllegalArgumentException | SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
        }
        
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
} 