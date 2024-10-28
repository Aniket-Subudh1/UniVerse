<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Dashboard - Assigned Courses</title>
    <link rel="stylesheet" href="styles/ViewAssignSub.css?v=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
<div class="container">
    <nav class="navbar">
        <h2 class="logo">UniVerse</h2>
        <ul class="nav-links">
            <li><a href="teacher-dashboard.jsp">Home</a></li>
            <li><a href="Manage-Course.jsp">Courses</a></li>
            <li><a href="add_grade.jsp">Grades</a></li>
            <li><a href="teacher-attendance.jsp">Attendance</a></li> </ul>
    </nav>
</div>
    <div class="dashboard">
        <h2>Your Assigned Subjects</h2>

        <br>
        <button id="loadCoursesButton" class="btn">Load Assigned Subjects</button>
        <br> <br>
        <div id="error-message" class="error-message" style="display: none;">No subjects found</div>

        <!-- Table to display assigned courses -->
        <table id="coursesTable">
            <thead>
            <tr>
                <th>Subject ID</th>
                <th>Subject Name</th>
            </tr>
            </thead>
            <tbody id="coursesTableBody">
            <tr><td colspan="2">Press "Load Assigned Subjects" to see your subjects.</td></tr>
            </tbody>
        </table>
    </div>


<script src="script/dark-mode.js"></script> <!-- Universal dark mode script -->
<script src="script/ViewAssignSub.js"></script> <!-- Page-specific script -->
</body>
</html>
