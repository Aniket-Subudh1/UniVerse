<%@ page import="java.util.List" %>
<%@ page import="com.cms.model.ViewAssignedSub" %>
<!DOCTYPE html>
<html>
<head>
    <title>Teacher Dashboard</title>

    <link rel="stylesheet" href="styles/ViewAssignSub.css">
</head>
<body>

<h1>Enter Your Teacher ID to View Assigned Courses</h1>

<form action="teacherDashboard" method="post">
    <label for="teacherId">Teacher ID:</label>
    <input type="text" id="teacherId" name="teacherId" required>
    <button type="submit">View Courses</button>
</form>

<h2>Your Assigned Courses:</h2>

<table border="1">
    <thead>
    <tr>
        <th>Course ID</th>
        <th>Course Name</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<ViewAssignedSub> courses = (List<ViewAssignedSub>) request.getAttribute("assignedCourses");
        if (courses != null && !courses.isEmpty()) {
            for (ViewAssignedSub course : courses) {
    %>
    <tr>
        <td><%= course.getCourseId() %></td>
        <td><%= course.getCourseName() %></td>
    </tr>
    <%
        }
    } else if (request.getAttribute("errorMessage") != null) {
    %>
    <tr>
        <td colspan="2"><%= request.getAttribute("errorMessage") %></td>
    </tr>
    <%
    } else {
    %>
    <tr>
        <td colspan="2">Please enter a valid Teacher ID to view courses.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>
