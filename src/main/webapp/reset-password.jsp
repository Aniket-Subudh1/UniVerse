<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Reset Password</title>
  <link rel="stylesheet" href="styles/reset-password.css">
</head>
<body>
<div class="container">
  <h2>Reset Password</h2>
  <p>Enter your new password below.</p>
  <form action="resetpassword" method="post">
    <input type="hidden" name="email" value="<%= request.getParameter("email") %>">
    <input type="hidden" name="role" value="<%= request.getParameter("role") %>">

    <label for="password">New Password:</label>
    <input type="password" id="password" name="password" required>

    <button type="submit">Reset Password</button>
  </form>

  <% if (request.getParameter("error") != null) { %>
  <p style="color:red;"><%= request.getParameter("error") %></p>
  <% } %>
</div>
</body>
</html>
