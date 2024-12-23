<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Assign Subjects to Teachers</title>
    <link rel="stylesheet" href="styles/assign-subjects.css?v=1.0">
</head>
<body>
<div class="container">

    <nav class="navbar">
        <h2 class="logo">UniVerse</h2>
        <ul class="nav-links">
            <li><a href="admin-dashboard.jsp">Home</a></li>
            <li><a href="admin-attendance.jsp">Attendance</a></li>
            <li><a href="manage-routine.jsp">Timetable</a></li>
            <li><a href="create-course.jsp">Courses</a></li>
            <li>
                <div class="dark-mode-toggle" id="dark-mode-toggle">
                    <span class="sun"><i class='bx bx-sun'></i></span>
                    <span class="moon"><i class='bx bx-moon'></i></span>
                </div>
            </li>
        </ul>
    </nav>

    <div class="dashboard-sections">
        <!-- Assign Subject Card -->
        <div class="card">
            <h3>Assign a Teacher to a Course</h3>
            <form action="assignSubjects" method="post">
                <label for="courseId">Select Course:</label>
                <select id="courseId" name="courseId" required></select>
                <br><br>
                <label for="registrationId">Select Teacher (by Registration ID):</label>
                <select id="registrationId" name="registrationId" required></select>
                <br><br>
                <button type="submit" class="btn">Assign Teacher</button>
            </form>
        </div>

        <!-- Available Courses Card -->
        <div class="card">
            <h3>Available Courses</h3>
            <table id="courseTable">
                <thead>
                <tr>
                    <th>Course ID</th>
                    <th>Course Name</th>
                    <th>Course Description</th>
                </tr>
                </thead>
                <tbody id="courseTableBody">
                <tr><td colspan="3">Loading courses...</td></tr>
                </tbody>
            </table>
        </div>

        <!-- Available Teachers Card -->
        <div class="card">
            <h3>Available Teachers</h3>
            <table id="teacherTable">
                <thead>
                <tr>
                    <th>Registration ID</th>
                    <th>Teacher Name</th>
                    <th>Photo</th>
                </tr>
                </thead>
                <tbody id="teacherTableBody">
                <tr><td colspan="3">Loading teachers...</td></tr>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Success Modal -->
    <div id="successModal" class="modal">
        <div class="modal-content">
            <h2>Success</h2>
            <p>Subject assigned successfully!</p>
            <button class="btn" id="closeModalButton">OK</button>
        </div>
    </div>
</div>

<script src="script/assign-subjects.js"></script>
<script src="script/dark-mode.js"></script>
</body>
</html>
