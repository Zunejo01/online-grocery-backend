<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Profile - Online Grocery Store</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <style>
        .navbar { background-color: #333; overflow: hidden; position: fixed; top: 0; width: 100%; }
        .navbar a { float: left; display: block; color: white; text-align: center; padding: 14px 20px; text-decoration: none; }
        .navbar a:hover { background-color: #ddd; color: black; }
        body { padding-top: 60px; } /* Add padding to body to avoid content being hidden by navbar */
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
    <h2>My Profile</h2>
    <p>Customer: ${sessionScope.customer.customerName}</p>
    <form action="profile" method="post">
        <p style="color:red;">${errorMessage}</p>
        <p style="color:green;">${successMessage}</p>

        <label for="customerName">Customer Name:</label>
        <input type="text" id="customerName" name="customerName" value="${sessionScope.customer.customerName}" disabled>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${sessionScope.customer.email}" disabled>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" value="${sessionScope.customer.password}" disabled>

        <label for="address">Address:</label>
        <textarea id="address" name="address" disabled>${sessionScope.customer.address}</textarea>

        <label for="contactNumber">Contact Number:</label>
        <input type="text" id="contactNumber" name="contactNumber" value="${sessionScope.customer.contactNumber}" disabled>

        <button type="button" id="updateBtn" onclick="enableEdit()">Update</button>
        <button type="submit" id="saveBtn" style="display:none;">Save</button>
    </form>
</div>

<script>
    function enableEdit() {
        document.getElementById('customerName').disabled = false;
        document.getElementById('email').disabled = false;
        document.getElementById('password').disabled = false;
        document.getElementById('address').disabled = false;
        document.getElementById('contactNumber').disabled = false;
        document.getElementById('updateBtn').style.display = 'none';
        document.getElementById('saveBtn').style.display = 'block';
    }
</script>

</body>
</html> 