<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Grade</title>
    <link rel="stylesheet" href="styles/addgrade.css">
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<div class="navbar-container">
    <!-- Navbar -->
    <nav class="navbar">
        <h2 class="logo">UniVerse</h2>
        <ul class="nav-links">
            <li><a href="teacher-dashboard.jsp">Home</a></li>
            <li><a href="Manage-Course.jsp">Courses</a></li>
            <li><a href="add_grade.jsp">Grades</a></li>
            <li><a href="teacher-attendance.jsp">Attendance</a></li>
        </ul>
    </nav>
</div>

<div class="content-container">
    <!-- Add Grade Card -->
    <div class="card">
        <h2>Add Grade</h2>
        <form action="GradeManagementServlet" method="post">
            <label for="studentId">Student ID:</label>
            <select id="studentId" name="studentId" required>
                <option value="">Loading...</option>
            </select>

            <br><br>

            <label for="courseId">Course ID:</label>
            <select id="courseId" name="courseId" required>
                <option value="">Select Course ID</option>
            </select>

            <br><br>

            <label for="grade">Grade:</label>
            <input type="text" id="grade" name="grade" required pattern="[A-Fa-f]">
            <small>(Enter a grade from A to F)</small>

            <br><br>

            <input type="submit" value="Add Grade">
        </form>
    </div>
</div>

<script>
    $(document).ready(function () {
        // Load all available student IDs on page load
        $.ajax({
            url: "GradeManagementServlet",
            type: "GET",
            dataType: "json",
            success: function (data) {
                var studentDropdown = $("#studentId");
                studentDropdown.empty().append("<option value=''>Select Student ID</option>");
                $.each(data, function (index, studentId) {
                    studentDropdown.append($("<option></option>").attr("value", studentId).text(studentId));
                });
            },
            error: function () {
                alert("Failed to load student IDs. Please try again.");
            }
        });


        $("#studentId").change(function () {
            var studentId = $(this).val();
            if (studentId) {
                $.ajax({
                    url: "GradeManagementServlet",
                    type: "GET",
                    data: { studentId: studentId },
                    dataType: "json",
                    success: function (data) {
                        var courseDropdown = $("#courseId");
                        courseDropdown.empty().append("<option value=''>Select Course ID</option>");
                        $.each(data, function (index, courseId) {
                            courseDropdown.append($("<option></option>").attr("value", courseId).text(courseId));
                        });
                    },
                    error: function () {
                        alert("Failed to fetch courses. Please try again.");
                    }
                });
            } else {
                $("#courseId").empty().append("<option value=''>Select Course ID</option>");
            }
        });
    });
</script>

<script src="script/dark-mode.js"></script>
</body>
</html>
