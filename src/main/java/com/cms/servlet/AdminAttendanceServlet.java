package com.cms.servlet;

import com.cms.dao.DBConnection;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@WebServlet("/adminAttendance")
public class AdminAttendanceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Handle GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Set response content type to JSON
        response.setContentType("application/json");

        String action = request.getParameter("action");

        if ("viewCourses".equals(action)) {
            viewAssignedCourses(response);
        } else if ("loadTimetable".equals(action)) {
            String teacherId = request.getParameter("teacherId");
            String courseId = request.getParameter("courseId");
            loadTimetable(teacherId, courseId, response);
        } else if ("viewStudents".equals(action)) {
            String courseId = request.getParameter("courseId");
            String teacherId = request.getParameter("teacherId");
            String dayOfWeek = request.getParameter("dayOfWeek");
            String timeStart = request.getParameter("timeStart");
            String timeEnd = request.getParameter("timeEnd");
            viewEnrolledStudents(courseId, teacherId, dayOfWeek, timeStart, timeEnd, response);
        }
    }

    // Handle POST requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type to JSON
        response.setContentType("application/json");

        String action = request.getParameter("action");

        if ("markAttendance".equals(action)) {
            markAttendance(request, response);
        }
    }

    // Fetch Courses Assigned to Teachers
    private void viewAssignedCourses(HttpServletResponse response) throws IOException {
        List<Map<String, String>> assignedCourses = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT ac.courseId, c.name AS courseName, t.registrationId AS teacherId, t.name AS teacherName " +
                    "FROM assigncourses ac " +
                    "JOIN courses c ON ac.courseId = c.courseId " +
                    "JOIN teachers t ON ac.registrationId = t.registrationId";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> courseDetails = new HashMap<>();
                courseDetails.put("courseId", rs.getString("courseId"));
                courseDetails.put("courseName", rs.getString("courseName"));
                courseDetails.put("teacherId", rs.getString("teacherId"));
                courseDetails.put("teacherName", rs.getString("teacherName"));
                assignedCourses.add(courseDetails);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sendJsonResponse(response, Map.of("assignedCourses", assignedCourses));
    }

    // Load Timetable Based on Teacher and Course
    private void loadTimetable(String teacherId, String courseId, HttpServletResponse response) throws IOException {
        List<Map<String, String>> timetableEntries = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT id, courseId, teacherId, enrolledCourse, dayOfWeek, timeStart, timeEnd " +
                    "FROM timetable " +
                    "WHERE teacherId = ? AND courseId = ? ORDER BY dayOfWeek, timeStart";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, teacherId);
            ps.setString(2, courseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> entry = new HashMap<>();
                entry.put("id", rs.getString("id"));
                entry.put("courseId", rs.getString("courseId")); // Include courseId
                entry.put("teacherId", rs.getString("teacherId")); // Include teacherId
                entry.put("enrolledCourse", rs.getString("enrolledCourse"));
                entry.put("dayOfWeek", rs.getString("dayOfWeek"));
                entry.put("timeStart", rs.getString("timeStart"));
                entry.put("timeEnd", rs.getString("timeEnd"));
                timetableEntries.add(entry);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sendJsonResponse(response, Map.of("timetable", timetableEntries));
    }

    // View Enrolled Students for a Time Slot
    private void viewEnrolledStudents(String courseId, String teacherId, String dayOfWeek, String timeStart, String timeEnd, HttpServletResponse response) throws IOException {
        List<Map<String, String>> enrolledStudents = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT s.registrationId AS studentId, s.name AS studentName " +
                    "FROM student_course_tracking sct " +
                    "JOIN students s ON s.registrationId = sct.studentId " +
                    "JOIN timetable t ON sct.courseId = t.courseId AND sct.registrationId = t.teacherId " +
                    "WHERE sct.courseId = ? AND t.teacherId = ? AND t.dayOfWeek = ? AND t.timeStart = ? AND t.timeEnd = ? " +
                    "AND s.enrolledCourse = t.enrolledCourse";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, courseId);
            ps.setString(2, teacherId);
            ps.setString(3, dayOfWeek);
            ps.setString(4, timeStart);
            ps.setString(5, timeEnd);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> studentDetails = new HashMap<>();
                studentDetails.put("studentId", rs.getString("studentId"));
                studentDetails.put("studentName", rs.getString("studentName"));
                enrolledStudents.add(studentDetails);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sendJsonResponse(response, Map.of("enrolledStudents", enrolledStudents));
    }

    // Mark Attendance Based on Timetable Constraints
    private void markAttendance(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null)
                jsonBuffer.append(line);
        }

        String jsonString = jsonBuffer.toString();

        Gson gson = new Gson();
        Map<String, Object> data = gson.fromJson(jsonString, Map.class);

        String teacherId = (String) data.get("teacherId");
        String courseId = (String) data.get("courseId");
        String attendanceDate = (String) data.get("date");
        String timeStart = (String) data.get("timeStart");
        String timeEnd = (String) data.get("timeEnd");
        List<Map<String, String>> studentAttendance = (List<Map<String, String>>) data.get("studentAttendance");

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO attendance (studentId, courseId, teacherId, date, timeStart, timeEnd, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            for (Map<String, String> entry : studentAttendance) {
                String studentId = entry.get("studentId");
                String status = entry.get("status");

                ps.setString(1, studentId);
                ps.setString(2, courseId);
                ps.setString(3, teacherId);
                ps.setString(4, attendanceDate);
                ps.setString(5, timeStart);
                ps.setString(6, timeEnd);
                ps.setString(7, status);
                ps.addBatch();
            }

            ps.executeBatch();
            ps.close();

            sendJsonResponse(response, Map.of("status", "success", "message", "Attendance marked successfully."));
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonResponse(response, Map.of("status", "error", "message", "Failed to mark attendance."));
        }
    }

    // Utility Method: Send JSON Response
    private void sendJsonResponse(HttpServletResponse response, Map<String, Object> data) throws IOException {
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(data);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}
