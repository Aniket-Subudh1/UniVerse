<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Student Attendance</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/student-attendance.css">
  <script src="${pageContext.request.contextPath}/script/student-attendance.js" defer></script>
</head>
<body>
<div class="container">
  <!-- Navbar copied from view-courses.jsp -->
  <nav class="navbar">
    <h2 class="logo">UniVerse</h2>
    <ul class="nav-links">
      <li><a href="student-dashboard.jsp">Home</a></li>
      <li><a href="view-courses.jsp">Courses</a></li>
      <li><a href="grades.jsp">Grades</a></li>
      <li><a href="student-attendance.jsp">Attendance</a></li>
      <li>
        <div class="dark-mode-toggle" id="dark-mode-toggle">
          <span class="sun"><i class='bx bx-sun'></i></span>
          <span class="moon"><i class='bx bx-moon'></i></span>
        </div>
      </li>
    </ul>
  </nav>

  <h1>Attendance Records</h1>

  <!-- Date Range Selection -->
  <div class="date-range">
    <label for="startDate">From:</label>
    <input type="date" id="startDate" name="startDate">

    <label for="endDate">To:</label>
    <input type="date" id="endDate" name="endDate">

    <button id="filterButton">Filter</button>
  </div>

  <div id="attendanceRecordsContainer" class="attendance-cards">
    <!-- Attendance records will be loaded here by JavaScript -->
    <p>Loading your attendance records...</p>
  </div>
</div>

<script src="script/dark-mode.js"></script>
</body>
</html>
