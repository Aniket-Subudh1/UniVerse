<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="styles/style.css">
  <title>Reset Password</title>
</head>
<body>
<div class="container" id="container">
  <div class="form-container">
    <form method="post" action="resetpassword">
      <h1>Reset Password</h1>
      <p>Enter your new password below.</p>

      <!-- Display error or success messages -->
      <%
        String error = request.getParameter("error");
        String success = request.getParameter("success");
        if (error != null) {
          out.println("<p class='error'>" + error + "</p>");
        } else if (success != null) {
          out.println("<p class='success'>" + success + "</p>");
        }
      %>

      <!-- Reset Password Form -->
      <input type="hidden" name="email" value="<%= request.getParameter("email") %>">
      <input type="password" name="password" id="password" placeholder="New Password" required>

      <button type="submit">Reset Password</button>
    </form>
  </div>
</div>
</body>
</html>
