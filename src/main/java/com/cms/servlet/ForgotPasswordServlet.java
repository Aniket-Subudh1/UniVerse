package com.cms.servlet;

import com.cms.dao.DBConnection;
import com.cms.util.GEmailSender;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/forgotpassword")
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(ForgotPasswordServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email").trim();
        boolean emailFound = false;

        logger.info("Received reset password request for email: " + email);

        try (Connection connection = DBConnection.getConnection()) {
            emailFound = isEmailExist(connection, email);

            if (emailFound) {
                // Generate OTP
                int otp = generateOtp();

                // Save OTP to the database
                saveOtpToDatabase(connection, email, otp);

                // Send OTP to the email
                if (sendOtpToEmail(email, otp)) {
                    response.sendRedirect("verify-otp.jsp?email=" + email);
                } else {
                    response.sendRedirect("forgot-password.jsp?error=Failed to send OTP. Please try again.");
                }
            } else {
                response.sendRedirect("forgot-password.jsp?error=Email not found");
            }
        } catch (Exception e) {
            logger.error("Error during forgot password process", e);
            response.sendRedirect("forgot-password.jsp?error=Database error");
        }
    }

    private boolean isEmailExist(Connection connection, String email) throws SQLException {
        String query = "SELECT email FROM students WHERE LOWER(email) = LOWER(?) UNION SELECT email FROM teachers WHERE LOWER(email) = LOWER(?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    private int generateOtp() {
        Random rand = new Random();
        return 100000 + rand.nextInt(900000);
    }

    private void saveOtpToDatabase(Connection connection, String email, int otp) throws SQLException {
        String insertQuery = "INSERT INTO otp_verification (email, otpCode, expirationTime) VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 5 MINUTE))";
        try (PreparedStatement insertPs = connection.prepareStatement(insertQuery)) {
            insertPs.setString(1, email);
            insertPs.setString(2, String.valueOf(otp));
            insertPs.executeUpdate();
            logger.info("OTP saved successfully for email: " + email);
        }
    }

    private boolean sendOtpToEmail(String email, int otp) {
        GEmailSender emailSender = new GEmailSender();
        String subject = "Your OTP Code";
        String message = "Your OTP code is: " + otp;
        return emailSender.sendEmail(email, "noreply@universe.com", subject, message);
    }
}
