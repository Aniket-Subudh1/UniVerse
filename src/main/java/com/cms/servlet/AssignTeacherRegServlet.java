package com.cms.servlet;

import com.cms.dao.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AssignTeacherRegServlet")
public class AssignTeacherRegServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String teacherName = request.getParameter("teacherName");
        String teacherRegId = request.getParameter("teacherRegId");

        try (Connection connection = DBConnection.getConnection()) {
            // Insert teacher name and registration ID only; email and other fields will be updated later
            String query = "INSERT INTO teachers (name, registrationId, email, password) " +
                    "VALUES (?, ?, 'default@universe.com', 'defaultPassword') " +
                    "ON DUPLICATE KEY UPDATE registrationId=?";

            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, teacherName);
                ps.setString(2, teacherRegId);
                ps.setString(3, teacherRegId);

                int result = ps.executeUpdate();
                if (result > 0) {
                    response.sendRedirect("admin-dashboard.jsp?success=Teacher registration ID assigned successfully.");
                } else {
                    response.sendRedirect("assign-registration.jsp?error=Failed to assign registration ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("assign-registration.jsp?error=Database error.");
        }
    }
}
