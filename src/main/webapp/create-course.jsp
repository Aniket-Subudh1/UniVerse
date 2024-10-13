<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Create and Manage Courses</title>
  <link rel="stylesheet" href="styles/manage-course.css?v=1.0">
  <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
<div class="container">
  <nav class="navbar">
    <h2 class="logo">UniVerse Admin</h2>
    <ul class="nav-links">
      <li><a href="admin-dashboard.jsp">Home</a></li>
      <li><a href="manage-timetable.jsp">Timetable</a></li>
      <li><a href="manage-courses.jsp">Courses</a></li>
      <li><a href="manage-grades.jsp">Grades</a></li>
    </ul>
    <div class="profile-info">
      <p class="profile-name"><%= session.getAttribute("name") %></p>
      <p class="profile-role"><%= session.getAttribute("role") %></p>
    </div>
    <div class="dark-mode-toggle" id="dark-mode-toggle">
      <span class="sun"><i class='bx bx-sun'></i></span>
      <span class="moon"><i class='bx bx-moon'></i></span>
    </div>
  </nav>

  <div class="dashboard-sections">
    <!-- Create Course Card -->
    <div class="card">
      <h3>Create a New Course</h3>
      <form id="createCourseForm">
        <label for="courseId">Course ID:</label>
        <input type="text" id="courseId" name="courseId" required><br>

        <label for="courseName">Course Name:</label>
        <input type="text" id="courseName" name="courseName" required><br>

        <label for="courseDescription">Course Description:</label>
        <textarea id="courseDescription" name="courseDescription" required></textarea><br>

        <button type="submit" class="btn">Create Course</button>
      </form>
    </div>

    <!-- Manage Courses Card -->
    <div class="card">
      <h3>Manage Courses</h3>
      <!-- Search Bar -->
      <input type="text" id="searchCourse" placeholder="Search course by name..." onkeyup="filterCourses()">
      <button class="btn" onclick="loadCourses()">Load Courses</button>

      <!-- Display Courses in a Table -->
      <table id="courseTable">
        <thead>
        <tr>
          <th>Course ID</th>
          <th>Course Name</th>
          <th>Course Description</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody id="courseTableBody">
        <tr>
          <td colspan="4">No courses found</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>

  <!-- Modal for Editing Course -->
  <div id="editModal" class="modal">
    <div class="modal-content">
      <h2>Edit Course</h2>
      <form id="editCourseForm">
        <input type="hidden" id="editCourseId" name="courseId">
        <label for="editCourseName">Course Name:</label>
        <input type="text" id="editCourseName" name="courseName" required><br>
        <label for="editCourseDescription">Course Description:</label>
        <textarea id="editCourseDescription" name="courseDescription" required></textarea><br>
        <input type="hidden" name="action" value="update">
        <button type="submit" class="btn">Update Course</button>
      </form>
    </div>
  </div>

  <!-- Success Modal -->
  <div id="successModal" class="modal">
    <div class="modal-content">
      <h2>Success</h2>
      <p id="modalMessage">Your course has been successfully created.</p> <!-- Correct ID placement -->
      <button class="btn" id="closeModalButton">OK</button>
    </div>
  </div>

  <!-- Error Modal -->
  <div id="errorModal" class="modal">
    <div class="modal-content">
      <h2>Error</h2>
      <p id="errorMessage">An error occurred. Please try again.</p> <!-- Added error message -->
      <button class="btn" id="closeErrorButton">OK</button>
    </div>
  </div>

</div>

<script src="script/dark-mode.js"></script>
<script src="script/course-management.js"></script>
</body>
</html>
