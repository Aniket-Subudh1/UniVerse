<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Timetable</title>
    <link rel="stylesheet" href="styles/manage-timetable.css?v=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
<div class="container">
    <nav class="navbar">
        <h2 class="logo">UniVerse Admin</h2>
        <ul class="nav-links">
            <li><a href="manage-attendance.jsp">Attendance</a></li>
            <li><a href="manage-timetable.jsp">Timetable</a></li>
            <li><a href="manage-courses.jsp">Courses</a></li>
            <li><a href="manage-grades.jsp">Grades</a></li>
        </ul>
        <div class="profile-info">
            <p class="profile-name"><%= session.getAttribute("name") %></p>
            <p class="profile-role"><%= session.getAttribute("role") %></p>
        </div>
        <div class="dark-mode-toggle" id="dark-mode-toggle" onclick="toggleDarkMode()">
            <span class="sun"><i class='bx bx-sun'></i></span>
            <span class="moon"><i class='bx bx-moon'></i></span>
        </div>
    </nav>

    <div class="dashboard-sections">
        <div class="card">
            <i class='bx bx-calendar'></i>
            <h3>Manage Student Timetable</h3>
            <p>Upload and manage the student timetable.</p>
            <a href="upload-student-timetable.jsp">Manage Student Timetable</a>
        </div>
        <div class="card">
            <i class='bx bx-time'></i>
            <h3>Manage Teacher Timetable</h3>
            <p>Upload and manage the teacher timetable.</p>
            <a href="upload-teacher-timetable.jsp">Manage Teacher Timetable</a>
        </div>
    </div>
</div>

<script src="script/manage-timetable.js"></script>
</body>
</html>
