<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Teacher Attendance Management</title>
  <link rel="stylesheet" href="styles/teacher-attendance.css">
  <link rel="stylesheet" href="styles/submit-course.css?v=1.0"> <!-- Optional: Link the CSS if it affects navbar styling -->
  <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'> <!-- Optional: Add boxicons if needed -->
  <script src="script/teacher-attendance.js" defer></script>
</head>
<body>
<div class="container">

  <!-- Navbar copied from the "Submit Course for Approval" page -->
  <nav class="navbar">
    <h2 class="logo">UniVerse</h2>
    <ul class="nav-links">
      <li><a href="teacher-dashboard.jsp">Home</a></li>
      <li><a href="Manage-Course.jsp">Courses</a></li>
      <li><a href="ViewGrades.jsp">Grades</a></li>
      <li><a href="teacher-attendance.jsp">Attendance</a></li>
    </ul>
  </nav>

  <h1>Manage Attendance</h1>
  <form id="markAttendanceForm">
    <div class="card">
      <div class="attendance-options">
        <label for="courseSelect">Select Course:</label>
        <select id="courseSelect" required>
          <option value="">-- Select Course --</option>
        </select>
      </div>

      <div class="attendance-options">
        <label for="attendanceDate">Select Date:</label>
        <input type="date" id="attendanceDate" required>
      </div>

      <div class="attendance-actions">
        <button type="submit">Mark Attendance</button>
      </div>

    </div>

    <div class="attendance-cards">
      <p>Please select a course to view the Attendance.</p>
    </div>

    <div id="studentsContainer">
      <!-- Dynamically populated student list -->
    </div>
  </form>
</div>

<script src="script/dark-mode.js"></script>

</body>
</html>
