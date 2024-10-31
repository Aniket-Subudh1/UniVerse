<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fee Management</title>
    <style>
        /* Basic styling for clarity */
        label {
            display: inline-block;
            width: 150px;
            margin-top: 5px;
        }
        input, select {
            margin-top: 5px;
        }
        button {
            margin-top: 10px;
        }
        #feeDetails p {
            margin: 5px 0;
        }
    </style>
    <link rel="stylesheet" href="styles/managefees.css">
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
            <li>
                <div class="dark-mode-toggle" id="dark-mode-toggle">
                    <span class="sun"><i class='bx bx-sun'></i></span>
                    <span class="moon"><i class='bx bx-moon'></i></span>
                </div>
            </li>
        </ul>
    </nav>
<h2>Fee Management</h2>

<!-- Single Student Fee Update Section -->
<h3>Update Fees for Single Student</h3>
<label for="registrationId">Registration ID:</label>
<select id="registrationId"></select>
<button id="searchFeesButton">Search Fees</button>

<div id="feeDetails">
    <p>Academic Fee: <span id="academicFeeDisplay"></span></p>
    <p>Hostel Fee: <span id="hostelFeeDisplay"></span></p>
    <p>Exam Fee: <span id="examFeeDisplay"></span></p>
    <p>Total Fees: <span id="totalFeesDisplay"></span></p>
    <p>Amount Paid: <span id="amountPaidDisplay"></span></p>
    <p>Balance: <span id="balanceDisplay"></span></p>
</div>

<h4>Update Fee Amounts</h4>
<label for="academicFee">Academic Fee:</label>
<input type="text" id="academicFee"><br>
<label for="hostelFee">Hostel Fee:</label>
<input type="text" id="hostelFee"><br>
<label for="examFee">Exam Fee:</label>
<input type="text" id="examFee"><br>
<label for="amountPaid">Amount Paid:</label>
<input type="text" id="amountPaid"><br>
<button id="updateFeesButton">Update Fees</button>

<!-- Multiple Students Fee Update by Course or Registration IDs -->
<h3>Update Fees for Multiple Students (by Course or Registration IDs)</h3>
<label for="enrolledCourse">Enrolled Course:</label>
<select id="enrolledCourse"></select><br>
<label for="registrationIds">Registration IDs (comma-separated):</label>
<input type="text" id="registrationIds" placeholder="e.g., REG123, REG456"><br>

<h4>Set Fee Amounts</h4>
<label for="courseAcademicFee">Academic Fee:</label>
<input type="text" id="courseAcademicFee"><br>
<label for="courseHostelFee">Hostel Fee:</label>
<input type="text" id="courseHostelFee"><br>
<label for="courseExamFee">Exam Fee:</label>
<input type="text" id="courseExamFee"><br>
<label for="courseAmountPaid">Amount Paid:</label>
<input type="text" id="courseAmountPaid"><br>
<button id="updateCourseFeesButton">Update Fees</button>

<!-- Include the JavaScript file -->
<script src="script/manageFees.js"></script>
    <script src="script/dark-mode.js"></script>
    </div>
</body>
</html>