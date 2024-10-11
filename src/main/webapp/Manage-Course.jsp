<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Manage Courses</title>
    <link rel="stylesheet" href="styles/Manage-Course.css">
</head>

<body>
<h1>Manage Courses</h1>

<form action="CourseServlet" method="POST">
    <!-- Hidden field to store the teacher's ID -->
    <input type="hidden" name="teacherId" value="<%= request.getAttribute("teacherId") %>">

    <table border="1">
        <tr>
            <th>Course ID</th>
            <th>Course Name</th>
            <th>Description</th>
        </tr>
        <tr>
            <th> </th>
            <th> </th>
            <th> </th>
        </tr>
        <tr>
            <th> </th>
            <th> </th>
            <th> </th>
        </tr>
        <tr>
            <th> </th>
            <th> </th>
            <th> </th>
        </tr>
    </table>

    <br>
    <!-- Button to add a new course -->
    <input type="submit" name="action" value="Add New Course">
</form>
</body>
</html>
