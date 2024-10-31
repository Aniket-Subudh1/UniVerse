<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cms.dao.DBConnection" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Grade</title>
    <link rel="stylesheet" href="styles/addgrade.css">
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
</head>
<body>

<div class="navbar-container">
    <nav class="navbar">
        <h2 class="logo">UniVerse</h2>
        <ul class="nav-links">
            <li><a href="teacher-dashboard.jsp">Home</a></li>
            <li><a href="Manage-Course.jsp">Courses</a></li>
            <li><a href="add_grade.jsp">Grades</a></li>
            <li><a href="teacher-attendance.jsp">Attendance</a></li>
            <li>
                <div class="dark-mode-toggle" id="dark-mode-toggle">
                    <span class="sun"><i class='bx bx-sun'></i></span>
                    <span class="moon"><i class='bx bx-moon'></i></span>
                </div>
            </li>
        </ul>
    </nav>
</div>

<div class="content-container">
    <div class="card">
        <h2>Add Grade</h2>

        <!-- Display success message if applicable -->
        <%
            String successMessage = (String) request.getAttribute("successMessage");
            if (successMessage != null) {
        %>
        <div class="success-message">
            <p><%= successMessage %></p>
        </div>
        <%
            }
        %>

        <form action="GradeManagementServlet" method="post">
            <label for="studentId">Student ID:</label>
            <select id="studentId" name="studentId" required>
                <option value="">Select Student ID</option>
                <%
                    try (Connection conn = DBConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT studentId FROM student_course_tracking");
                         ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            String studentId = rs.getString("studentId");
                %>
                <option value="<%= studentId %>"><%= studentId %></option>
                <%
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                %>
            </select>

            <br><br>

            <label for="courseId">Course ID:</label>
            <select id="courseId" name="courseId" required>
                <option value="">Select Course ID</option>
                <%
                    try (Connection conn = DBConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT courseId FROM student_course_tracking");
                         ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            String courseId = rs.getString("courseId");
                %>
                <option value="<%= courseId %>"><%= courseId %></option>
                <%
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                %>
            </select>

            <br><br>

            <label for="grade">Grade:</label>
            <input type="text" id="grade" name="grade" required pattern="[A-Fa-f]">
            <small>(Enter a grade from A to F)</small>

            <br><br>

            <input type="submit" value="Add Grade">
        </form>
    </div>
</div>

<script src="script/dark-mode.js"></script>
</body>
</html>
