<%@ page import="java.util.List" %>
<%@ page import="com.cms.model.ViewGrades" %>
<!DOCTYPE html>
<html>
<head>
    <title>Assign Grades</title>
    <link rel="stylesheet" href="styles/ViewGrades.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>

<!-- Navbar Section -->
<div class="container">
    <nav class="navbar">
        <h2 class="logo">UniVerse</h2>
        <ul class="nav-links">
            <li><a href="teacher-dashboard.jsp">Home</a></li>
            <li><a href="Manage-Course.jsp">Courses</a></li>
            <li><a href="ViewGrades.jsp">Grades</a></li>
            <li><a href="attendance.jsp">Attendance</a></li>
        </ul>
    </nav>
</div>

<!-- Main Content Section -->
<h1>Enter Details to Assign Grades</h1>

<form action="assignGrades" method="post">
    <label for="studentId">Student Registration ID:</label>
    <input type="text" id="studentId" name="studentId" required>

    <label for="subjectCode">Subject Code:</label>
    <input type="text" id="subjectCode" name="subjectCode" required>

    <label for="grade">Assign Grade:</label>
    <input type="text" id="grade" name="grade" required>

    <button type="submit">Submit Grade</button>
</form>

<h2>Assigned Grades:</h2>

<table border="1">
    <thead>
    <tr>
        <th>Student Registration ID</th>
        <th>Subject Code</th>
        <th>Grade</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<ViewGrades> gradesList = (List<ViewGrades>) request.getAttribute("assignedGrades");
        if (gradesList != null && !gradesList.isEmpty()) {
            for (ViewGrades grade : gradesList) {
    %>
    <tr>
        <td><%= grade.getStudentId() %></td>
        <td><%= grade.getSubjectCode() %></td>
        <td><%= grade.getGrade() %></td>
    </tr>
    <%
        }
    } else if (request.getAttribute("errorMessage") != null) {
    %>
    <tr>
        <td colspan="3"><%= request.getAttribute("errorMessage") %></td>
    </tr>
    <%
    } else {
    %>
    <tr>
        <td colspan="3">Please enter details to assign a grade.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<script src="script/dark-mode.js"></script>

</body>
</html>
