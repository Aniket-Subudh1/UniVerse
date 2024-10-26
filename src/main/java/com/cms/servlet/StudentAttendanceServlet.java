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
import java.util.*;

@WebServlet("/studentAttendance")
public class StudentAttendanceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Handle GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Set content type and character encoding
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Retrieve the student's registration ID from the session
        HttpSession session = request.getSession(false); // Use false to avoid creating a new session
        String studentId = (session != null) ? (String) session.getAttribute("registrationId") : null;

        if (studentId != null) {
            System.out.println("Registration ID found in session: " + studentId); // Debugging statement

            // Get date parameters
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");

            displayAttendanceRecords(studentId, startDate, endDate, response);
        } else {
            System.out.println("Error: Registration ID not found in session."); // Debugging statement
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Not authorized\"}");
            response.getWriter().flush();
        }
    }

    // Method to fetch attendance records based on registrationId and date range
    private void displayAttendanceRecords(String studentId, String startDate, String endDate, HttpServletResponse response) throws IOException {
        List<Map<String, String>> attendanceRecords = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection()) {
            System.out.println("Database connection established."); // Debugging statement

            StringBuilder sql = new StringBuilder("SELECT a.date, a.status, a.timeStart, a.timeEnd, c.courseId, c.name AS courseName " +
                    "FROM attendance a " +
                    "JOIN courses c ON a.courseId = c.courseId " +
                    "WHERE a.studentId = ?");

            List<Object> params = new ArrayList<>();
            params.add(studentId);

            if (startDate != null && !startDate.isEmpty()) {
                sql.append(" AND a.date >= ?");
                params.add(startDate);
            }

            if (endDate != null && !endDate.isEmpty()) {
                sql.append(" AND a.date <= ?");
                params.add(endDate);
            }

            sql.append(" ORDER BY a.date, a.timeStart");

            PreparedStatement ps = connection.prepareStatement(sql.toString());

            // Set parameters
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            System.out.println("SQL query executed."); // Debugging statement

            while (rs.next()) {
                Map<String, String> record = new HashMap<>();
                record.put("courseId", rs.getString("courseId"));
                record.put("courseName", rs.getString("courseName"));
                record.put("date", rs.getString("date"));
                record.put("timeStart", rs.getString("timeStart"));
                record.put("timeEnd", rs.getString("timeEnd"));
                record.put("status", rs.getString("status"));
                attendanceRecords.add(record);
            }

            rs.close();
            ps.close();
            System.out.println("Attendance records fetched: " + attendanceRecords.size()); // Debugging statement

            // Prepare the JSON response
            Gson gson = new Gson();
            Map<String, Object> result = new HashMap<>();
            result.put("attendanceRecords", attendanceRecords);
            String jsonResponse = gson.toJson(result);

            // Write the JSON response
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error occurred.\"}");
            response.getWriter().flush();
        }
    }
}
