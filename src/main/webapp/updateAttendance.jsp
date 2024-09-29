<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Attendance</title>
    <link rel="stylesheet" href="styles/updateAttendance.css?v=1.0">
</head>
<body>
<h2>Update Attendance</h2>
<form action="UpdateAttendanceServlet" method="post">
    <label for="regId">Student Registration ID:</label>
    <input type="text" id="regId" name="regId" required><br><br>

    <label for="subjectId">Subject ID:</label>
    <input type="text" id="subjectId" name="subjectId" required><br><br>

    <label for="date">Date:</label>
    <input type="date" id="date" name="date" required><br><br>

    <label for="status">Attendance Status (Present/Absent):</label>
    <input type="text" id="status" name="status" required><br><br>

    <input type="submit" value="Update Attendance">
</form>
</body>
</html>
