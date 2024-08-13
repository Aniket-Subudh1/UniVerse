<%@ page import="java.util.Base64" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<<<<<<< HEAD
    <title>Teacher Dashboard</title>
    <link rel="stylesheet" href="styles/teacher-dashboard.css?v=1.0">
=======
    <title>Teacher-Dashboard</title>
    <link rel="stylesheet" href="styles/teacher-dashboard.css">
>>>>>>> 27469c7b538d058279f6e79aa71046b264a24c2f
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <style>
        /* CSS for full-page background video */
        .video-bg {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            object-fit: cover;
            z-index: -1; /* Ensure video is behind all content */
        }

        /* Styling to ensure content visibility on the video background */
        .container {
            position: relative;
            z-index: 1; /* Ensure the container content is above the video */
            color: white; /* Adjust text color for better visibility on video background */
        }

        .welcome-section h1,
        .welcome-section p,
        .dashboard-sections .card {
            background: rgba(0, 0, 0, 0.5); /* Semi-transparent background for better readability */
            padding: 10px;
            border-radius: 5px;
        }

        /* Full-page height to ensure proper display */
        body, html {
            height: 100%;
            margin: 0;
            padding: 0;
            overflow: hidden; /* Prevent scrolling */
        }
    </style>
</head>

<body>
<!-- Background Video -->
<video autoplay loop muted class="video-bg">
    <source src="styles/University%20.mp4" type="video/mp4">
    Your browser does not support the video tag.
</video>
<div class="container">
    <nav class="navbar">
        <h2 class="logo">UniVerse</h2>
        <ul class="nav-links">
            <li><a href="#">Home</a></li>
            <li><a href="#">Manage Courses</a></li>
            <li><a href="#">Student Grades</a></li>
            <li><a href="#">Attendance</a></li>

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
        <h1>Welcome, <%= session.getAttribute("name") %>!</h1>
        <p>Your teacher dashboard is here to help you manage your courses, view student grades, and track attendance.</p>
    </div>

    <div class="dashboard-sections">
        <div class="card">
            <i class='bx bx-chalkboard'></i>
            <h3>Manage Courses</h3>
            <p>Create, update, and delete courses you are teaching.</p>
            <a href="#">Manage Courses</a>
        </div>
        <div class="card">
            <i class='bx bx-list-check'></i>
            <h3>Student Grades</h3>
            <p>View and manage grades for students enrolled in your courses.</p>
            <a href="#">View Grades</a>
        </div>
        <div class="card">
            <i class='bx bx-calendar'></i>
            <h3>Attendance</h3>
            <p>Track and update attendance records for your classes.</p>
            <a href="#">Track Attendance</a>
        </div>
    </div>

    <!-- Profile Modal -->
    <div id="profileModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeProfileModal()">&times;</span>
            <h2>Profile Information</h2>
            <img src="<%= photoData %>" alt="Profile" class="profile-pic-modal">
            <p><strong>Name:</strong> <%= session.getAttribute("name") %></p>
            <p><strong>ID:</strong> <%= session.getAttribute("id") %></p>
            <p><strong>Email:</strong> <%= session.getAttribute("email") %></p>
            <a href="EditProfile.jsp" class="edit-profile-btn">Edit Profile</a>
        </div>
    </div>
</div>

<script src="script/teacher-dashboard.js"></script>
</body>
</html>
