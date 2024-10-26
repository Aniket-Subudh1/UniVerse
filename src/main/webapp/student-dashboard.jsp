<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="styles/student-dashboard.css?v=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
</head>

<body>
<div class="container">
    <nav class="navbar">
        <h2 class="logo">UniVerse</h2>
        <ul class="nav-links">
            <li><a href="#">Home</a></li>
            <li><a href="#">Courses</a></li>
            <li><a href="grades.jsp">Grades</a></li>
            <li><a href="#">Attendance</a></li>
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
                            photoData = "img/default-profile.jpg"; // Path to a default profile image
                        }
                    %>
                    <img src="<%= photoData %>" alt="Profile" class="profile-pic" onclick="showProfileModal()">
                </div>
            </li>
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
            <i class='bx bx-book-open'></i>
            <h3>My Courses</h3>
            <p>View and manage your enrolled courses.</p>
            <a href="view-courses.jsp">View Courses</a>
        </div>
        <div class="card">
            <i class='bx bx-comment-dots'></i>
            <h3>Course Registration</h3>
            <p>Register Your course.</p>
            <a href="register-course.jsp">Register Course</a>
        </div>

        <div class="card">
            <i class='bx bx-award'></i>
            <h3>My Grades</h3>
            <p>Check your grades for all courses.</p>
            <a href="grades.jsp">View Grades</a>
        </div>
        <div class="card">
            <i class='bx bx-calendar-check'></i>
            <h3>My Attendance</h3>
            <p>Track your attendance record.</p>
            <a href="student-attendance.jsp">View Attendance</a>
        </div>
        <div class="card">
            <i class='bx bx-wallet'></i>
            <h3>Fee Payment & Status</h3>
            <p>Check your fee details and payment status.</p>
            <a href="paymentForm.jsp">View Fee Status</a>
        </div>


        <div class="card">
            <i class='bx bx-time'></i>
            <h3>Timetable</h3>
            <p>View your class schedule.</p>
            <a href="view-student-timetable.jsp">View Timetable</a>
        </div>
        <div class="card">
            <i class='bx bx-comment-dots'></i>
            <h3>Student Feedback</h3>
            <p>Give feedback on your courses.</p>
            <a href="feedback.jsp">Provide Feedback</a>
        </div>

    </div>

    <!-- Profile Modal -->
    <div id="profileModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeProfileModal()">&times;</span>
            <h2>Profile Information</h2>
            <img src="<%= photoData %>" alt="Profile Picture" class="profile-pic-modal">
            <p><strong>Name:</strong> <%= session.getAttribute("name") %></p>
            <p><strong>Email:</strong> <%= session.getAttribute("email") %></p>
            <p><strong>Role:</strong> <%= session.getAttribute("role") %></p>
            <p><strong>Student ID:</strong> <%= session.getAttribute("registrationId") %></p>
            <p><strong>Enrolled Courses:</strong><br>
                <%
                    String enrolledCourse = (String) session.getAttribute("enrolledCourse");
                    if (enrolledCourse != null && !enrolledCourse.isEmpty()) {
                        out.println(enrolledCourse);
                    } else {
                        out.println("No courses enrolled yet.");
                    }
                %>
            </p>
            <a href="edit-profile.jsp" class="edit-profile-btn">Edit Profile</a>
        </div>
    </div>
</div>

<script src="script/dark-mode.js"></script> <!-- Universal dark mode script -->
<script src="script/student-dashboard.js"></script> <!-- Page-specific script -->
</body>
</html>
