<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Course Registration</title>
    <link rel="stylesheet" href="styles/course-registration.css">
    <script>
        function registerCourse(courseId, courseName) {
            // Set the course ID and course name in the hidden form
            document.getElementById("courseId").value = courseId;
            document.getElementById("courseName").value = courseName;

            // Submit the form
            document.getElementById("courseForm").submit();
        }
    </script>
</head>
<body>
<div class="container">
    <h2>Course Registration</h2>
    <div class="course-grid">
        <div class="course-card">
            <h3>AdvanceJava</h3>
            <p>Course ID: 101</p>
            <button type="button" onclick="registerCourse(101, 'Course 1')">Register</button>
        </div>
        <div class="course-card">
            <h3>Spring Boot</h3>
            <p>Course ID: 102</p>
            <button type="button" onclick="registerCourse(102, 'Course 2')">Register</button>
        </div>
        <div class="course-card">
            <h3>Angular</h3>
            <p>Course ID: 103</p>
            <button type="button" onclick="registerCourse(103, 'Course 3')">Register</button>
        </div>
        <div class="course-card">
            <h3>Product Development</h3>
            <p>Course ID: 104</p>
            <button type="button" onclick="registerCourse(104, 'Course 4')">Register</button>
        </div>
        <div class="course-card">
            <h3>AndroidApp Development</h3>
            <p>Course ID: 105</p>
            <button type="button" onclick="registerCourse(105, 'Course 5')">Register</button>
        </div>
        <div class="course-card">
            <h3>Job Readiness</h3>
            <p>Course ID: 106</p>
            <button type="button" onclick="registerCourse(106, 'Course 6')">Register</button>
        </div>
        <div class="course-card">
            <h3>Data Structure</h3>
            <p>Course ID: 107</p>
            <button type="button" onclick="registerCourse(107, 'Course 7')">Register</button>
        </div>
        <div class="course-card">
            <h3>Machine Learning</h3>
            <p>Course ID: 108</p>
            <button type="button" onclick="registerCourse(108, 'Course 8')">Register</button>
        </div>
        <div class="course-card">
            <h3>Cloud Computing</h3>
            <p>Course ID: 109</p>
            <button type="button" onclick="registerCourse(109, 'Course 9')">Register</button>
        </div>
    </div>
</div>

<!-- Hidden form to submit course data -->
<form id="courseForm" action="registerCourse" method="post">
    <input type="hidden" id="courseId" name="courseId">
    <input type="hidden" id="courseName" name="courseName">
</form>

</body>
</html>
