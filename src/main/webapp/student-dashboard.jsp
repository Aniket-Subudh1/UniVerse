<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="styles/student-dashboard.css">
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
            <li><a href="#">Profile</a></li>
            <li><a href="index.jsp">Logout</a></li>
        </ul>
    </nav>

    <div class="welcome-section">
        <h1>Welcome, <%= session.getAttribute("name") %>!</h1>
        <p>Your student dashboard is here to help you navigate your courses, check your grades, and track your attendance.</p>
    </div>

    <div class="dashboard-sections">
        <div class="card">
            <i class='bx bx-book-open'></i>
            <h3>My Courses</h3>
            <p>View and manage your enrolled courses.</p>
            <a href="#">View Courses</a>
        </div>
        <div class="card">
            <i class='bx bx-award'></i>
            <h3>My Grades</h3>
            <p>Check your grades for all courses.</p>
            <a href="#">View Grades</a>
        </div>
        <div class="card">
            <i class='bx bx-calendar-check'></i>
            <h3>My Attendance</h3>
            <p>Track your attendance record.</p>
            <a href="#">View Attendance</a>
        </div>
    </div>
</div>
</body>
</html>
