<%@ page import="com.grocery.ordering.model.Order" %>
<%@ page import="com.grocery.ordering.model.OrderItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Invoice - Online Grocery Store</title>
    <style>
        body { font-family: Arial, sans-serif; padding-top: 60px; }
        .navbar { background-color: #333; overflow: hidden; position: fixed; top: 0; width: 100%; }
        .navbar a { float: left; display: block; color: white; text-align: center; padding: 14px 20px; text-decoration: none; }
        .navbar a:hover { background-color: #ddd; color: black; }
        .container { width: 60%; margin: auto; }
        .invoice-box { padding: 20px; border: 1px solid #ccc; margin-top: 20px; }
        .invoice-table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        .invoice-table th, .invoice-table td { border: 1px solid #ddd; padding: 8px; text-align: left; }
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
    <h2 style="color:green;">Order Placed Successfully!</h2>
    <div class="invoice-box">
        <h3>Invoice</h3>
        <% Order order = (Order) request.getAttribute("order"); %>
        <p><strong>Transaction ID:</strong> <%= order.getOrderId() %></p>
        <p><strong>Customer ID:</strong> <%= order.getCustomerId() %></p>
        <p><strong>Date:</strong> <%= order.getOrderDate() %></p>
        
        <table class="invoice-table">
            <tr>
                <th>Product ID</th>
                <th>Quantity</th>
                <th>Price</th>
            </tr>
            <% for (OrderItem item : order.getItems()) { %>
            <tr>
                <td><%= item.getProductId() %></td>
                <td><%= item.getQuantity() %></td>
                <td>₹<%= String.format("%.2f", item.getPrice()) %></td>
            </tr>
            <% } %>
        </table>
        <h4>Total Amount: ₹<%= String.format("%.2f", order.getTotalAmount()) %></h4>
    </div>
</div>

</body>
</html> 