<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Fee Payment</title>
    <link rel="stylesheet" href="styles/studentFeePayment.css">
</head>
<body>
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
</body>
</html>