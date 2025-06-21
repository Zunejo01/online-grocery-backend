<%@ page import="com.grocery.ordering.model.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout - Online Grocery Store</title>
    <style>
        body { font-family: Arial, sans-serif; padding-top: 60px; }
        .navbar { background-color: #333; overflow: hidden; position: fixed; top: 0; width: 100%; }
        .navbar a { float: left; display: block; color: white; text-align: center; padding: 14px 20px; text-decoration: none; }
        .navbar a:hover { background-color: #ddd; color: black; }
        .container { width: 50%; margin: auto; }
        .checkout-form { padding: 20px; border: 1px solid #ccc; margin-top: 20px; }
        .proceed-btn { background-color: #5cb85c; color: white; padding: 10px 20px; border: none; cursor: pointer; text-decoration: none; display: inline-block; margin-top: 10px; }
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
    <h2>Checkout</h2>
    <% Cart cart = (Cart) session.getAttribute("cart"); %>
    <div class="checkout-form">
        <h3>Payment Method</h3>
        <form action="checkout" method="post">
            <input type="radio" id="cod" name="paymentMethod" value="COD" checked>
            <label for="cod">Cash on Delivery (COD)</label><br>
            <input type="radio" id="upi" name="paymentMethod" value="UPI">
            <label for="upi">UPI</label><br>
            <input type="radio" id="cc" name="paymentMethod" value="Credit Card">
            <label for="cc">Credit Card</label><br>

            <hr>
            <h3>Pricing Details</h3>
            <p>Total Product Price: ₹<%= String.format("%.2f", cart.getTotal()) %></p>
            <p>Total Discount: ₹0.00</p>
            <p>Extra Charges (GST, etc.): ₹0.00</p>
            <h4>Order Total: ₹<%= String.format("%.2f", cart.getTotal()) %></h4>

            <button type="submit" class="proceed-btn">Proceed to Buy</button>
        </form>
    </div>
</div>

</body>
</html> 