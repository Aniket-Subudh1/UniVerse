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

@WebServlet("/teacherDashboard")
public class ViewAssignSubServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public class AssignedCourse {
        private String courseId;
        private String courseName;

        public AssignedCourse(String courseId, String courseName) {
            this.courseId = courseId;
            this.courseName = courseName;
        }

        public String getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("fetchAssignedCourses".equals(action)) {
            // Get registrationId from session
            HttpSession session = request.getSession(false);
            String registrationId = (String) session.getAttribute("registrationId");

            if (registrationId != null) {
                List<AssignedCourse> assignedCourses = getCoursesByRegistrationId(registrationId);

                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("courses", assignedCourses);

                // Return JSON response
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(jsonResponse));
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
            }
        } else {
            // Default behavior if action is missing
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private List<AssignedCourse> getCoursesByRegistrationId(String registrationId) {
        List<AssignedCourse> courses = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT c.courseId, c.name AS courseName " +
                    "FROM assigncourses ac " +
                    "JOIN courses c ON ac.courseId = c.courseId " +
                    "WHERE ac.registrationId = ?";  // Updated to use registrationId
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, registrationId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String courseId = resultSet.getString("courseId");
                String courseName = resultSet.getString("courseName");
                courses.add(new AssignedCourse(courseId, courseName));
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return courses;
    }
}
