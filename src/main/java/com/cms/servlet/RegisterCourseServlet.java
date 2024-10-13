package com.cms.servlet;

import com.cms.dao.DBConnection;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/registerCourse")
public class RegisterCourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Helper class to represent a course
    private static class Course {
        String courseId;
        String courseName;
        String courseDescription;

        public Course(String courseId, String courseName, String courseDescription) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.courseDescription = courseDescription;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch available courses from the database
        List<Course> availableCourses = getAvailableCourses();

        // Convert the courses list to JSON
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(availableCourses);

        // Set the response content type to JSON and send the JSON data
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle student course registration
        HttpSession session = request.getSession(false);
        String studentId = (String) session.getAttribute("registrationId"); // Student registration ID from session
        String courseId = request.getParameter("courseId");

        if (studentId != null && courseId != null) {
            // Check if the student is already registered for the course
            if (isStudentAlreadyRegistered(studentId, courseId)) {
                Map<String, String> jsonResponse = new HashMap<>();
                jsonResponse.put("status", "duplicate");
                jsonResponse.put("message", "Student is already registered for this course.");
                Gson gson = new Gson();
                response.setContentType("application/json");
                response.getWriter().write(gson.toJson(jsonResponse));
            } else {
                // Get the teacher's registrationId from assigncourses table for the selected course
                String teacherRegistrationId = getTeacherRegistrationId(courseId);

                if (teacherRegistrationId != null) {
                    // Register the student for the course
                    boolean isRegistered = registerStudentCourse(studentId, courseId, teacherRegistrationId);

                    // Respond with success or failure message
                    Map<String, String> jsonResponse = new HashMap<>();
                    jsonResponse.put("status", isRegistered ? "success" : "failed");

                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    response.getWriter().write(gson.toJson(jsonResponse));
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Teacher registration ID not found");
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
        }
    }

    // Check if the student is already registered for the course
    private boolean isStudentAlreadyRegistered(String studentId, String courseId) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM student_course_tracking WHERE studentId = ? AND courseId = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, studentId);
            ps.setString(2, courseId);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Return true if a record is found
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if any error occurs
        }
    }

    // Fetch available courses from the courses table
    private List<Course> getAvailableCourses() {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT courseId, name, description FROM courses";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String courseId = rs.getString("courseId");
                String courseName = rs.getString("name");
                String courseDescription = rs.getString("description");
                courses.add(new Course(courseId, courseName, courseDescription));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    // Get teacher's registrationId from the assigncourses table based on the courseId
    private String getTeacherRegistrationId(String courseId) {
        String registrationId = null;
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT registrationId FROM assigncourses WHERE courseId = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                registrationId = rs.getString("registrationId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registrationId;
    }

    // Register student for a course in student_course_tracking
    private boolean registerStudentCourse(String studentId, String courseId, String registrationId) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO student_course_tracking (studentId, courseId, registrationId, enrollmentDate) VALUES (?, ?, ?, CURDATE())";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, studentId);
            ps.setString(2, courseId);
            ps.setString(3, registrationId); // Teacher's registrationId
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
