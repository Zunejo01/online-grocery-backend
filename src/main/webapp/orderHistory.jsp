<%@ page import="com.grocery.ordering.model.Order" %>
<%@ page import="com.grocery.ordering.model.OrderItem" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Orders - Online Grocery Store</title>
    <style>
        body { font-family: Arial, sans-serif; padding-top: 60px; }
        .navbar { background-color: #333; overflow: hidden; position: fixed; top: 0; width: 100%; }
        .navbar a { float: left; display: block; color: white; text-align: center; padding: 14px 20px; text-decoration: none; }
        .navbar a:hover { background-color: #ddd; color: black; }
        .container { width: 80%; margin: auto; }
        .order-box { padding: 20px; border: 1px solid #ccc; margin-top: 20px; margin-bottom: 20px; }
        .order-table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        .order-table th, .order-table td { border: 1px solid #ddd; padding: 8px; text-align: left; }
    </style>
</head>
<body>

<div class="navbar">
    <a href="home">Home</a>
    <a href="profile.jsp">My Profile</a>
    <a href="cart.jsp">Cart</a>
    <a href="order-history">My Orders</a>
    <a href="logout">Logout</a>
</div>

<div class="container">
    <h2>My Order History</h2>
    <%
        List<Order> orders = (List<Order>) request.getAttribute("orders");
        if (orders == null || orders.isEmpty()) {
    %>
    <p>You have not placed any orders yet.</p>
    <%
    } else {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        for (Order order : orders) {
    %>
    <div class="order-box">
        <h4>Order ID: <%= order.getOrderId() %></h4>
        <p><strong>Date:</strong> <%= order.getOrderDate().format(formatter) %></p>
        <p><strong>Total Amount:</strong> ₹<%= String.format("%.2f", order.getTotalAmount()) %></p>

        <table class="order-table">
            <tr>
                <th>Product ID</th>
                <th>Quantity</th>
                <th>Price Paid</th>
            </tr>
            <% for (OrderItem item : order.getItems()) { %>
            <tr>
                <td><%= item.getProductId() %></td>
                <td><%= item.getQuantity() %></td>
                <td>₹<%= String.format("%.2f", item.getPrice()) %></td>
            </tr>
            <% } %>
        </table>
    </div>
    <%
        }
    }
    %>
</div>

</body>
</html> 