<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Base64" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="styles/admin-dashboard.css">
</head>
<body>
<nav class="navbar">
    <h2 class="logo">UniVerse Admin</h2>
    <ul class="nav-links">
        <li><a href="manage-attendance.jsp">Manage Attendance</a></li>
        <li><a href="manage-timetable.jsp">Manage Timetable</a></li>
        <li><a href="manage-fees.jsp">Manage Fees</a></li>
        <li><a href="manage-courses.jsp">Manage Courses</a></li>
        <li><a href="manage-grades.jsp">Manage Grades</a></li>
        <li><a href="view-feedback.jsp">View Feedback</a></li>
        <li><a href="index.jsp">Logout</a></li>
    </ul>
</nav>

<div class="container">
    <div class="welcome-section">
        <h1>Welcome, Admin!</h1>
        <p>Your admin dashboard is here to help you manage the entire university system efficiently.</p>
    </div>

    <div class="dashboard-sections">
        <div class="card">
            <i class='bx bx-calendar'></i>
            <h3>Manage Attendance</h3>
            <a href="manage-attendance.jsp">Manage Attendance</a>
        </div>
        <div class="card">
            <i class='bx bx-time'></i>
            <h3>Manage Timetable</h3>
            <a href="manage-timetable.jsp">Manage Timetable</a>
        </div>
        <div class="card">
            <i class='bx bx-wallet'></i>
            <h3>Manage Fees</h3>
            <a href="manage-fees.jsp">Manage Fees</a>
        </div>
        <div class="card">
            <i class='bx bx-book'></i>
            <h3>Manage Courses</h3>
            <a href="manage-courses.jsp">Manage Courses</a>
        </div>
        <div class="card">
            <i class='bx bx-award'></i>
            <h3>Manage Grades</h3>
            <a href="manage-grades.jsp">Manage Grades</a>
        </div>
        <div class="card">
            <i class='bx bx-comment-dots'></i>
            <h3>View Feedback</h3>
            <a href="view-feedback.jsp">View Feedback</a>
        </div>
        <!-- New card for assigning subjects to teachers -->
        <div class="card">
            <i class='bx bx-user-pin'></i>
            <h3>Assign Subjects</h3>
            <a href="assign-subjects.jsp">Assign Subjects to Teachers</a>
        </div>
    </div>
</div>
</body>
</html>
