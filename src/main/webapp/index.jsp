<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*, jakarta.servlet.*, jakarta.servlet.http.*, jakarta.servlet.annotation.MultipartConfig"%>
<%@ page import="java.nio.file.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login and Register Form</title>
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link rel="stylesheet" href="styles/login.css">
</head>
<body>

<div class="container" id="container">
    <div class="form-container sign-up">
        <form id="registrationForm" action="register.jsp" method="post" enctype="multipart/form-data">
            <h1>Sign Up</h1>
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
            <div class="input-box">
                <select id="role" name="role" required>
                    <option value="role" disabled selected>Select Role</option>
                    <option value="student" class="st">Student</option>
                    <option value="teacher" class="st">Teacher</option>
                </select>
                <i class='bx bxs-user'></i>
            </div>
            <div class="input-box-file">
                <input type="file" id="photo" name="photo" >
            </div>
            <button type="submit" class="btn">Sign Up</button>
            <div id="message"></div>
        </form>
    </div>
    <div class="form-container sign-in">
        <form action="login" method="post">
            <h1>Sign In</h1>
            <div class="input-box">
                <input type="text" name="email" placeholder="Email" required>
                <i class='bx bxs-user'></i>
            </div>
            <div class="input-box">
                <input type="password" name="password" placeholder="Password" id="passwordLogin" required>
                <i class='bx bxs-lock-alt'></i>
                <i class='bx bx-show' id="togglePasswordLogin"></i>
            </div>
            <div class="remember-forgot">
                <label><input type="checkbox">Remember Me</label>

                <a href="forgot-password.jsp">Forgot Password</a>
            </div>
            <button type="submit" class="btn">Sign In</button>
        </form>
    </div>
    <div class="toggle-container">
        <div class="toggle">
            <div class="toggle-panel toggle-left">
                <h1>Welcome Back!</h1>
                <p>Enter your personal details to use all site features</p>
                <button class="hidden" id="login">Sign In</button>
            </div>
            <div class="toggle-panel toggle-right">
                <h1>Hello, Friend!</h1>
                <p>Register with your personal details to use all site features</p>
                <button class="hidden" id="register">Sign Up</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal for Registration Success -->
<div id="successModal" class="modal">
    <div class="modal-content">
        <h2>Registration Successful</h2>
        <p>You have successfully registered. Please login to continue.</p>
        <button id="closeModalBtn" class="btn">OK</button>
    </div>
</div>

<!-- Add JSP logic for handling form submission -->
<%
    if(request.getMethod().equalsIgnoreCase("POST")) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String dob = request.getParameter("dob");
        String role = request.getParameter("role");

        Part photoPart = request.getPart("photo");
        String photoFileName = null;
        String uploadPath = application.getRealPath("") + File.separator + "uploads";

        if (photoPart != null) {
            photoFileName = Paths.get(photoPart.getSubmittedFileName()).getFileName().toString();
        }

        // Check if no photo was uploaded and set the default photo name
        if (photoFileName == null || photoFileName.isEmpty()) {
            photoFileName = "pRO.jpg"; // Set default photo
        } else {
            // Save the uploaded photo
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String photoFilePath = uploadPath + File.separator + photoFileName;
            photoPart.write(photoFilePath);
        }

        // Now you can save the photoFileName and other user data into the database
        // Database logic goes here

        // Set success message
        out.println("<script type='text/javascript'>");
        out.println("document.getElementById('message').innerText = 'Registration successful!';");
        out.println("</script>");
    }
%>

<script src="script/script.js"></script>

</body>
</html>
