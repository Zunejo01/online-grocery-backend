<!DOCTYPE html>
<html>
<head>
    <title>Register - Online Grocery Store</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2>Customer Registration</h2>
        <form action="register" method="post">
            <p style="color:red;">${errorMessage}</p>
            <p style="color:green;">${successMessage}</p>
            
            <label for="customerName">Customer Name:</label>
            <input type="text" id="customerName" name="customerName" required>
            
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            
            <label for="address">Address:</label>
            <textarea id="address" name="address" required></textarea>
            
            <label for="contactNumber">Contact Number:</label>
            <input type="text" id="contactNumber" name="contactNumber" required>
            
            <button type="submit">Register</button>
        </form>
        <p>Already have an account? <a href="login.jsp">Login</a></p>
    </div>
</body>
</html> 