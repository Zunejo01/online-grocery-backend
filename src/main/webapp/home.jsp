<%@ page import="com.grocery.ordering.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home - Online Grocery Store</title>
    <style>
        body { font-family: Arial, sans-serif; }
        .navbar { background-color: #333; overflow: hidden; }
        .navbar a { float: left; display: block; color: white; text-align: center; padding: 14px 20px; text-decoration: none; }
        .navbar a:hover { background-color: #ddd; color: black; }
        .header { text-align: center; padding: 20px; }
        .product-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 20px; padding: 20px; }
        .product-card { border: 1px solid #ccc; border-radius: 5px; padding: 15px; text-align: center; }
        .product-card button { background-color: #5cb85c; color: white; border: none; padding: 10px; cursor: pointer; width: 100%; }
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

<div class="header">
    <h2>Hello ${sessionScope.customer.customerName} to Online Grocery Store</h2>
</div>

<div class="product-grid">
    <% 
        List<Product> products = (List<Product>) request.getAttribute("products");
        if (products != null) {
            for (Product product : products) {
    %>
    <div class="product-card">
        <h3><%= product.getProductName() %></h3>
        <p>Price: ₹<%= String.format("%.2f", product.getPrice()) %></p>
        <form action="cart" method="post">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="productId" value="<%= product.getProductId() %>">
            <button type="submit">Add to Cart</button>
        </form>
    </div>
    <% 
            }
        } 
    %>
</div>

</body>
</html> 