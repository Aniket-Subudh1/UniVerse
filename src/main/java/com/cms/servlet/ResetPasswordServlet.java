package com.cms.servlet;

import com.cms.dao.DBConnection;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/resetpassword")
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        boolean emailFound = false; // Flag to check if the email was found

        System.out.println("Received reset password request for email: " + email);

        try (Connection connection = DBConnection.getConnection()) {
            // Check if the email exists in the students table
            String query = "SELECT email FROM students WHERE LOWER(email) = LOWER(?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                emailFound = true; // Email found in students table

                // Hash the new password
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                // Update the password in the students table
                String updateQuery = "UPDATE students SET password=? WHERE LOWER(email)=LOWER(?)";
                PreparedStatement updatePs = connection.prepareStatement(updateQuery);
                updatePs.setString(1, hashedPassword);
                updatePs.setString(2, email);
                updatePs.executeUpdate();

                System.out.println("Password updated successfully for student.");
                response.sendRedirect("index.jsp?success=Password reset successful");
            } else {
                // If not found in students, check the teachers table
                query = "SELECT email FROM teachers WHERE LOWER(email) = LOWER(?)";
                ps = connection.prepareStatement(query);
                ps.setString(1, email);
                rs = ps.executeQuery();

                if (rs.next()) {
                    emailFound = true; // Email found in teachers table

                    // Hash the new password
                    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                    // Update the password in the teachers table
                    String updateQuery = "UPDATE teachers SET password=? WHERE LOWER(email)=LOWER(?)";
                    PreparedStatement updatePs = connection.prepareStatement(updateQuery);
                    updatePs.setString(1, hashedPassword);
                    updatePs.setString(2, email);
                    updatePs.executeUpdate();

                    System.out.println("Password updated successfully for teacher.");
                    response.sendRedirect("index.jsp?success=Password reset successful");
                }
            }

            if (!emailFound) {
                System.out.println("Email not found in either table.");
                response.sendRedirect("reset-password.jsp?error=Email not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("reset-password.jsp?error=Database error&email=" + email);
        }
    }
}
