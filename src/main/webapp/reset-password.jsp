<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Reset Password</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 50px;
    }
    .error {
      color: red;
    }
    .success {
      color: green;
    }
    form {
      margin-top: 20px;
    }
  </style>
</head>
<body>
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
<form method="post" action="resetpassword">
  <!-- These hidden fields store the email and role that were passed to this page -->
  <input type="hidden" name="email" value="<%= request.getParameter("email") %>">
  <input type="hidden" name="role" value="<%= request.getParameter("role") %>">

  <label for="password">New Password:</label>
  <input type="password" name="password" id="password" required>

  <button type="submit">Reset Password</button>
</form>
</body>
</html>
