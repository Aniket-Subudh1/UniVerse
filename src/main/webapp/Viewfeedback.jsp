<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.cms.model.feedback"%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Feedback Retrieval</title>
    <link rel="stylesheet" href="styles/Viewfeedback.css">
</head>
<body>
<h1>Retrieve Feedback for Teacher</h1>
<form action="RetrieveFeedbackServlet" method="post">
    <label for="teacherId">Teacher ID:</label>
    <input type="text" id="teacherId" name="teacherId" required>
    <input type="submit" value="Get Feedback">
</form>

<%
    List<feedback> feedbackList = (List<feedback>) request.getAttribute("feedbackList");
    if (feedbackList != null && !feedbackList.isEmpty()) {
%>
<h2>Feedback Results:</h2>
<ul>
    <%
        for (feedback feedback : feedbackList) {
    %>
    <li>
        <strong>Student Name:</strong> <%= feedback.getStudentName() %><br>
        <strong>Feedback:</strong> <%= feedback.getFeedbackText() %>
    </li>
    <%
        }
    %>
</ul>
<%
} else if (feedbackList != null) {
%>
<h2>No feedback found for the provided Teacher ID.</h2>
<%
    }
%>
</body>
</html>
