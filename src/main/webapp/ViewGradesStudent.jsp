<%@ page import="java.util.List" %>
<%@ page import="com.cms.model.ViewGrades" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>View Grades - Student</title>
    <link rel="stylesheet" href="styles/ViewGrades.css">
</head>
<body>

<!-- Navbar -->
<div class="navbar">
    <div class="logo">UniVerse</div>
    <ul class="nav-links">
        <li><a href="student-dashboard.jsp">Home</a></li>
        <li><a href="view-courses.jsp">Courses</a></li>
        <li><a href="ViewGradesStudent.jsp">Grades</a></li>
        <li><a href="student-attendance.jsp">Attendance</a></li>
    </ul>
</div>

<!-- Main Content -->
<div class="container">
    <h1>Enter Your Registration ID to View Grades</h1>

    <form action="viewGradesStudent" method="post" class="form-section">
        <label for="studentId">Student Registration ID:</label>
        <input type="text" id="studentId" name="studentId" required>
        <button type="submit" class="submit-button">View Grades</button>
    </form>

    <h2>Your Grades:</h2>

    <table class="grades-table">
        <thead>
        <tr>
            <th>Subject Code</th>
            <th>Grade</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<ViewGrades> gradesList = (List<ViewGrades>) request.getAttribute("studentGrades");
            if (gradesList != null && !gradesList.isEmpty()) {
                for (ViewGrades grade : gradesList) {
        %>
        <tr>
            <td><%= grade.getSubjectCode() %></td>
            <td><%= grade.getGrade() %></td>
        </tr>
        <%
            }
        } else if (request.getAttribute("errorMessage") != null) {
        %>
        <tr>
            <td colspan="2"><%= request.getAttribute("errorMessage") %></td>
        </tr>
        <%
        } else {
        %>
        <tr>
            <td colspan="2">Please enter your registration ID to view your grades.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

<script src="script/dark-mode.js"></script>
</body>
</html>
