<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Admin Attendance Management</title>
  <link rel="stylesheet" href="styles/admin-attendance.css">
  <script src="script/admin-attendance.js" defer></script>
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
    </ul>
  </nav>


  <h1>Manage Attendance</h1>
  <form id="markAttendanceForm">
    <div class="form-group">
      <label for="teacherSelect">Select Teacher:</label>
      <select id="teacherSelect" required>
        <option value="">-- Select Teacher --</option>
        <!-- Dynamically populated with teacher options -->
      </select>
    </div>

    <div class="form-group">
      <label for="courseSelect">Select Course:</label>
      <select id="courseSelect" required>
        <option value="">-- Select Course --</option>
        <!-- Dynamically populated with course options -->
      </select>
    </div>

    <div class="form-group">
      <label for="attendanceDate">Select Date:</label>
      <input type="date" id="attendanceDate" required>
    </div>

    <div id="timetableContainer">
      <p>Please select a teacher and course to view the timetable.</p>
      <!-- Dynamically populated timetable with days and time slots -->
    </div>

    <div id="studentsContainer">
      <!-- Dynamically populated student list for selected time slot -->
    </div>

    <div class="form-group">
      <button type="submit">Mark Attendance</button>
    </div>
  </form>
</div>

<script src="script/dark-mode.js"></script>

</body>
</html>
