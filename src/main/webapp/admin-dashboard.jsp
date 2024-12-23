<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="styles/admin-dashboard.css?v=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
<div class="container">
    <nav class="navbar">
        <h2 class="logo">UniVerse Admin</h2>
        <ul class="nav-links">
            <li><a href="admin-dashboard.jsp">Home</a></li>
            <li><a href="admin-attendance.jsp">Attendance</a></li>
            <li><a href="manage-routine.jsp">Timetable</a></li>
            <li><a href="create-course.jsp">Courses</a></li>

            <div class="profile-info">
                <p class="profile-name"><%= session.getAttribute("name") %></p>
                <p class="profile-role"><%= session.getAttribute("role") %></p>
            </div>
            <div class="dark-mode-toggle" id="dark-mode-toggle">
                <span class="sun"><i class='bx bx-sun'></i></span>
                <span class="moon"><i class='bx bx-moon'></i></span>
            </div>
            <!-- Logout with Icon -->
            <li>
                <a href="home.jsp" class="logout-link">
                    <i class='bx bx-exit'></i> Logout
                </a>
            </li>
        </ul>
    </nav>

    <div class="dashboard-sections">
        <div class="card">
            <i class='bx bx-calendar'></i>
            <h3>Manage Attendance</h3>
            <p>Track and update attendance records for students.</p>
            <a href="admin-attendance.jsp">Manage Attendance</a>
        </div>
        <div class="card">
            <i class='bx bx-time'></i>
            <h3>Manage Timetable</h3>
            <p>View and manage the teaching timetable.</p>
            <a href="manage-routine.jsp">Manage Timetable</a>
        </div>
        <div class="card">
            <i class='bx bx-wallet'></i>
            <h3>Manage Fees</h3>
            <p>Check and update student fee details and payment status.</p>
            <a href="manage-fees.jsp">Manage Fees</a>
        </div>
        <div class="card">
            <i class='bx bx-book'></i>
            <h3>Manage Courses</h3>
            <p>Create, update, and delete courses.</p>
            <a href="create-course.jsp">Manage Courses</a>
        </div>

        <div class="card">
            <i class='bx bx-comment-dots'></i>
            <h3>View Feedback</h3>
            <p>Review feedback provided by students.</p>
            <a href="admin-view-feedback.jsp">View Feedback</a>
        </div>
        <div class="card">
            <i class='bx bx-user-pin'></i>
            <h3>Assign Subjects</h3>
            <p>Assign subjects to teachers.</p>
            <a href="assign-subjects.jsp">Assign Subjects</a>
        </div>
        <!-- New Card for Adding Teacher and Student Registration ID -->
        <div class="card">
            <i class='bx bx-id-card'></i>
            <h3>Assign Registration IDs</h3>
            <p>Add registration IDs for teachers and students, and assign courses to students.</p>
            <a href="assign-registration.jsp">Assign Registration</a>
        </div>
    </div>
</div>

<script src="script/dark-mode.js"></script> <!-- Universal dark mode script -->
<script src="script/admin-dashboard.js"></script> <!-- Page-specific script -->
</body>
</html>
