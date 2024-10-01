<!-- feedback.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Submit Feedback</title>
    <link rel="stylesheet" href="styles/feedback.css">
</head>
<body>
<h2>Submit Feedback</h2>
<form action="SubmitFeedbackServlet" method="post">
    <label for="teacherId">Teacher ID:</label>
    <input type="text" id="teacherId" name="teacherId" required><br><br>

    <label for="studentName">Your Name:</label>
    <input type="text" id="studentName" name="studentName" required><br><br>

    <label for="feedback">Feedback:</label><br>
    <textarea id="feedback" name="feedback" rows="5" cols="30" required></textarea><br><br>

    <input type="submit" value="Submit">
</form>
</body>
</html>
