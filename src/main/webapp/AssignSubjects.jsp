<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Assign Subjects</title>
    <link rel="stylesheet" href="styles/AssignSubject.css?v=1.0">
</head>
<body>
<div class="container">
    <h2>Assign Courses to Teachers</h2>
    <form action="AssignSubjectsServlet" method="post">
        <label for="teacherId">Teacher ID:</label>
        <input type="text" id="teacherId" name="teacherId" required>

        <label for="courseId">Course ID:</label>
        <input type="text" id="courseId" name="courseId" required>

        <label for="courseName">Course Name:</label>
        <input type="text" id="courseName" name="courseName" required>

        <button type="submit">Assign Course</button>
    </form>
</div>
</body>
</html>
