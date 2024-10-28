<!-- addFee.jsp -->
<%@ page import="java.sql.*, java.util.ArrayList"%>
<%@ page import="com.cms.dao.DBConnection" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="styles/addFee.css">
    <title>Add Fees</title>
</head>
<body>
<div class="container">
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
<h2>Add Student Fees</h2>
<form action="AddFeeServlet" method="post">
    <label for="registrationId">Registration ID:</label>
    <select name="registrationId" id="registrationId">
        <%
            try (Connection conn = DBConnection.getConnection()){

                String query = "SELECT registrationId FROM students";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    String regId = rs.getString("registrationId");
                    out.println("<option value='" + regId + "'>" + regId + "</option>");
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </select><br><br>

    <label for="academicFee">Academic Fee:</label>
    <input type="number" name="academicFee" id="academicFee" required><br><br>

    <label for="hostelFee">Hostel Fee:</label>
    <input type="number" name="hostelFee" id="hostelFee"><br><br>

    <label for="examFee">Exam Fee:</label>
    <input type="number" name="examFee" id="examFee"><br><br>

    <button type="submit">Add Fees</button>
</form>
</body>
</html>
