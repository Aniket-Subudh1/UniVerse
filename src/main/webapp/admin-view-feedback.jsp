<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>View Teacher Feedback</title>
  <link rel="stylesheet" href="styles/adminviewfeedback.css">
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

  <h1>View Teacher Feedback</h1>
  <label for="teacherDropdown">Select a Teacher:</label>
  <select id="teacherDropdown">
    <option value="">Select a teacher</option>
  </select>

  <h2>Feedback for <span id="selectedTeacherName">Selected Teacher</span></h2>
  <div class="feedback-list">
    <p>Select a teacher to view feedback.</p>
  </div>
</div>

<script src="script/admin-view-feedback.js"></script>
<script src="script/dark-mode.js"></script>
</body>
</html>