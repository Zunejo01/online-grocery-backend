package com.grocery.ordering.servlet;

import com.grocery.ordering.dao.LoginDAO;
import com.grocery.ordering.model.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private final LoginDAO loginDAO = new LoginDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // false means do not create a new session
        if (session != null) {
            Customer customer = (Customer) session.getAttribute("customer");
            if (customer != null) {
                try {
                    loginDAO.recordLogout(customer.getCustomerId());
                } catch (Exception e) {
                    // Log the exception, but don't prevent logout
                    e.printStackTrace();
                }
            }
            session.invalidate();
        }
        response.sendRedirect("login.jsp");
    }
}
