<%@ page import="com.grocery.ordering.model.Cart" %>
<%@ page import="com.grocery.ordering.model.CartItem" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your Cart - Online Grocery Store</title>
    <style>
        body { font-family: Arial, sans-serif; padding-top: 60px; }
        .navbar { background-color: #333; overflow: hidden; position: fixed; top: 0; width: 100%; }
        .navbar a { float: left; display: block; color: white; text-align: center; padding: 14px 20px; text-decoration: none; }
        .navbar a:hover { background-color: #ddd; color: black; }
        .container { width: 80%; margin: auto; }
        .cart-table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        .cart-table th, .cart-table td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        .cart-table th { background-color: #f2f2f2; }
        .summary { float: right; width: 30%; margin-top: 20px; padding: 15px; border: 1px solid #ccc; }
        .checkout-btn { background-color: #5cb85c; color: white; padding: 10px 20px; border: none; cursor: pointer; text-decoration: none; display: inline-block; margin-top: 10px; }
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
    <h2>Your Shopping Cart</h2>
    <%
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
    %>
    <p>Your cart is empty. <a href="home">View Products</a></p>
    <%
    } else {
    %>
    <div class="cart-items">
        <table class="cart-table">
            <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Subtotal</th>
                <th>Action</th>
            </tr>
            <% for (CartItem item : cart.getItems()) { %>
            <tr>
                <td><%= item.getProduct().getProductName() %></td>
                <td>₹<%= String.format("%.2f", item.getProduct().getPrice()) %></td>
                <td>
                    <form action="cart" method="post" style="display: inline;">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="productId" value="<%= item.getProduct().getProductId() %>">
                        <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1" style="width: 50px;">
                        <input type="submit" value="Update">
                    </form>
                </td>
                <td>₹<%= String.format("%.2f", item.getSubtotal()) %></td>
                <td>
                    <form action="cart" method="post" style="display: inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="productId" value="<%= item.getProduct().getProductId() %>">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
    </div>
    <div class="summary">
        <h3>Summary</h3>
        <p>Total: ₹<%= String.format("%.2f", cart.getTotal()) %></p>
        <a href="checkout.jsp" class="checkout-btn">Checkout</a>
    </div>
    <%
        }
    %>
</div>

</body>
</html> 