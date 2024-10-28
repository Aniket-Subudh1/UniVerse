<!-- addFee.jsp -->
<%@ page import="java.sql.*, java.util.ArrayList"%>
<%@ page import="com.cms.dao.DBConnection" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Fees</title>
</head>
<body>
<h2>Add Student Fees</h2>
<form action="AddFeeServlet" method="post">
    <label for="registrationId">Registration ID:</label>
    <select name="registrationId" id="registrationId">
        <%
            try (Connection conn = DBConnection.getConnection()){

                String query = "SELECT registrationId FROM students";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    String regId = rs.getString("registrationId");
                    out.println("<option value='" + regId + "'>" + regId + "</option>");
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </select><br><br>

    <label for="academicFee">Academic Fee:</label>
    <input type="number" name="academicFee" id="academicFee" required><br><br>

    <label for="hostelFee">Hostel Fee:</label>
    <input type="number" name="hostelFee" id="hostelFee"><br><br>

    <label for="examFee">Exam Fee:</label>
    <input type="number" name="examFee" id="examFee"><br><br>

    <button type="submit">Add Fees</button>
</form>
</body>
</html>
