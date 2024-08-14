<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password</title>
    <link rel="stylesheet" href="styles/forgot-password.css">
</head>
<body>
<div class="container">
    <h2>Forgot Password</h2>
    <p>Enter your email address and select your role to reset your password.</p>
    <form action="forgotpassword" method="post">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>

        <label for="role">Role:</label>
        <select id="role" name="role" required>
            <option value="student">Student</option>
            <option value="teacher">Teacher</option>
        </select>

        <button type="submit">Send OTP</button>
    </form>

    <% if (request.getParameter("error") != null) { %>
    <p style="color:red;"><%= request.getParameter("error") %></p>
    <% } %>
</div>
</body>
</html>
