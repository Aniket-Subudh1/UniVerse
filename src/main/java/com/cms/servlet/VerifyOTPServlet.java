package com.cms.servlet;

import com.cms.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/verifyotp")
public class VerifyOTPServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
                response.sendRedirect("reset-password.jsp?email=" + email);
            } else {
                response.sendRedirect("verify-otp.jsp?error=Invalid OTP&email=" + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("verify-otp.jsp?error=Database error&email=" + email);
        }
    }
}
