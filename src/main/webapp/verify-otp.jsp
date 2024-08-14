<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Verify OTP</title>
    <link rel="stylesheet" href="styles/verify-otp.css">
</head>
<body>
<div class="container">
    <h2>Verify OTP</h2>
    <p>Please enter the OTP sent to your email.</p>
    <form action="verifyotp" method="post">
        <input type="hidden" name="email" value="<%= request.getParameter("email") %>">

        <label for="otp">OTP:</label>
        <input type="text" id="otp" name="otp" placeholder="Enter OTP" required>

        <button type="submit">Verify OTP</button>
    </form>

    <% if (request.getParameter("error") != null) { %>
    <p class="error"><%= request.getParameter("error") %></p>
    <% } %>
</div>
</body>
</html>
