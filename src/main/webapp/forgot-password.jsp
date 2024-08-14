<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgot Password</title>
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
<h1>Forgot Password</h1>

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

<!-- Forgot Password Form -->
<form method="post" action="forgotpassword">
    <label for="email">Email:</label>
    <input type="email" name="email" id="email" required>

    <button type="submit">Submit</button>
</form>
</body>
</html>
