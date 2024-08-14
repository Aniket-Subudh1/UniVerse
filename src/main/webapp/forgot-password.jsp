<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles/style.css">
    <title>Forgot Password</title>
</head>
<body>
<div class="container" id="container">
    <div class="form-container">
        <form method="post" action="forgotpassword">
            <h1>Forgot Password</h1>
            <p>Enter your email to reset your password.</p>

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
            <input type="email" name="email" id="email" placeholder="Email" required>

            <button type="submit">Submit</button>
        </form>
    </div>
</div>
</body>
</html>
