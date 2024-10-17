<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Base64" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Timetable</title>
    <link rel="stylesheet" href="styles/student-timetable.css?v=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
<div class="container">
    <nav class="navbar">
        <h2 class="logo">UniVerse</h2>
        <ul class="nav-links">
            <li><a href="student-dashboard.jsp">Home</a></li>
            <li><a href="view-courses.jsp">Courses</a></li>
            <li><a href="view-grades.jsp">Grades</a></li>
            <li><a href="view-attendance.jsp">Attendance</a></li>
            <li><a href="view-student-timetable.jsp">Timetable</a></li>
        </ul>
    </nav>
</div>
<div class="dashboard-sections">
    <h2>Your Timetable</h2>
    <div id="timetableContainer">
        <p>Loading your timetable...</p> <!-- This will be replaced by JavaScript -->
    </div>
</div>

<!-- JS Files -->
<script src="script/dark-mode.js"></script> <!-- Dark Mode Script -->
<script src="script/student-timetable.js"></script> <!-- Fetch Timetable Data -->
</body>
</html>
