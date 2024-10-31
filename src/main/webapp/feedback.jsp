<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Submit Feedback</title>
    <link rel="stylesheet" href="styles/feedback1.css"> <!-- Include your CSS file here -->
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

    <h2>Submit Feedback</h2>

    <form id="feedbackForm">
        <label for="courseTeacherSelect">Course and Teacher:</label>
        <select id="courseTeacherSelect" name="courseTeacherSelect" required>
            <!-- Options will be populated via JavaScript -->
        </select>

        <label for="rating">Rating (1-5):</label>
        <input type="number" id="rating" name="rating" min="1" max="5" required>

        <label for="comments">Comments:</label>
        <textarea id="comments" name="comments" placeholder="Optional"></textarea>

        <button type="submit">Submit Feedback</button>
    </form>

    <p id="feedbackMessage"></p> <!-- To display success or error messages -->
</div>

<script src="script/feedback.js"></script> <!-- Link to the external JS file -->
<script src="script/dark-mode.js"></script>
</body>
</html>