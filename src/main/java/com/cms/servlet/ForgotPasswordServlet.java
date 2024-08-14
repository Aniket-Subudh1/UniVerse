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
        String email = request.getParameter("email").trim();
        boolean emailFound = false; // Flag to track if the email was found

        System.out.println("Received reset password request for email: " + email);

        try (Connection connection = DBConnection.getConnection()) {
            // Check if the email exists in the students table
            String query = "SELECT email FROM students WHERE LOWER(email) = LOWER(?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                emailFound = true; // Email found in students table
            } else {
                // If not found in students, check the teachers table
                query = "SELECT email FROM teachers WHERE LOWER(email) = LOWER(?)";
                ps = connection.prepareStatement(query);
                ps.setString(1, email);
                rs = ps.executeQuery();

                if (rs.next()) {
                    emailFound = true; // Email found in teachers table
                }
            }

            if (emailFound) {
                // Generate OTP
                Random rand = new Random();
                int otp = 100000 + rand.nextInt(900000);

                // Save OTP to the database
                String insertQuery = "INSERT INTO otp_verification (email, otpCode, expirationTime) VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 5 MINUTE))";
                PreparedStatement insertPs = connection.prepareStatement(insertQuery);
                insertPs.setString(1, email);
                insertPs.setString(2, String.valueOf(otp));
                insertPs.executeUpdate();

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
