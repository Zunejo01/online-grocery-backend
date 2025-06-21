package com.grocery.ordering.servlet;

import com.grocery.ordering.dao.OrderDAO;
import com.grocery.ordering.model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customer") == null || session.getAttribute("cart") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Customer customer = (Customer) session.getAttribute("customer");
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart.getItems().isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        Order order = new Order(customer.getCustomerId(), cart.getTotal(), LocalDateTime.now());
        order.setItems(cart.getItems().stream()
                .map(cartItem -> new OrderItem(
                        cartItem.getProduct().getProductId(),
                        cartItem.getQuantity(),
                        cartItem.getProduct().getPrice()))
                .collect(Collectors.toList()));

        try {
            Order createdOrder = orderDAO.createOrder(order);
            session.removeAttribute("cart"); // Clear the cart
            request.setAttribute("order", createdOrder);
            request.getRequestDispatcher("invoice.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error during checkout", e);
        }
    }
} 