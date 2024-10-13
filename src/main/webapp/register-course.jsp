<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Course Registration</title>
    <link rel="stylesheet" href="styles/student-dashboard.css?v=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
<div class="container">
    <nav class="navbar">
        <h2 class="logo">UniVerse</h2>
        <ul class="nav-links">
            <li><a href="#">Home</a></li>
            <li><a href="#">Courses</a></li>
            <li><a href="#">Grades</a></li>
            <li><a href="#">Attendance</a></li>
            <!-- Logout with Icon -->
            <li>
                <a href="home.jsp" class="logout-link">
                    <i class='bx bx-exit'></i> Logout
                </a>
            </li>
        </ul>
    </nav>
</div>

<!-- Main Content for Course Registration -->
<div class="dashboard-sections">
    <!-- Section for showing available courses as cards -->
    <div class="card">
        <h3>Available Courses</h3>
        <div id="coursesContainer" class="courses-container">
            <!-- Cards will be dynamically created here using JS -->
        </div>
    </div>

    <!-- Section for showing success/failure messages -->
    <div class="card">
        <h3>Message</h3>
        <div id="message" class="message"></div>
    </div>
</div>

<!-- Hidden field to pass the student registration ID from the session -->
<input type="hidden" id="studentRegistrationId" value="<%= session.getAttribute("registrationId") %>">

<!-- Include necessary JavaScript files -->
<script src="script/dark-mode.js"></script> <!-- Universal dark mode script -->
<script src="script/register-course.js"></script> <!-- Course registration script -->
</body>
</html>
