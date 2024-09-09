<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.cms.model.Course" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registered Courses</title>
    <link rel="stylesheet" href="styles/student-dashboard.css?v=1.0">
</head>
<body>
<h2>My Registered Courses</h2>

<table border="1" cellpadding="10" cellspacing="0">
    <tr>
        <th>Course ID</th>
        <th>Course Name</th>
    </tr>
    <%
        List<Course> courseList = (List<Course>) request.getAttribute("courseList");
        if (courseList != null && !courseList.isEmpty()) {
            for (Course course : courseList) {
    %>
    <tr>
        <td><%= course.getCourseId() %></td>
        <td><%= course.getCourseName() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="2">No courses registered yet.</td>
    </tr>
    <%
        }
    %>
</table>

<a href="student-dashboard.jsp">Back to Dashboard</a>
</body>
</html>
