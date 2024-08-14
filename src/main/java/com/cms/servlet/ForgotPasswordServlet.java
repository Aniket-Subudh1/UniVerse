package com.cms.servlet;

import com.cms.util.DBConnection;
import com.cms.util.GEmailSender;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/forgotpassword")
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String role = request.getParameter("role"); // Either "student" or "teacher"

        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT email FROM " + (role.equals("student") ? "students" : "teachers") + " WHERE email=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Generate OTP
                Random rand = new Random();
                int otp = 100000 + rand.nextInt(900000);

                // Save OTP to the database
                query = "INSERT INTO otp_verification (email, otpCode, expirationTime) VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 5 MINUTE))";
                ps = connection.prepareStatement(query);
                ps.setString(1, email);
                ps.setString(2, String.valueOf(otp));
                ps.executeUpdate();

                // Send OTP to the email
                GEmailSender emailSender = new GEmailSender();
                boolean emailSent = emailSender.sendEmail(
                        email,
                        "noreply@universe.com",
                        "Your OTP Code",
                        "Your OTP code is: " + otp
                );

                if (emailSent) {
                    response.sendRedirect("verify-otp.jsp?email=" + email);
                } else {
                    response.sendRedirect("forgot-password.jsp?error=Failed to send OTP. Please try again.");
                }
            } else {
                response.sendRedirect("forgot-password.jsp?error=Email not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("forgot-password.jsp?error=Database error");
        }
    }
}
