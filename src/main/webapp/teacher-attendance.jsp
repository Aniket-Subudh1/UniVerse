<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Teacher Attendance Management</title>
  <link rel="stylesheet" href="styles/teacher-attendance.css">
  <script src="script/teacher-attendance.js" defer></script>
</head>
<body>
<div class="container">
  <h1>Manage Attendance</h1>
  <form id="markAttendanceForm">
    <div class="card">
      <label for="courseSelect">Select Course:</label>
      <select id="courseSelect" required>
        <option value="">-- Select Course --</option>
      </select>

      <label for="attendanceDate">Select Date:</label>
      <input type="date" id="attendanceDate" required>

      <button type="submit">Mark Attendance</button>
    </div>

    <div class="attendance-cards">
      <p>Please select a course to view the timetable.</p>
    </div>

    <div id="studentsContainer">
      <!-- Dynamically populated student list -->
    </div>
  </form>
</div>
</body>
</html>
