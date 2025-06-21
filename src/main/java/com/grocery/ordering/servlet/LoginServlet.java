package com.grocery.ordering.servlet;

import com.grocery.ordering.dao.CustomerDAO;
import com.grocery.ordering.dao.LoginDAO;
import com.grocery.ordering.model.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final LoginDAO loginDAO = new LoginDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Optional<Customer> customerOpt = customerDAO.findByEmail(email);
            if (customerOpt.isPresent() && customerOpt.get().getPassword().equals(password)) {
                Customer customer = customerOpt.get();
                loginDAO.recordLogin(customer.getCustomerId());
                HttpSession session = request.getSession();
                session.setAttribute("customer", customer);
                response.sendRedirect("home");
            } else {
                request.setAttribute("errorMessage", "ID/password not valid");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
