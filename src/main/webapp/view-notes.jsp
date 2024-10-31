<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Available Course Notes</title>
  <link rel="stylesheet" href="styles/notes1..css">
</head>
<body>
<div class="container">

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

  <h2>Available Course Notes</h2>
  <div id="notesContainer" class="notes-container">
    <p>Loading notes...</p>
  </div>
</div>
<script src="script/view-notes.js"></script>
<script src="script/dark-mode.js"></script>
</body>
</html>