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
</body>
</html>
