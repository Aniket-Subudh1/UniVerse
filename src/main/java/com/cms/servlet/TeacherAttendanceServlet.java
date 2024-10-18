package com.cms.servlet;

import com.cms.dao.DBConnection;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@WebServlet("/teacherAttendance")
public class TeacherAttendanceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Handle GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        String teacherId = (String) session.getAttribute("registrationId");

        if ("viewAssignedCourses".equals(action) && teacherId != null) {
            viewAssignedCourses(teacherId, response);
        } else if ("loadTimetable".equals(action) && teacherId != null) {
            String courseId = request.getParameter("courseId");
            loadTimetable(teacherId, courseId, response);
        } else if ("viewStudents".equals(action) && teacherId != null) {
            String courseId = request.getParameter("courseId");
            String dayOfWeek = request.getParameter("dayOfWeek");
            String timeStart = request.getParameter("timeStart");
            String timeEnd = request.getParameter("timeEnd");
            viewEnrolledStudents(courseId, teacherId, dayOfWeek, timeStart, timeEnd, response);
        }
    }

    // Handle POST requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        String teacherId = (String) session.getAttribute("registrationId");

        if ("markAttendance".equals(action) && teacherId != null) {
            markAttendance(request, teacherId, response);
        }
    }

    // Fetch Assigned Courses for Teacher
    private void viewAssignedCourses(String teacherId, HttpServletResponse response) throws IOException {
        List<Map<String, String>> assignedCourses = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT ac.courseId, c.name AS courseName " +
                    "FROM assigncourses ac " +
                    "JOIN courses c ON ac.courseId = c.courseId " +
                    "WHERE ac.registrationId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, teacherId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> courseDetails = new HashMap<>();
                courseDetails.put("courseId", rs.getString("courseId"));
                courseDetails.put("courseName", rs.getString("courseName"));
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
            String sql = "SELECT id, dayOfWeek, timeStart, timeEnd " +
                    "FROM timetable " +
                    "WHERE teacherId = ? AND courseId = ? ORDER BY dayOfWeek, timeStart";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, teacherId);
            ps.setString(2, courseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> entry = new HashMap<>();
                entry.put("id", rs.getString("id"));
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
            String sql = "SELECT DISTINCT s.registrationId AS studentId, s.name AS studentName " +
                    "FROM students s " +
                    "JOIN student_course_tracking sct ON s.registrationId = sct.studentId " +
                    "JOIN timetable t ON sct.courseId = t.courseId AND t.teacherId = ? AND t.dayOfWeek = ? AND t.timeStart = ? AND t.timeEnd = ? " +
                    "WHERE sct.courseId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, teacherId);
            ps.setString(2, dayOfWeek);
            ps.setString(3, timeStart);
            ps.setString(4, timeEnd);
            ps.setString(5, courseId);

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
    private void markAttendance(HttpServletRequest request, String teacherId, HttpServletResponse response) throws IOException {
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null)
                jsonBuffer.append(line);
        }

        String jsonString = jsonBuffer.toString();

        Gson gson = new Gson();
        Map<String, Object> data = gson.fromJson(jsonString, Map.class);

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
