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

@WebServlet("/viewStudentTimetable")
public class ViewStudentTimetableServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Class to represent a timetable entry
    public class TimetableEntry {
        private String courseName;
        private String dayOfWeek;
        private String timeStart;
        private String timeEnd;

        public TimetableEntry(String courseName, String dayOfWeek, String timeStart, String timeEnd) {
            this.courseName = courseName;
            this.dayOfWeek = dayOfWeek;
            this.timeStart = timeStart;
            this.timeEnd = timeEnd;
        }

        public String getCourseName() {
            return courseName;
        }

        public String getDayOfWeek() {
            return dayOfWeek;
        }

        public String getTimeStart() {
            return timeStart;
        }

        public String getTimeEnd() {
            return timeEnd;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get the student's registrationId from the session
        HttpSession session = request.getSession(false);
        String registrationId = (String) session.getAttribute("registrationId");

        if (registrationId != null) {
            // Use the registrationId directly as studentId in student_course_tracking
            List<TimetableEntry> timetableEntries = getTimetableByRegistrationId(registrationId);

            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("timetable", timetableEntries);

            // Return JSON response
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(jsonResponse));
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
        }
    }

    // Method to fetch the timetable for the student's registered courses
    private List<TimetableEntry> getTimetableByRegistrationId(String registrationId) {
        List<TimetableEntry> timetableEntries = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            // SQL query to get the timetable by matching registrationId with studentId in student_course_tracking
            String sql = "SELECT c.name AS courseName, t.dayOfWeek, t.timeStart, t.timeEnd " +
                    "FROM students s " +
                    "JOIN student_course_tracking sct ON s.registrationId = sct.studentId " +
                    "JOIN courses c ON sct.courseId = c.courseId " +
                    "JOIN timetable t ON t.courseId = c.courseId " +
                    "WHERE s.registrationId = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, registrationId);  // Use the registrationId as studentId in student_course_tracking
            ResultSet resultSet = statement.executeQuery();
            System.out.println(resultSet);
            while (resultSet.next()) {
                String courseName = resultSet.getString("courseName");
                String dayOfWeek = resultSet.getString("dayOfWeek");
                String timeStart = resultSet.getString("timeStart");
                String timeEnd = resultSet.getString("timeEnd");

                timetableEntries.add(new TimetableEntry(courseName, dayOfWeek, timeStart, timeEnd));
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return timetableEntries;
    }
}
