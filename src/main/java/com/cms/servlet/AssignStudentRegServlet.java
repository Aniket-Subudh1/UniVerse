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

@WebServlet("/AssignStudentRegServlet")
public class AssignStudentRegServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentName = request.getParameter("studentName");
        String studentRegId = request.getParameter("studentRegId");
        String enrolledCourse = request.getParameter("enrolledCourse");

        try (Connection connection = DBConnection.getConnection()) {

            String query = "INSERT INTO students (name, registrationId, enrolledCourse, email, password) " +
                    "VALUES (?, ?, ?, 'default@universe.com', 'defaultPassword') " +
                    "ON DUPLICATE KEY UPDATE registrationId=?, enrolledCourse=?";

            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, studentName);
                ps.setString(2, studentRegId);
                ps.setString(3, enrolledCourse);
                ps.setString(4, studentRegId);
                ps.setString(5, enrolledCourse);

                int result = ps.executeUpdate();
                if (result > 0) {
                    response.sendRedirect("admin-dashboard.jsp?success=Student registration ID and course assigned successfully.");
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
