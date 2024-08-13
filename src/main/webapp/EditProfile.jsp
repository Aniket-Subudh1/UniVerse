<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Profile</title>
    <link rel="stylesheet" href="styles/profile.css">
</head>
<body>
<div class="wrapper">
    <form action="editProfile" method="post" enctype="multipart/form-data">
        <h1>Edit Profile</h1>
        <div class="input-box">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${sessionScope.name}" required>
        </div>
        <div class="input-box">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${sessionScope.email}" readonly>
        </div>
        <div class="input-box">
            <label for="dob">Date of Birth:</label>
            <input type="date" id="dob" name="dob" value="${sessionScope.dob}" required>
        </div>
        <div class="input-box">
            <label for="photo">Profile Photo:</label>
            <input type="file" id="photo" name="photo" accept="image/*">
        </div>
        <button type="submit" class="btn">Update Profile</button>
    </form>
</div>
</body>
</html>
