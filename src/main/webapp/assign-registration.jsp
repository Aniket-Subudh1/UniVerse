<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Assign Registration IDs</title>
    <link rel="stylesheet" href="styles/assign-registration.css?v=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
<div class="container">
    <nav class="navbar">
        <h2 class="logo">UniVerse Admin</h2>
        <ul class="nav-links">
            <li><a href="admin-dashboard.jsp">Home</a></li>
            <li><a href="manage-attendance.jsp">Attendance</a></li>
            <li><a href="manage-timetable.jsp">Timetable</a></li>
            <li><a href="manage-courses.jsp">Courses</a></li>



        <div class="dark-mode-toggle" id="dark-mode-toggle">
            <span class="sun"><i class='bx bx-sun'></i></span>
            <span class="moon"><i class='bx bx-moon'></i></span>
        </div>

        <li>
            <a href="home.jsp" class="logout-link">
                <i class='bx bx-exit'></i> Logout
            </a>
        </li>
        </ul>
    </nav>

    <h2>Assign Registration IDs and Enrolled Courses</h2>

    <div class="dashboard-sections">
        <!-- Card for Assigning Teacher Registration ID -->
        <div class="card">
            <h3>Assign Teacher Registration ID</h3>
            <form action="AssignTeacherRegServlet" method="post">
                <label for="teacherName">Teacher Name:</label>
                <input type="text" id="teacherName" name="teacherName" required>

                <label for="teacherRegId">Registration ID:</label>
                <input type="text" id="teacherRegId" name="teacherRegId" required>

                <button type="submit">Assign Registration ID</button>
            </form>
        </div>

        <!-- Card for Assigning Student Registration ID and Course -->
        <div class="card">
            <h3>Assign Student Registration ID and Course</h3>
            <form action="AssignStudentRegServlet" method="post">
                <label for="studentName">Student Name:</label>
                <input type="text" id="studentName" name="studentName" required>

                <label for="studentRegId">Registration ID:</label>
                <input type="text" id="studentRegId" name="studentRegId" required>

                <label for="enrolledCourse">Enrolled Course:</label>
                <input type="text" id="enrolledCourse" name="enrolledCourse" required>

                <button type="submit">Assign Registration ID and Course</button>
            </form>
        </div>
    </div>
</div>

<script src="script/dark-mode.js"></script> <!-- Universal dark mode script -->

</body>
</html>
