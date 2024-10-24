<%@ page import="com.cms.model.Attendance" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Attendance</title>
    <link rel="stylesheet" href="styles/ViewAttendance.css">
</head>
<body>
<h2>Attendance Record for Student</h2>

<h2>Enter Registration ID to View Attendance</h2>

<form action="ViewAttendanceServlet" method="post">
    Registration ID: <input type="text" name="regId" required>
    <input type="submit" value="View Attendance">
</form>

<table border="1">
    <tr>
        <th>Subject ID</th>
        <th>Date</th>
        <th>Status</th>
    </tr>
    <%
        List<Attendance> attendanceList = (List<Attendance>) request.getAttribute("attendanceList");
        if (attendanceList != null && !attendanceList.isEmpty()) {
            for (Attendance att : attendanceList) {
    %>
    <tr>
        <td><%= att.getSubjectId() %></td>
        <td><%= att.getDate() %></td>
        <td><%= att.getStatus() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="3">No attendance records found.</td>
    </tr>
    <%
        }
    %>
</table>

</body>
</html>