<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Course Registration</title>
    <link rel="stylesheet" href="styles/course-registration.css">
    <script>
        function showPopup(message) {
            alert(message);
        }

        window.onload = function() {
            // Retrieve the registration success attribute and show the appropriate message
            var registrationSuccess = '<%= request.getAttribute("registrationSuccess") %>';
            if (registrationSuccess === 'true') {
                showPopup('Course registered successfully!');
            } else if (registrationSuccess === 'false') {
                showPopup('Failed to register the course.');
            }
        };
    </script>
</head>
<body>
<div class="container">
    <h2>Course Registration</h2>
    <div class="course-grid">
        <!-- Course Card: AdvanceJava -->
        <div class="course-card">
            <h3>AdvanceJava</h3>
            <p>Course ID: 101</p>
            <form action="registerCourse" method="post">
                <input type="hidden" name="courseId" value="101">
                <input type="hidden" name="courseName" value="AdvanceJava">
                <button type="submit">Register</button>
            </form>
        </div>

        <!-- Course Card: Spring Boot -->
        <div class="course-card">
            <h3>Spring Boot</h3>
            <p>Course ID: 102</p>
            <form action="registerCourse" method="post">
                <input type="hidden" name="courseId" value="102">
                <input type="hidden" name="courseName" value="Spring Boot">
                <button type="submit">Register</button>
            </form>
        </div>

        <!-- Course Card: Angular -->
        <div class="course-card">
            <h3>Angular</h3>
            <p>Course ID: 103</p>
            <form action="registerCourse" method="post">
                <input type="hidden" name="courseId" value="103">
                <input type="hidden" name="courseName" value="Angular">
                <button type="submit">Register</button>
            </form>
        </div>

        <!-- Course Card: Product Development -->
        <div class="course-card">
            <h3>Product Development</h3>
            <p>Course ID: 104</p>
            <form action="registerCourse" method="post">
                <input type="hidden" name="courseId" value="104">
                <input type="hidden" name="courseName" value="Product Development">
                <button type="submit">Register</button>
            </form>
        </div>

        <!-- Add more courses similarly -->
        <!-- Course Card: AndroidApp Development -->
        <div class="course-card">
            <h3>AndroidApp Development</h3>
            <p>Course ID: 105</p>
            <form action="registerCourse" method="post">
                <input type="hidden" name="courseId" value="105">
                <input type="hidden" name="courseName" value="AndroidApp Development">
                <button type="submit">Register</button>
            </form>
        </div>

        <!-- Course Card: Job Readiness -->
        <div class="course-card">
            <h3>Job Readiness</h3>
            <p>Course ID: 106</p>
            <form action="registerCourse" method="post">
                <input type="hidden" name="courseId" value="106">
                <input type="hidden" name="courseName" value="Job Readiness">
                <button type="submit">Register</button>
            </form>
        </div>

        <!-- Add other courses here -->
        <div class="course-card">
            <h3>Data Structure</h3>
            <p>Course ID: 107</p>
            <form action="registerCourse" method="post">
                <input type="hidden" name="courseId" value="107">
                <input type="hidden" name="courseName" value="Data Structure">
                <button type="submit">Register</button>
            </form>
        </div>

        <div class="course-card">
            <h3>Machine Learning</h3>
            <p>Course ID: 108</p>
            <form action="registerCourse" method="post">
                <input type="hidden" name="courseId" value="108">
                <input type="hidden" name="courseName" value="Machine Learning">
                <button type="submit">Register</button>
            </form>
        </div>

        <div class="course-card">
            <h3>Cloud Computing</h3>
            <p>Course ID: 109</p>
            <form action="registerCourse" method="post">
                <input type="hidden" name="courseId" value="109">
                <input type="hidden" name="courseName" value="Cloud Cmputing">
                <button type="submit">Register</button>
            </form>
        </div>

    </div>
</div>
</body>
</html>
