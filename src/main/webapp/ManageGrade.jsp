<%@ page import="java.util.List" %>
<%@ page import="com.cms.model.ViewGrades" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Grades</title>

    <link rel="stylesheet" href="styles/managegrades.css">
</head>
<body>

<h1>Grade Management System</h1>

<h2>Assign Grades</h2>

<form action="assignGrades" method="post">
    <label for="studentId">Student Registration ID:</label>
    <input type="text" id="studentId" name="studentId" placeholder="Enter student ID" required>

    <label for="subjectCode">Subject Code:</label>
    <input type="text" id="subjectCode" name="subjectCode" placeholder="Enter subject code" required>

    <label for="grade">Assign Grade:</label>
    <input type="text" id="grade" name="grade" placeholder="Enter grade" required>

    <button type="submit">Submit Grade</button>
</form>

<%-- Display any error messages if they exist --%>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
<p style="color: red;"><%= errorMessage %></p>
<%
    }
%>

<h2>Assigned Grades:</h2>

<table border="1" cellpadding="10" cellspacing="0">
    <thead>
    <tr>
        <th>Student Registration ID</th>
        <th>Subject Code</th>
        <th>Grade</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<ViewGrades> gradesList = (List<ViewGrades>) request.getAttribute("assignedGrades");
        if (gradesList != null && !gradesList.isEmpty()) {
            for (ViewGrades grade : gradesList) {
    %>
    <tr>
        <td><%= grade.getStudentId() %></td>
        <td><%= grade.getSubjectCode() %></td>
        <td><%= grade.getGrade() %></td>
    </tr>
    <%
        }
    } else if (request.getAttribute("errorMessage") != null) {
    %>
    <tr>
        <td colspan="3"><%= request.getAttribute("errorMessage") %></td>
    </tr>
    <%
    } else {
    %>
    <tr>
        <td colspan="3">No grades assigned yet. Please enter details to assign a grade.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>
