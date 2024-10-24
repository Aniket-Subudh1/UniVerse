<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Submit Course for Approval</title>
    <link rel="stylesheet" href="styles/submit-course.css?v=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
<div class="container">
    <nav class="navbar">
        <h2 class="logo">UniVerse</h2>
        <ul class="nav-links">
            <li><a href="teacher-dashboard.jsp">Home</a></li>
            <li><a href="Manage-Course.jsp">Courses</a></li>
            <li><a href="ViewGrades.jsp">Grades</a></li>
            <li><a href="attendance.jsp">Attendance</a></li>
        </ul>

    </nav>

    <div class="dashboard-sections">
        <!-- Submit Course Form Section -->
        <div class="card">
            <h3>Submit a New Course for Approval</h3>
            <form id="submitCourseForm">
                <label for="courseId">Course ID:</label>
                <input type="text" id="courseId" name="courseId" required><br>

                <label for="courseName">Course Name:</label>
                <input type="text" id="courseName" name="courseName" required><br>

                <label for="courseDescription">Course Description:</label>
                <textarea id="courseDescription" name="courseDescription" required></textarea><br>

                <button type="submit" class="btn">Submit Course</button>
            </form>
        </div>
    </div>

    <!-- Success Modal -->
    <div id="successModal" class="modal">
        <div class="modal-content">
            <h2>Success</h2>
            <p id="modalMessage">Course submitted successfully for approval.</p>
            <button class="btn" id="closeModalButton">OK</button>
        </div>
    </div>

    <!-- Error Modal -->
    <div id="errorModal" class="modal">
        <div class="modal-content">
            <h2>Error</h2>
            <p id="errorMessage">An error occurred. Please try again.</p>
            <button class="btn" id="closeErrorButton">OK</button>
        </div>
    </div>

</div>


<script>
    document.addEventListener('DOMContentLoaded', function () {
        const submitCourseForm = document.getElementById('submitCourseForm');
        const successModal = document.getElementById('successModal');
        const errorModal = document.getElementById('errorModal');
        const modalMessage = document.getElementById('modalMessage');
        const errorMessage = document.getElementById('errorMessage');
        const closeModalButton = document.getElementById('closeModalButton');
        const closeErrorButton = document.getElementById('closeErrorButton');

        // Handle form submission for submitting a course
        submitCourseForm.addEventListener('submit', function (event) {
            event.preventDefault();

            const formData = new FormData(submitCourseForm);
            const courseId = formData.get('courseId');
            const courseName = formData.get('courseName');
            const courseDescription = formData.get('courseDescription');

            fetch('submitCourse', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    courseId: courseId,
                    courseName: courseName,
                    courseDescription: courseDescription
                })
            })
                .then(response => response.json())
                .then(data => {
                    if (data.status === 'success') {
                        modalMessage.innerHTML = data.message;
                        successModal.style.display = 'flex'; // Show success modal
                    } else {
                        errorMessage.innerHTML = data.message;
                        errorModal.style.display = 'flex'; // Show error modal
                    }
                })
                .catch(error => {
                    console.error('Error submitting course:', error);
                    errorMessage.innerHTML = 'An error occurred. Please try again.';
                    errorModal.style.display = 'flex'; // Show error modal
                });
        });

        // Close modals when the "OK" buttons are clicked
        closeModalButton.addEventListener('click', function () {
            successModal.style.display = 'none';
        });

        closeErrorButton.addEventListener('click', function () {
            errorModal.style.display = 'none';
        });
    });
</script>
<script src="script/dark-mode.js"></script>
</body>
</html>
