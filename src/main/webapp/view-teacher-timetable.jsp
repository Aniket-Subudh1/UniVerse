<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Timetable</title>
    <link rel="stylesheet" href="styles/teacher-timetable.css?v=1.0">
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
        <li>
            <div class="dark-mode-toggle" id="dark-mode-toggle">
                <span class="sun"><i class='bx bx-sun'></i></span>
                <span class="moon"><i class='bx bx-moon'></i></span>
            </div>
        </li>
    </nav>

</div>
    <div class="dashboard-sections">
        <div class="card">
            <h3>Your Timetable</h3>
            <table id="timetableTable">
                <thead>
                <tr>
                    <th>Enrolled Course</th>
                    <th>Course Name</th>
                    <th>Day</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                </tr>
                </thead>
                <tbody id="timetableTableBody">
                <tr>
                    <td colspan="4">Loading timetable...</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>



<script src="script/dark-mode.js"></script>
<script src="script/view-teacher-timetable.js"></script> <!-- Custom script to load timetable -->
</body>
</html>
