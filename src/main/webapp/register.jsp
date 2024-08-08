<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register Form</title>
    <link rel="stylesheet" href="styles/register.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
<div class="wrapper">
    <form id="registrationForm" action="register" method="post" enctype="multipart/form-data">
        <h1>Register</h1>
        <div class="input-box">
            <input type="text" id="name" name="name" placeholder="Name" required>
            <i class='bx bxs-user'></i>
        </div>
        <div class="input-box">
            <input type="email" id="email" name="email" placeholder="Email" required>
            <i class='bx bxs-envelope'></i>
        </div>
        <div class="input-box">
            <input type="password" id="password" name="password" placeholder="Password" required>
            <i class='bx bxs-lock-alt'></i>
            <i class='bx bx-show' id="togglePassword"></i>
        </div>
        <div class="input-box">
            <input type="password" id="confirmPassword" placeholder="Confirm Password" required>
            <i class='bx bxs-lock-alt'></i>
        </div>
        <div class="input-box">
            <input type="date" id="dob" name="dob" placeholder="Date of Birth" required>
            <i class='bx bxs-calendar'></i>
        </div>
        <div class="input-box-file">
            <input type="file" id="photo" name="photo" required>
            <i class='bx bxs-file'></i>
        </div>
        <button type="submit" class="btn">Register</button>
        <div id="message"></div>
        <div class="register-link">
            <p>Already have an account? <a href="index.jsp">Login</a></p>
        </div>
    </form>
</div>

<!-- Success Modal -->
<div id="successModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeSuccessModal()">&times;</span>
        <h2>Registration Successful</h2>
        <p>You have successfully registered. Please login to continue.</p>
    </div>
</div>

<script src="script/register.js"></script>
</body>
</html>