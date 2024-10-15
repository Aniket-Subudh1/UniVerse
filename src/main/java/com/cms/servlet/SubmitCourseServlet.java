package com.cms.servlet;

import com.cms.dao.DBConnection;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

@WebServlet("/submitCourse")
public class SubmitCourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch course details from the request
        String courseId = request.getParameter("courseId");
        String courseName = request.getParameter("courseName");
        String courseDescription = request.getParameter("courseDescription");

        try (Connection connection = DBConnection.getConnection()) {
            String insertQuery = "INSERT INTO pending_courses (courseId, name, description) VALUES (?, ?, ?)";

            // Insert new course into pending_courses table for admin approval
            try (PreparedStatement ps = connection.prepareStatement(insertQuery)) {
                ps.setString(1, courseId);
                ps.setString(2, courseName);
                ps.setString(3, courseDescription);
                ps.executeUpdate();
            }
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "success", "message", "Course submitted for approval")));
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Unable to submit course")));
        }
    }
}
