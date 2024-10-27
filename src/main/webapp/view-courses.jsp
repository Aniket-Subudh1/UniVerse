<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Registered Courses</title>
    <link rel="stylesheet" href="styles/view-courses.css?v=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
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
        </ul>
    </nav>

    <h2 class="page-title">Registered Courses</h2>

    <!-- Add a Load Courses Button -->
    <div class="load-courses-container">
        <button class="btn-load" id="loadCoursesBtn">Load Courses</button>
    </div>

    <div class="courses-table-container">
        <table class="courses-table">
            <thead>
            <tr>
                <th>Course ID</th>
                <th>Course Name</th>

            </tr>
            </thead>
            <tbody id="coursesTableBody">
            <tr>
                <td colspan="3">No registered courses found</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<script src="script/view-courses.js"></script>
<script src="script/dark-mode.js"></script>
</body>
</html>
