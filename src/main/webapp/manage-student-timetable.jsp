<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Student Timetable</title>
    <link rel="stylesheet" href="styles/student-timetable.css?v=1.0">
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
        <div class="dark-mode-toggle" id="dark-mode-toggle">
            <span class="sun"><i class='bx bx-sun'></i></span>
            <span class="moon"><i class='bx bx-moon'></i></span>
        </div>
    </nav>

    <div class="dashboard-sections">
        <div class="card">
            <i class='bx bx-upload'></i>
            <h3>Upload Student Timetable</h3>
            <p>Upload the latest timetable for students.</p>
            <form action="uploadStudentTimetable" method="post" enctype="multipart/form-data">
                <input type="file" name="studentTimetable" accept=".xls, .xlsx" required>
                <button type="submit" class="btn">Upload</button>
            </form>
        </div>
        <div class="card">
            <i class='bx bx-show-alt'></i>
            <h3>View Uploaded Timetable</h3>
            <p>View the uploaded timetable for students.</p>
            <a href="view-student-timetable.jsp" class="btn">View Timetable</a>
        </div>
    </div>
</div>

<script src="script/dark-mode.js"></script> <!-- Universal dark mode script -->
<script src="script/student-timetable.js"></script>
</body>
</html>
