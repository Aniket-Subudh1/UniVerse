package com.cms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cms.dao.DBConnection;
import com.cms.model.ViewGrades;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/manageGrades")
class ManageGradesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action"); // The action (add, update, delete)
        String studentId = request.getParameter("studentId");
        String subjectCode = request.getParameter("subjectCode");
        String grade = request.getParameter("grade");

        if (action != null && studentId != null && subjectCode != null) {
            switch (action) {
                case "add":
                    addGrade(studentId, subjectCode, grade, request, response);
                    break;
                case "update":
                    updateGrade(studentId, subjectCode, grade, request, response);
                    break;
                case "delete":
                    deleteGrade(studentId, subjectCode, request, response);
                    break;
                default:
                    request.setAttribute("errorMessage", "Invalid action.");
                    request.getRequestDispatcher("ManageGrades.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Please provide valid inputs.");
            request.getRequestDispatcher("ManageGrades.jsp").forward(request, response);
        }
    }

    // Method to add a grade
    private void addGrade(String studentId, String subjectCode, String grade, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO student_grades (student_id, subject_code, grade) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentId);
            statement.setString(2, subjectCode);
            statement.setString(3, grade);

            int result = statement.executeUpdate();
            if (result > 0) {
                request.setAttribute("successMessage", "Grade added successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to add grade.");
            }

            request.getRequestDispatcher("ManageGrades.jsp").forward(request, response);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("ManageGrades.jsp").forward(request, response);
        }
    }

    // Method to update a grade
    private void updateGrade(String studentId, String subjectCode, String grade, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "UPDATE student_grades SET grade = ? WHERE student_id = ? AND subject_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, grade);
            statement.setString(2, studentId);
            statement.setString(3, subjectCode);

            int result = statement.executeUpdate();
            if (result > 0) {
                request.setAttribute("successMessage", "Grade updated successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to update grade.");
            }

            request.getRequestDispatcher("ManageGrades.jsp").forward(request, response);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("ManageGrades.jsp").forward(request, response);
        }
    }

    // Method to delete a grade
    private void deleteGrade(String studentId, String subjectCode, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "DELETE FROM student_grades WHERE student_id = ? AND subject_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentId);
            statement.setString(2, subjectCode);

            int result = statement.executeUpdate();
            if (result > 0) {
                request.setAttribute("successMessage", "Grade deleted successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to delete grade.");
            }

            request.getRequestDispatcher("ManageGrades.jsp").forward(request, response);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("ManageGrades.jsp").forward(request, response);
        }
    }
}
