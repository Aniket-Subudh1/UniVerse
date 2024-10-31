<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Fee Payment</title>
    <link rel="stylesheet" href="styles/feepayment.css">
</head>
<body>
<div class="container">
    <!-- Navbar copied from view-courses.jsp -->
    <nav class="navbar">
        <h2 class="logo">UniVerse</h2>
        <ul class="nav-links">
            <li><a href="student-dashboard.jsp">Home</a></li>
            <li><a href="view-courses.jsp">Courses</a></li>
            <li><a href="grades.jsp">Grades</a></li>
            <li><a href="student-attendance.jsp">Attendance</a></li>
        </ul>
    </nav>

<h2>Student Fee Payment</h2>

<div id="messages"></div>

<div id="paymentSection">
    <!-- Fee Details and Payment Button -->
    <div id="feeDetails" style="display: none;">
        <p>Total Academic Fee (For Entire Course Duration): INR <span id="academicFee"></span></p>
        <p>Hostel Fee: INR <span id="hostelFee"></span></p>
        <p>Exam Fee: INR <span id="examFee"></span></p>
        <p>Total Fees: INR <span id="totalFees"></span></p>
        <p>Amount Paid: INR <span id="amountPaid"></span></p>
        <p>Remaining Balance: INR <span id="balance"></span></p>
        <button id="payButton">Pay Now</button>
    </div>
</div>

<!-- Include Razorpay checkout script -->
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
<!-- Include your custom JavaScript file -->
<script src="script/studentFeePayment.js"></script>
</div>
</body>
</html>