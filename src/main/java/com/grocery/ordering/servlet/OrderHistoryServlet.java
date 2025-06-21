package com.grocery.ordering.servlet;

import com.grocery.ordering.dao.OrderDAO;
import com.grocery.ordering.model.Customer;
import com.grocery.ordering.model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/order-history")
public class OrderHistoryServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customer") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Customer customer = (Customer) session.getAttribute("customer");

        try {
            List<Order> orders = orderDAO.findByCustomerId(customer.getCustomerId());
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("orderHistory.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error when fetching order history", e);
        }
    }
} 