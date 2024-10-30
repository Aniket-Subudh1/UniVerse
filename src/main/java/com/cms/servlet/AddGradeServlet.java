package com.cms.servlet;

import com.cms.dao.DBConnection;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/GradeManagementServlet")
public class AddGradeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        List<String> ids = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) { // Using DBConnection for connection
            if (studentId == null || studentId.isEmpty()) {
                // Fetch all student IDs
                try (PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT studentId FROM student_course_tracking")) {
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        ids.add(rs.getString("studentId"));
                    }
                }
            } else {
                // Fetch course IDs associated with the given studentId
                try (PreparedStatement stmt = conn.prepareStatement("SELECT courseId FROM student_course_tracking WHERE studentId = ?")) {
                    stmt.setString(1, studentId);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        ids.add(rs.getString("courseId"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return list of IDs as JSON response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(ids));
    }

    // The doPost method for inserting a new grade
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
                response.sendRedirect("success.jsp");
            } else {
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
