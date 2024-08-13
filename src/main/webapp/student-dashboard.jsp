<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="styles/student-dashboard.css?v=1.0">
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

            <!-- Display Student's Photo or Default Image -->
            <li>
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
            </li>

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

    <!-- Profile Modal -->
    <div id="profileModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeProfileModal()">&times;</span>
            <h2>Profile Information</h2>
            <img src="<%= photoData %>" alt="Profile Picture" class="profile-pic-modal"> <!-- Display the profile picture -->
            <p><strong>Name:</strong> <%= session.getAttribute("name") %></p>
            <p><strong>ID:</strong> <%= session.getAttribute("id") %></p>
            <p><strong>Enrolled Courses:</strong></p>
            <ul>
                <%
                    List<String> courses = (List<String>) session.getAttribute("enrolledCourses");
                    if (courses != null) {
                        for (String course : courses) {
                            out.println("<li>" + course + "</li>");
                        }
                    } else {
                        out.println("<li>No courses enrolled yet.</li>");
                    }
                %>
            </ul>
            <a href="edit-profile.jsp" class="edit-profile-btn">Edit Profile</a>
        </div>
    </div>
</div>

<script src="script/student-dashboard.js"></script>
</body>
</html>
