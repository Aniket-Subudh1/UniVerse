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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/createCourse")
public class ManageCourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Inner class to represent the Course entity
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

    // Handle GET requests (e.g., loading courses)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("load".equals(action)) {
            // Load all courses from the database
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
        } else if ("loadPending".equals(action)) {
            response.setContentType("application/json");
            try (Connection connection = DBConnection.getConnection()) {
                String query = "SELECT * FROM pending_courses";
                List<Course> pendingCourses = new ArrayList<>();
                try (PreparedStatement ps = connection.prepareStatement(query);
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String courseId = rs.getString("courseId");
                        String courseName = rs.getString("name");
                        String courseDescription = rs.getString("description");
                        pendingCourses.add(new Course(courseId, courseName, courseDescription));
                    }
                }
                Gson gson = new Gson();
                String jsonPendingCourses = gson.toJson(Map.of("pendingCourses", pendingCourses));
                response.getWriter().write(jsonPendingCourses);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load pending courses");
            }
        }
    }

        // Method to load pending courses
    private void loadPendingCourses(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM pending_courses";
            List<Course> pendingCourses = new ArrayList<>();
            try (PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String courseId = rs.getString("courseId");
                    String courseName = rs.getString("name");
                    String courseDescription = rs.getString("description");
                    pendingCourses.add(new Course(courseId, courseName, courseDescription));
                }
            }

            Gson gson = new Gson();
            String jsonPendingCourses = gson.toJson(Map.of("pendingCourses", pendingCourses));
            response.getWriter().write(jsonPendingCourses);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load pending courses");
        }
    }

    // Handle POST requests (e.g., deleting or creating courses)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            // Handle course deletion
            deleteCourse(request, response);
        } else if (action == null || "create".equals(action)) {
            // Handle course creation
            createCourse(request, response);
        } else if ("approve".equals(action)) {
            // Handle course approval
            approveCourse(request, response);
        } else if ("reject".equals(action)) {
            // Handle course rejection
            rejectCourse(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    // Method to delete a course
    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String courseId = request.getParameter("courseId");
        try (Connection connection = DBConnection.getConnection()) {
            String deleteQuery = "DELETE FROM courses WHERE courseId = ?";
            try (PreparedStatement ps = connection.prepareStatement(deleteQuery)) {
                ps.setString(1, courseId);
                ps.executeUpdate();
            }
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "success", "message", "Course deleted successfully")));
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Unable to delete course")));
        }
    }

    // Method to create a course
    private void createCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String courseId = request.getParameter("courseId");
        String courseName = request.getParameter("courseName");
        String courseDescription = request.getParameter("courseDescription");

        try (Connection connection = DBConnection.getConnection()) {
            String checkQuery = "SELECT * FROM courses WHERE courseId = ?";
            String insertQuery = "INSERT INTO courses (courseId, name, description) VALUES (?, ?, ?)";

            // Check if the course already exists
            try (PreparedStatement checkPs = connection.prepareStatement(checkQuery)) {
                checkPs.setString(1, courseId);
                ResultSet rs = checkPs.executeQuery();
                if (rs.next()) {
                    response.setContentType("application/json");
                    response.getWriter().write(new Gson().toJson(Map.of("status", "exists", "message", "Course already exists")));
                    return;
                }
            }

            // Insert new course if it doesn't exist
            try (PreparedStatement ps = connection.prepareStatement(insertQuery)) {
                ps.setString(1, courseId);
                ps.setString(2, courseName);
                ps.setString(3, courseDescription);
                ps.executeUpdate();
            }
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "success", "message", "Course created successfully")));
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Unable to create course")));
        }
    }

    // Method to approve a pending course (move from pending_courses to courses)
    private void approveCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String courseId = request.getParameter("courseId");

        try (Connection connection = DBConnection.getConnection()) {
            String insertQuery = "INSERT INTO courses (courseId, name, description) "
                    + "SELECT courseId, name, description FROM pending_courses WHERE courseId = ?";
            String deleteQuery = "DELETE FROM pending_courses WHERE courseId = ?";

            try (PreparedStatement insertPs = connection.prepareStatement(insertQuery);
                 PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
                insertPs.setString(1, courseId);
                deletePs.setString(1, courseId);

                insertPs.executeUpdate();
                deletePs.executeUpdate();
            }
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "success", "message", "Course approved and added to courses")));
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Unable to approve course")));
        }
    }

    // Method to reject a pending course (delete from pending_courses)
    private void rejectCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String courseId = request.getParameter("courseId");

        try (Connection connection = DBConnection.getConnection()) {
            String deleteQuery = "DELETE FROM pending_courses WHERE courseId = ?";
            try (PreparedStatement ps = connection.prepareStatement(deleteQuery)) {
                ps.setString(1, courseId);
                ps.executeUpdate();
            }
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "success", "message", "Course rejected and removed from pending courses")));
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Unable to reject course")));
        }
    }
}
