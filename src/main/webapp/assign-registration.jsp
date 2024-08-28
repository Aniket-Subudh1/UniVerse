<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Assign Registration IDs</title>
    <link rel="stylesheet" href="styles/assign-registration.css?v=1.0">
</head>
<body>
<div class="container">
    <h2>Assign Registration IDs and Enrolled Courses</h2>

    <!-- Form for Assigning Teacher Registration ID -->
    <div class="form-section">
        <h3>Assign Teacher Registration ID</h3>
        <form action="AssignTeacherRegServlet" method="post">
            <label for="teacherName">Teacher Name:</label>
            <input type="text" id="teacherName" name="teacherName" required>

            <label for="teacherRegId">Registration ID:</label>
            <input type="text" id="teacherRegId" name="teacherRegId" required>

            <button type="submit">Assign Registration ID</button>
        </form>
    </div>

    <!-- Form for Assigning Student Registration ID and Course -->
    <div class="form-section">
        <h3>Assign Student Registration ID and Course</h3>
        <form action="AssignStudentRegServlet" method="post">
            <label for="studentName">Student Name:</label>
            <input type="text" id="studentName" name="studentName" required>

            <label for="studentRegId">Registration ID:</label>
            <input type="text" id="studentRegId" name="studentRegId" required>

            <label for="enrolledCourse">Enrolled Course:</label>
            <input type="text" id="enrolledCourse" name="enrolledCourse" required>

            <button type="submit">Assign Registration ID and Course</button>
        </form>
    </div>
</div>
</body>
</html>
