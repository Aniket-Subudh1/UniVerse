package com.cms.servlet;

import com.cms.dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet("/GradeManagementServlet")
public class AddGradeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("add_grade.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String courseId = request.getParameter("courseId");
        String grade = request.getParameter("grade");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO grades (studentId, courseId, grade) VALUES (?, ?, ?)")) {
            stmt.setString(1, studentId);
            stmt.setString(2, courseId);
            stmt.setString(3, grade);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                // Set success message as a request attribute
                request.setAttribute("successMessage", "Grade added successfully!");
            } else {
                request.setAttribute("successMessage", "Failed to add grade. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("successMessage", "An error occurred: " + e.getMessage());
        }

        // Forward back to the same page
        request.getRequestDispatcher("add_grade.jsp").forward(request, response);
    }
}
