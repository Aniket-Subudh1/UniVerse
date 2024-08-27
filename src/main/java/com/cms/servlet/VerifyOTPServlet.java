package com.cms.servlet;

import com.cms.dao.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/verifyotp")
public class VerifyOTPServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(VerifyOTPServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String otp = request.getParameter("otp");

        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT otpCode FROM otp_verification WHERE email=? AND otpCode=? AND expirationTime > NOW()";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, otp);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Clean up OTP from the database upon successful verification
                deleteOtp(email, connection);

                response.sendRedirect("reset-password.jsp?email=" + email);
            } else {
                logger.warn("Invalid OTP attempt for email: " + email);
                response.sendRedirect("verify-otp.jsp?error=The OTP you entered is incorrect or has expired. Please try again.&email=" + email);
            }
        } catch (SQLException e) {
            logger.error("Database error during OTP verification for email: " + email, e);
            response.sendRedirect("verify-otp.jsp?error=Database error&email=" + email);
        }
    }

    private void deleteOtp(String email, Connection connection) throws SQLException {
        String deleteQuery = "DELETE FROM otp_verification WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(deleteQuery)) {
            ps.setString(1, email);
            ps.executeUpdate();
        }
    }
}
