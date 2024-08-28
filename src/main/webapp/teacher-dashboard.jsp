<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Base64" %>
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
<div class="container">
    <nav class="navbar">
        <h2 class="logo">UniVerse</h2>
        <ul class="nav-links">
            <li><a href="home.jsp">Home</a></li>
            <li><a href="updateAttendance.jsp">Attendance</a></li>
            <li><a href="timetable.jsp">Timetable</a></li>
            <li><a href="notes.jsp">Notes</a></li>

            <!-- Dark Mode Toggle -->
            <li>
                <div class="dark-mode-toggle" id="dark-mode-toggle">
                    <span class="sun"><i class='bx bx-sun'></i></span>
                    <span class="moon"><i class='bx bx-moon'></i></span>
                </div>
            </li>

            <!-- Profile Section -->
            <li>
                <div class="profile-info">
                    <div>
                        <p class="profile-name"><%= session.getAttribute("name") %></p>
                        <p class="profile-role"><%= session.getAttribute("role") %></p>
                    </div>
                    <%
                        byte[] photo = (byte[]) session.getAttribute("photo");
                        String photoData = "";
                        if (photo != null && photo.length > 0) {
                            photoData = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(photo);
                        } else {
                            photoData = "img/default-profile.png"; // Path to a default profile image
                        }
                    %>
                    <img src="<%= photoData %>" alt="Profile" class="profile-pic" onclick="showProfileModal()">
                </div>
            </li>
            <!-- Logout with Icon -->
            <li>
                <a href="index.jsp" class="logout-link">
                    <i class='bx bx-exit'></i> Logout
                </a>
            </li>
        </ul>
    </nav>

    <div class="dashboard-sections">
        <div class="card">
            <i class='bx bx-chalkboard'></i>
            <h3>Manage Courses</h3>
            <p>Create, update, and delete courses you are teaching.</p>
            <a href="manage-courses.jsp">Manage Courses</a>
        </div>
        <div class="card">
            <i class='bx bx-list-check'></i>
            <h3>Student Grades</h3>
            <p>View and manage grades for students enrolled in your courses.</p>
            <a href="student-grades.jsp">View Grades</a>
        </div>
        <div class="card">
            <i class='bx bx-calendar'></i>
            <h3>Attendance</h3>
            <p>Track and update attendance records for your classes.</p>
            <a href="updateAttendance.jsp">Track Attendance</a>
        </div>
        <!-- New Timetable Card -->
        <div class="card">
            <i class='bx bx-calendar-event'></i>
            <h3>Timetable</h3>
            <p>View and manage your teaching timetable.</p>
            <a href="timetable.jsp">View Timetable</a>
        </div>
        <!-- New Notes Card -->
        <div class="card">
            <i class='bx bx-note'></i>
            <h3>Notes</h3>
            <p>Access and upload teaching notes and materials.</p>
            <a href="notes.jsp">Manage Notes</a>
        </div>
        <!-- New Assigned Subjects Card -->
        <div class="card">
            <i class='bx bx-book-content'></i>
            <h3>Assigned Subjects</h3>
            <p>View the subjects assigned to you.</p>
            <a href="assigned-subjects.jsp">View Assigned Subjects</a>
        </div>
    </div>

    <!-- Profile Modal -->
    <div id="profileModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeProfileModal()">&times;</span>
            <h2>Profile Information</h2>
            <img src="<%= photoData %>" alt="Profile" class="profile-pic-modal">
            <p><strong>Name:</strong> <%= session.getAttribute("name") != null ? session.getAttribute("name") : "N/A" %></p>
            <p><strong>ID:</strong> <%= session.getAttribute("id") != null ? session.getAttribute("id") : "N/A" %></p>
            <p><strong>Email:</strong> <%= session.getAttribute("email") != null ? session.getAttribute("email") : "N/A" %></p>
            <a href="edit-profile.jsp" class="edit-profile-btn">Edit Profile</a>
        </div>
    </div>
</div>

<script src="script/dark-mode.js"></script> <!-- Universal dark mode script -->
<script src="script/teacher-dashboard.js"></script> <!-- Page-specific script -->
</body>
</html>
