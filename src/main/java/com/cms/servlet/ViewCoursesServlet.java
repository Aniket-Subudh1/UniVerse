package com.cms.servlet;

import com.cms.dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/viewCourses")
public class ViewCoursesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Inner class to represent the Course entity
    public class Course {
        private String courseId;
        private String courseName;
        private String registrationId;

        public Course(String courseId, String courseName, String registrationId) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.registrationId = registrationId;
        }

        // Getters
        public String getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public String getRegistrationId() {
            return registrationId;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String registrationId = (String) session.getAttribute("registrationId");
        System.out.println("registrationId: " + registrationId);
        String studentId = registrationId;
        if (studentId== null || studentId.isEmpty()) {
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "No student registrationId provided")));
            return;
        }

        // Query to fetch registered courses for the student
        List<Course> courses = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT c.courseId, c.name, s.studentId FROM courses c " +
                    "JOIN student_course_tracking s ON c.courseId = s.courseId " +
                    "WHERE s.studentId = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String courseId = rs.getString("courseId");
                String courseName = rs.getString("name");
                String studentId1 = rs.getString("studentId");
                courses.add(new Course(courseId, courseName, studentId1));
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Error fetching registered courses")));
            return;
        }

        // Send the list of courses as JSON
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(Map.of("status", "success", "courses", courses)));
    }
}
