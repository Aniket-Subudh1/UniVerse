<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create and Manage Routine</title>
    <link rel="stylesheet" href="styles/manage-routine.css?v=1.1">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
<div class="container">
    <nav class="navbar">
        <h2 class="logo">UniVerse</h2>
        <ul class="nav-links">
            <li><a href="admin-dashboard.jsp">Home</a></li>
            <li><a href="admin-attendance.jsp">Attendance</a></li>
            <li><a href="manage-routine.jsp">Timetable</a></li>
            <li><a href="create-course.jsp">Courses</a></li>
        </ul>
    </nav>

    <div class="dashboard-sections">
        <!-- Create Routine Section -->
        <div class="card create-routine-card">
            <h3>Create a New Routine</h3>
            <form id="createRoutineForm">
                <label for="enrolledCourse">Enrolled Course:</label>
                <input type="text" id="enrolledCourse" name="enrolledCourse" required><br>

                <label for="dayOfWeek">Day of Week:</label>
                <select id="dayOfWeek" name="dayOfWeek" required>
                    <option value="">Select Day</option>
                    <option value="Monday">Monday</option>
                    <option value="Tuesday">Tuesday</option>
                    <option value="Wednesday">Wednesday</option>
                    <option value="Thursday">Thursday</option>
                    <option value="Friday">Friday</option>
                    <option value="Saturday">Saturday</option>
                </select><br>

                <label for="timeStart">Start Time:</label>
                <input type="time" id="timeStart" name="timeStart" required><br>

                <label for="timeEnd">End Time:</label>
                <input type="time" id="timeEnd" name="timeEnd" required><br>

                <label for="courseId">Course:</label>
                <select id="courseId" name="courseId" required>
                    <option value="">-- Select Course --</option>
                </select><br>

                <label for="teacherId">Teacher:</label>
                <select id="teacherId" name="teacherId" required>
                    <option value="">-- Select Teacher --</option>
                </select><br>

                <button type="submit" class="btn">Create Routine</button>
            </form>
        </div>

        <!-- Routine Preview Section -->
        <div class="card routine-preview-card">
            <h3>Current Routines</h3>

            <!-- Filters for Subject and Enrolled Course -->
            <div class="filter-container">
                <label for="filterCourse">Filter by Course:</label>
                <select id="filterCourse" name="filterCourse">
                    <option value="">-- Select Course --</option>
                    <!-- This will be dynamically filled with courses -->
                </select>

                <button class="btn load-btn" onclick="
                ">Filter Routine</button>
            </div>

            <!-- Table for displaying routine -->
            <div class="routine-table-container">
                <table id="routineTable">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Enrolled Course</th>
                        <th>Day</th>
                        <th>Time Start</th>
                        <th>Time End</th>
                        <th>Course</th>
                        <th>Teacher</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody id="routineTableBody">
                    <tr>
                        <td colspan="8">No routines found</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
        <!-- Edit Modal -->
    <div id="editModal" class="modal">
        <div class="modal-content">
            <h2>Edit Routine</h2>
            <form id="editRoutineForm">
                <input type="hidden" id="editRoutineId" name="routineId">
                <label for="editEnrolledCourse">Enrolled Course:</label>
                <input type="text" id="editEnrolledCourse" name="enrolledCourse" required><br>

                <label for="editDayOfWeek">Day of Week:</label>
                <select id="editDayOfWeek" name="dayOfWeek" required>
                    <option value="">Select Day</option>
                    <option value="Monday">Monday</option>
                    <option value="Tuesday">Tuesday</option>
                    <option value="Wednesday">Wednesday</option>
                    <option value="Thursday">Thursday</option>
                    <option value="Friday">Friday</option>
                    <option value="Saturday">Saturday</option>
                </select><br>

                <label for="editTimeStart">Start Time:</label>
                <input type="time" id="editTimeStart" name="timeStart" required><br>

                <label for="editTimeEnd">End Time:</label>
                <input type="time" id="editTimeEnd" name="timeEnd" required><br>

                <label for="editCourseId">Course:</label>
                <select id="editCourseId" name="courseId" required></select><br>

                <label for="editTeacherId">Teacher:</label>
                <select id="editTeacherId" name="teacherId" required></select><br>

                <button type="submit" class="btn">Update Routine</button>
            </form>
        </div>
    </div>

    <!-- Success Modal -->
    <div id="successModal" class="modal">
        <div class="modal-content">
            <h2>Success</h2>
            <p id="modalMessage">Operation completed successfully.</p>
            <button class="btn" id="closeModalButton">OK</button>
        </div>
    </div>
</div>

<script src="script/dark-mode.js"></script>
<script src="script/manage-routine.js"></script>
</body>
</html>
