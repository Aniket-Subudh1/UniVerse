<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update Attendance</title>
    <link rel="stylesheet" type="text/css" href="styles/updateAttendance.css">
</head>
<body>
<h2>Update Attendance</h2>

<form action="updateAttendance" method="post">
    <%--@declare id="studentid"--%><%--@declare id="subjectid"--%><%--@declare id="date"--%><%--@declare id="status"--%>
        <label for="studentId">Student ID:</label>
    <input type="text" name="studentId" required><br>

    <label for="subjectId">Course ID:</label>
    <input type="text" name="subjectId" required><br>

    <label for="date">Date:</label>
    <input type="date" name="date" required><br>

    <label for="status">Status:</label>
    <select name="status">
        <option value="Present">Present</option>
        <option value="Absent">Absent</option>
    </select><br><br>

    <input type="submit" value="Update Attendance">
</form>

<c:if test="${not empty message}">
    <p>${message}</p>
</c:if>
</body>
</html>
