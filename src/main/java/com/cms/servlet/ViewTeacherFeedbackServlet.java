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

@WebServlet("/viewTeacherFeedback")
public class ViewTeacherFeedbackServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Inner class to represent a feedback entry
    public class FeedbackEntry {
        private int id; // Added feedback ID
        private String courseId;
        private String courseName;
        private int rating;
        private String comments;
        private String studentId;

        public FeedbackEntry(int id, String courseId, String courseName, int rating, String comments, String studentId) {
            this.id = id;
            this.courseId = courseId;
            this.courseName = courseName;
            this.rating = rating;
            this.comments = comments;
            this.studentId = studentId;
        }

        public int getId() { return id; }
        public String getCourseId() { return courseId; }
        public String getCourseName() { return courseName; }
        public int getRating() { return rating; }
        public String getComments() { return comments; }
        public String getStudentId() { return studentId; }
    }

    // Inner class to represent a teacher entry
    public class Teacher {
        private String teacherId;
        private String teacherName;

        public Teacher(String teacherId, String teacherName) {
            this.teacherId = teacherId;
            this.teacherName = teacherName;
        }

        public String getTeacherId() { return teacherId; }
        public String getTeacherName() { return teacherName; }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String teacherId = request.getParameter("teacherId");

        try (Connection connection = DBConnection.getConnection()) {
            if (teacherId == null || teacherId.isEmpty()) {
                // If teacherId is not provided, fetch all teachers with feedback
                List<Teacher> teacherList = new ArrayList<>();
                String teacherQuery = "SELECT DISTINCT t.registrationId AS teacherId, t.name AS teacherName " +
                        "FROM feedback f " +
                        "JOIN teachers t ON f.teacherId = t.registrationId";
                PreparedStatement teacherStmt = connection.prepareStatement(teacherQuery);
                ResultSet teacherRs = teacherStmt.executeQuery();

                while (teacherRs.next()) {
                    String id = teacherRs.getString("teacherId");
                    String name = teacherRs.getString("teacherName");
                    teacherList.add(new Teacher(id, name));
                }
                teacherRs.close();
                teacherStmt.close();

                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(Map.of("status", "success", "teachers", teacherList)));
            } else {
                // Fetch feedback for the selected teacher
                List<FeedbackEntry> feedbackList = new ArrayList<>();
                String feedbackQuery = "SELECT f.id, f.courseId, c.name AS courseName, f.rating, f.comments, f.studentId " +
                        "FROM feedback f " +
                        "JOIN courses c ON f.courseId = c.courseId " +
                        "WHERE f.teacherId = ?";
                PreparedStatement feedbackStmt = connection.prepareStatement(feedbackQuery);
                feedbackStmt.setString(1, teacherId);
                ResultSet feedbackRs = feedbackStmt.executeQuery();

                while (feedbackRs.next()) {
                    int id = feedbackRs.getInt("id");
                    String courseId = feedbackRs.getString("courseId");
                    String courseName = feedbackRs.getString("courseName");
                    int rating = feedbackRs.getInt("rating");
                    String comments = feedbackRs.getString("comments");
                    String studentId = feedbackRs.getString("studentId");

                    feedbackList.add(new FeedbackEntry(id, courseId, courseName, rating, comments, studentId));
                }
                feedbackRs.close();
                feedbackStmt.close();

                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(Map.of("status", "success", "feedback", feedbackList)));
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Error fetching feedback")));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String feedbackId = request.getParameter("feedbackId");

        // Log the feedbackId for debugging purposes
        System.out.println("Received feedbackId: " + feedbackId);

        if (feedbackId == null || feedbackId.isEmpty()) {
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "No feedback ID provided")));
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            String deleteQuery = "DELETE FROM feedback WHERE id = ?";
            PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, Integer.parseInt(feedbackId));
            int rowsAffected = deleteStmt.executeUpdate();
            deleteStmt.close();

            if (rowsAffected > 0) {
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(Map.of("status", "success", "message", "Feedback marked as reviewed and deleted successfully")));
            } else {
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Failed to delete feedback")));
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Invalid feedback ID format")));
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Error deleting feedback")));
        }
    }
}