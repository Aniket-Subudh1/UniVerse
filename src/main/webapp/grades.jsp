<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cms.dao.DBConnection" %>
<%@ page import="com.cms.model.Grade" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>View Grades</title>
    <link rel="stylesheet" href="styles/ViewGrades.css?v=1.0">
    <script>
        function fetchGrades() {
            var studentId = document.getElementById("studentId").value;
            if (studentId) {
                window.location.href = "grades?studentId=" + studentId;
            }
        }
    </script>
</head>
<body>

<div class="container">
    <nav class="navbar">
        <h2 class="logo">UniVerse</h2>
        <ul class="nav-links">
            <li><a href="student-dashboard.jsp">Home</a></li>
            <li><a href="register-course.jsp">Courses</a></li>
            <li><a href="grades.jsp">Grades</a></li>
            <li><a href="#">Attendance</a></li>
            <!-- Logout with Icon -->

        </ul>
    </nav>
</div>
<h1>View Grades</h1>

<label for="studentId">Select Student ID:</label>
<select id="studentId" onchange="fetchGrades()">
    <option value="">-- Select Student ID --</option>
    <%
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT DISTINCT studentId FROM grades";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("studentId");
    %>
    <option value="<%= id %>"><%= id %></option>
    <%
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    %>
</select>

<h2>Grades</h2>
<table border="1">
    <thead>
    <tr>
        <th>Course ID</th>
        <th>Grade</th>
        <th>Created At</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Grade> grades = (List<Grade>) request.getAttribute("grades");
        if (grades != null) {
            for (Grade grade : grades) {
    %>
    <tr>
        <td><%= grade.getCourseId() %></td>
        <td><%= grade.getGrade() %></td>
        <td><%= grade.getCreatedAt() %></td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
</body>
</html>
