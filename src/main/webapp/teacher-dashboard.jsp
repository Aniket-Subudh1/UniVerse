<%@ page import="java.util.Base64" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Dashboard</title>
    <link rel="stylesheet" href="styles/teacher-dashboard.css?v=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>

<body>
<!-- Removed the video background -->

<div class="container">
    <nav class="navbar">
        <h2 class="logo">UniVerse</h2>
        <ul class="nav-links">
            <li><a href="home.jsp">Home</a></li> <!-- Updated href -->
            <li><a href="manage-courses.jsp">Manage Courses</a></li> <!-- Updated href -->
            <li><a href="student-grades.jsp">Student Grades</a></li> <!-- Updated href -->
            <li><a href="attendance.jsp">Attendance</a></li> <!-- Updated href -->

            <!-- Display Teacher's Photo or Default Image -->
            <li>
                <%
                    byte[] photo = (byte[]) session.getAttribute("photo");
                    String photoData = "";
                    if (photo != null) {
                        photoData = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(photo);
                    } else {
                        photoData = "img/default-profile.png"; // Path to a default profile image
                    }
                %>
                <img src="<%= photoData %>" alt="Profile" class="profile-pic" onclick="showProfileModal()">
            </li>

            <li><a href="index.jsp">Logout</a></li>
        </ul>
    </nav>

    <div class="welcome-section">
        <h1>Welcome, <%= session.getAttribute("name") != null ? session.getAttribute("name") : "Teacher" %>!</h1> <!-- Null-safe session attribute -->
        <p>Your teacher dashboard is here to help you manage your courses, view student grades, and track attendance.</p>
    </div>

    <div class="dashboard-sections">
        <div class="card">
            <i class='bx bx-chalkboard'></i>
            <h3>Manage Courses</h3>
            <p>Create, update, and delete courses you are teaching.</p>
            <a href="manage-courses.jsp">Manage Courses</a> <!-- Updated href -->
        </div>
        <div class="card">
            <i class='bx bx-list-check'></i>
            <h3>Student Grades</h3>
            <p>View and manage grades for students enrolled in your courses.</p>
            <a href="student-grades.jsp">View Grades</a> <!-- Updated href -->
        </div>
        <div class="card">
            <i class='bx bx-calendar'></i>
            <h3>Attendance</h3>
            <p>Track and update attendance records for your classes.</p>
            <a href="attendance.jsp">Track Attendance</a> <!-- Updated href -->
        </div>
        <!-- New Timetable Card -->
        <div class="card">
            <i class='bx bx-calendar-event'></i>
            <h3>Timetable</h3>
            <p>View and manage your teaching timetable.</p>
            <a href="timetable.jsp">View Timetable</a> <!-- New href -->
        </div>
        <!-- New Notes Card -->
        <div class="card">
            <i class='bx bx-note'></i>
            <h3>Notes</h3>
            <p>Access and upload teaching notes and materials.</p>
            <a href="notes.jsp">Manage Notes</a> <!-- New href -->
        </div>
    </div>

    <!-- Profile Modal -->
    <div id="profileModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeProfileModal()">&times;</span>
            <h2>Profile Information</h2>
            <img src="<%= photoData %>" alt="Profile" class="profile-pic-modal">
            <p><strong>Name:</strong> <%= session.getAttribute("name") != null ? session.getAttribute("name") : "N/A" %></p> <!-- Null-safe session attribute -->
            <p><strong>ID:</strong> <%= session.getAttribute("id") != null ? session.getAttribute("id") : "N/A" %></p> <!-- Null-safe session attribute -->
            <p><strong>Email:</strong> <%= session.getAttribute("email") != null ? session.getAttribute("email") : "N/A" %></p> <!-- Null-safe session attribute -->
            <a href="edit-profile.jsp" class="edit-profile-btn">Edit Profile</a>
        </div>
    </div>
</div>

<script src="script/teacher-dashboard.js"></script>
</body>
</html>
