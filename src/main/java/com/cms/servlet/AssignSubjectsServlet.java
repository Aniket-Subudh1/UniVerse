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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/assignSubjects")
public class AssignSubjectsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static class Course {
        String courseId;
        String courseName;  // Use camelCase
        String courseDescription;  // Use camelCase

        public Course(String courseId, String courseName, String courseDescription) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.courseDescription = courseDescription;
        }
    }

    private static class Teacher {
        int teacherId;
        String teacherName;  // Use camelCase
        String photo;  // Use camelCase

        public Teacher(int teacherId, String teacherName, String photo) {
            this.teacherId = teacherId;
            this.teacherName = teacherName;
            this.photo = photo;
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from the form
        String courseId = request.getParameter("courseId");
        String teacherId = request.getParameter("teacherId");

        try (Connection connection = DBConnection.getConnection()) {
            // Insert the assigned teacher to the course
            String insertQuery = "INSERT INTO assigncourses (courseId, teacherId) VALUES (?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertQuery)) {
                ps.setString(1, courseId);
                ps.setInt(2, Integer.parseInt(teacherId));
                ps.executeUpdate();
            }

            // Redirect to the success modal
            response.sendRedirect("assign-subjects.jsp?success=1");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("assign-subjects.jsp?error=Database error");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("loadCourses".equals(action)) {
            loadCourses(response);
        } else if ("loadTeachers".equals(action)) {
            loadTeachers(response);
        }
    }

    private void loadCourses(HttpServletResponse response) throws IOException {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM courses";
            try (PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String courseId = rs.getString("courseId");
                    String courseName = rs.getString("name");  // Map to courseName
                    String courseDescription = rs.getString("description");  // Map to courseDescription
                    courses.add(new Course(courseId, courseName, courseDescription));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, List<Course>> result = new HashMap<>();
        result.put("courses", courses);

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(result);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }

    private void loadTeachers(HttpServletResponse response) throws IOException {
        List<Teacher> teachers = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM teachers";
            try (PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int teacherId = rs.getInt("teacherId");
                    String teacherName = rs.getString("name");  // Map to teacherName
                    byte[] photoBytes = rs.getBytes("photo");
                    String photo = (photoBytes != null) ? java.util.Base64.getEncoder().encodeToString(photoBytes) : "";
                    teachers.add(new Teacher(teacherId, teacherName, photo));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, List<Teacher>> result = new HashMap<>();
        result.put("teachers", teachers);

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(result);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}