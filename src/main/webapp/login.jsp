<!DOCTYPE html>
<html>
<head>
    <title>Login - Online Grocery Store</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2>Login</h2>
        <form action="login" method="post">
            <p style="color:red;">${errorMessage}</p>
            <label for="email">Email (Customer ID is your email):</label>
            <input type="text" id="email" name="email" required>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            
            <button type="submit">Login</button>
        </form>
        <p>New User? <a href="register.jsp">Register Yourself</a></p>
    </div>
</body>
</html>