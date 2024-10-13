package com.cms.servlet;

import com.cms.dao.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/createCourse")
public class ManageCourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public class Course {
        private String courseId;
        private String courseName;
        private String courseDescription;

        public Course(String courseId, String courseName, String courseDescription) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.courseDescription = courseDescription;
        }

        // Getters
        public String getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public String getCourseDescription() {
            return courseDescription;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("load".equals(action)) {
            // Handle course loading
            response.setContentType("application/json");
            try (Connection connection = DBConnection.getConnection()) {
                String query = "SELECT * FROM courses";
                List<Course> courses = new ArrayList<>();
                try (PreparedStatement ps = connection.prepareStatement(query);
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String courseId = rs.getString("courseId");
                        String courseName = rs.getString("name");
                        String courseDescription = rs.getString("description");
                        courses.add(new Course(courseId, courseName, courseDescription));
                    }
                }

                Gson gson = new Gson();
                String jsonCourses = gson.toJson(Map.of("courses", courses));
                response.getWriter().write(jsonCourses);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load courses");
            }
        } else {
            // Default behavior
            super.doGet(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            // Handle course deletion
            String courseId = request.getParameter("courseId");
            try (Connection connection = DBConnection.getConnection()) {
                String deleteQuery = "DELETE FROM courses WHERE courseId = ?";
                try (PreparedStatement ps = connection.prepareStatement(deleteQuery)) {
                    ps.setString(1, courseId);
                    ps.executeUpdate();
                }
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to delete course");
            }
        } else {
            // Handle course creation or update
            super.doPost(request, response);
        }
    }
}
