<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Profile</title>
    <link rel="stylesheet" href="styles/edit-profile.css?v=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>

<body>
<div class="wrapper">
    <form id="editProfileForm" action="editProfile" method="post" enctype="multipart/form-data">
        <h1>Edit Profile</h1>

        <!-- Name Input Field -->
        <div class="input-box">
            <input type="text" id="name" name="name" placeholder="Name" value="${sessionScope.name}">
            <i class='bx bxs-user'></i>
        </div>

        <!-- Date of Birth Input Field -->
        <div class="input-box">
            <input type="date" id="dob" name="dob" placeholder="Date of Birth" value="${sessionScope.dob}">
            <i class='bx bxs-calendar'></i>
        </div>

        <!-- Photo Upload Field -->
        <div class="input-box-file">
            <input type="file" id="photo" name="photo" accept="image/*">
            <i class='bx bxs-image'></i>
        </div>

        <!-- Hidden Field for User Role -->
        <input type="hidden" id="userRole" name="userRole" value="${sessionScope.role}">

        <!-- Submit Button -->
        <button type="submit" class="btn">Update Profile</button>

        <!-- Message Placeholder -->
        <div id="message"></div>
    </form>
</div>

<!-- Success Modal -->
<div id="successModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeSuccessModal()">&times;</span>
        <h2>Profile Updated Successfully</h2>
        <p>Your profile has been updated. You can continue using the application with the new details.</p>
    </div>
</div>

<!-- Error Modal -->
<div id="errorModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeErrorModal()">&times;</span>
        <h2>Profile Update Failed</h2>
        <p>There was an issue updating your profile. Please try again later.</p>
    </div>
</div>

<script src="script/editProfile.js"></script> <!-- External JavaScript file -->
</body>

</html>
