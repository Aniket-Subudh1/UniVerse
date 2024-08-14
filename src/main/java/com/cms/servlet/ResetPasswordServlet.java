package com.cms.servlet;

import com.cms.util.DBConnection;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/resetpassword")
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role"); // Either "student" or "teacher"

        try (Connection connection = DBConnection.getConnection()) {
            // Check if the email exists in the corresponding table
            String checkQuery = "SELECT email FROM " + (role.equals("student") ? "students" : "teachers") + " WHERE LOWER(email) = LOWER(?)";
            PreparedStatement checkPs = connection.prepareStatement(checkQuery);
            checkPs.setString(1, email);
            if (!checkPs.executeQuery().next()) {
                response.sendRedirect("reset-password.jsp?error=Email not found&email=" + email);
                return;
            }

            // Hash the password before updating it
            String hashedPassword = hashPassword(password);

            String query = "UPDATE " + (role.equals("student") ? "students" : "teachers") + " SET password=? WHERE email=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, hashedPassword);
            ps.setString(2, email);
            int result = ps.executeUpdate();

            if (result > 0) {
                response.sendRedirect("login.jsp?success=Password reset successful");
            } else {
                response.sendRedirect("reset-password.jsp?error=Password reset failed&email=" + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("reset-password.jsp?error=Database error&email=" + email);
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
