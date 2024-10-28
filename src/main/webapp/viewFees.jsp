<!-- viewFees.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.cms.model.Fee" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Fees</title>
    <style>

    </style>
</head>
<body>

<div class="container1">
    <nav class="navbar">
        <h2 class="logo">UniVerse Admin</h2>
        <ul class="nav-links">
            <li><a href="admin-dashboard.jsp">Home</a></li>
            <li><a href="manage-timetable.jsp">Timetable</a></li>
            <li><a href="manage-courses.jsp">Courses</a></li>
            <li><a href="manage-grades.jsp">Grades</a></li>
        </ul>
        <div class="profile-info">
            <p class="profile-name"><%= session.getAttribute("name") %></p>
            <p class="profile-role"><%= session.getAttribute("role") %></p>
        </div>
        <div class="dark-mode-toggle" id="dark-mode-toggle">
            <span class="sun"><i class='bx bx-sun'></i></span>
            <span class="moon"><i class='bx bx-moon'></i></span>
        </div>
    </nav>
</div>
<div class="container">
    <h2>Your Fees</h2>

    <!-- Form to select fee type -->
    <form action="viewFees" method="post">
        <select name="feeType" class="dropdown" onchange="this.form.submit()">
            <option value="">Select Fee Type</option>
            <option value="academic" <%= "academic".equals(request.getParameter("feeType")) ? "selected" : "" %>>Academic Fee</option>
            <option value="hostel" <%= "hostel".equals(request.getParameter("feeType")) ? "selected" : "" %>>Hostel Fee</option>
            <option value="exam" <%= "exam".equals(request.getParameter("feeType")) ? "selected" : "" %>>Exam Fee</option>
        </select>
    </form>

    <div class="fee-amount">
        <%
            // Display the fee amount if available
            Double feeAmount = (Double) request.getAttribute("feeAmount");
            if (feeAmount != null) {
                out.print("Amount: $" + String.format("%.2f", feeAmount));
            } else {
                out.print("Amount: $0.00");
            }
        %>
    </div>

    <!-- Pay Fee Button -->
    <%
        if (feeAmount != null) {
    %>
    <form action="paymentForm.jsp" method="post">
        <input type="hidden" name="feeType" value="<%= request.getParameter("feeType") %>">
        <input type="hidden" name="feeAmount" value="<%= feeAmount %>">
        <button type="submit" class="pay-button">Pay Fee</button>
    </form>
    <%
        }
    %>
</div>
</body>
</html>
