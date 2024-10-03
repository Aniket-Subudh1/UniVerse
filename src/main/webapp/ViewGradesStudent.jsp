<%@ page import="java.util.List" %>
<%@ page import="com.cms.model.ViewGrades" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Grades - Student</title>

    <link rel="stylesheet" href="styles/ViewGrades.css">
</head>
<body>

<h1>Enter Your Registration ID to View Grades</h1>

<form action="viewGradesStudent" method="post">
    <label for="studentId">Student Registration ID:</label>
    <input type="text" id="studentId" name="studentId" required>
    <button type="submit">View Grades</button>
</form>

<h2>Your Grades:</h2>

<table border="1">
    <thead>
    <tr>
        <th>Subject Code</th>
        <th>Grade</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<ViewGrades> gradesList = (List<ViewGrades>) request.getAttribute("studentGrades");
        if (gradesList != null && !gradesList.isEmpty()) {
            for (ViewGrades grade : gradesList) {
    %>
    <tr>
        <td><%= grade.getSubjectCode() %></td>
        <td><%= grade.getGrade() %></td>
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
        <td colspan="2">Please enter your registration ID to view your grades.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>
