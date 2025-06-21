package com.grocery.ordering.servlet;

import com.grocery.ordering.dao.ProductDAO;
import com.grocery.ordering.model.Cart;
import com.grocery.ordering.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cartFromSession = (Cart) session.getAttribute("cart");
        if (cartFromSession == null) {
            cartFromSession = new Cart();
            session.setAttribute("cart", cartFromSession);
        }

        final Cart cart = cartFromSession;

        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("cart.jsp");
            return;
        }

        try {
            if ("add".equals(action)) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                Optional<Product> productOpt = productDAO.findById(productId);
                productOpt.ifPresent(product -> cart.addItem(product, 1));
                response.sendRedirect("home");
            } else if ("delete".equals(action)) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                cart.removeItem(productId);
                response.sendRedirect("cart.jsp");
            } else if ("update".equals(action)) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                cart.updateItem(productId, quantity);
                response.sendRedirect("cart.jsp");
            }
        } catch (NumberFormatException | SQLException e) {
            // Handle error
            response.sendRedirect("cart.jsp"); // Or an error page
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
}
