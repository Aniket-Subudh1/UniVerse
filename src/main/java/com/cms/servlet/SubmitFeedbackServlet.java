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

@WebServlet("/submitFeedback")
public class SubmitFeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Inner class for feedback data structure
    public class CourseFeedbackData {
        private String courseId;
        private String courseName;
        private String teacherId;
        private String teacherName;

        public CourseFeedbackData(String courseId, String courseName, String teacherId, String teacherName) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.teacherId = teacherId;
            this.teacherName = teacherName;
        }

        public String getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String studentId = (String) session.getAttribute("registrationId");

        if (studentId == null) {
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "No student registrationId provided")));
            return;
        }

        // Fetch courses and teachers associated with this student
        List<CourseFeedbackData> feedbackDataList = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT c.courseId, c.name AS courseName, t.registrationId AS teacherId, t.name AS teacherName " +
                    "FROM student_course_tracking sct " +
                    "JOIN courses c ON sct.courseId = c.courseId " +
                    "JOIN timetable tt ON c.courseId = tt.courseId " +
                    "JOIN teachers t ON tt.teacherId = t.registrationId " +
                    "WHERE sct.studentId = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String courseId = rs.getString("courseId");
                String courseName = rs.getString("courseName");
                String teacherId = rs.getString("teacherId");
                String teacherName = rs.getString("teacherName");
                feedbackDataList.add(new CourseFeedbackData(courseId, courseName, teacherId, teacherName));
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Error fetching courses and teachers")));
            return;
        }

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(Map.of("status", "success", "feedbackData", feedbackDataList)));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String studentId = (String) session.getAttribute("registrationId");
        String courseId = request.getParameter("courseId");
        String teacherId = request.getParameter("teacherId");
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comments = request.getParameter("comments");

        if (studentId == null || courseId == null || teacherId == null || rating < 1 || rating > 5) {
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Invalid input data")));
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            // Check if feedback already exists for this student-teacher-course combination
            String checkQuery = "SELECT COUNT(*) AS count FROM feedback WHERE studentId = ? AND teacherId = ? AND courseId = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, studentId);
            checkStmt.setString(2, teacherId);
            checkStmt.setString(3, courseId);
            ResultSet checkResult = checkStmt.executeQuery();

            if (checkResult.next() && checkResult.getInt("count") > 0) {
                // Feedback already exists
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Feedback already submitted for this course and teacher")));
                checkResult.close();
                checkStmt.close();
                return;
            }

            checkResult.close();
            checkStmt.close();

            // Insert new feedback if it doesn't already exist
            String insertQuery = "INSERT INTO feedback (studentId, teacherId, courseId, rating, comments) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, studentId);
            insertStmt.setString(2, teacherId);
            insertStmt.setString(3, courseId);
            insertStmt.setInt(4, rating);
            insertStmt.setString(5, comments);
            insertStmt.executeUpdate();
            insertStmt.close();

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "success", "message", "Feedback submitted successfully")));

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Error submitting feedback")));
  }
}

}
