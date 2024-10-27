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
            var courseId = document.getElementById("courseId").value;
            if (courseId) {
                window.location.href = "grades?courseId=" + courseId; // Adjusted to send courseId
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
            <li><a href="view-courses.jsp">Courses</a></li>
            <li><a href="grades.jsp">Grades</a></li>
            <li><a href="student-attendance.jsp">Attendance</a></li>
            <!-- Logout with Icon -->
        </ul>
    </nav>
</div>

<h1>View Grades</h1>

<form>
    <label for="courseId">Select Course ID:</label>
    <select id="courseId" name="courseId" onchange="fetchGrades()">
        <option value="">-- Select Course ID --</option>
        <%
            // Fetch the registrationId from session
            String registrationId = (String) request.getSession().getAttribute("registrationId");
            if (registrationId != null) {
                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "SELECT DISTINCT courseId FROM grades WHERE studentId = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, registrationId);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String courseId = rs.getString("courseId");
        %>
        <option value="<%= courseId %>"><%= courseId %></option>
        <%
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        %>
    </select>
</form>

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
    } else {
    %>
    <tr>
        <td colspan="3">No grades found for this Course ID.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

<script src="script/dark-mode.js"></script>

</body>
</html>
