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

@WebServlet("/viewTeacherTimetable")
public class ViewTeacherTimetableServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Class to represent timetable entry
    public class TimetableEntry {
        private String enrolledCourse;
        private String courseName;
        private String dayOfWeek;
        private String timeStart;
        private String timeEnd;

        public TimetableEntry(String enrolledCourse, String courseName, String dayOfWeek, String timeStart, String timeEnd) {
            this.enrolledCourse = enrolledCourse;
            this.courseName = courseName;
            this.dayOfWeek = dayOfWeek;
            this.timeStart = timeStart;
            this.timeEnd = timeEnd;
        }

        public String getEnrolledCourse() {
            return enrolledCourse;
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
        // Get registrationId from session
        HttpSession session = request.getSession(false);
        String registrationId = (String) session.getAttribute("registrationId");

        if (registrationId != null) {
            // Fetch timetable entries for the teacher
            List<TimetableEntry> timetableEntries = getTimetableByTeacherId(registrationId);

            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("timetable", timetableEntries);

            // Return JSON response
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(jsonResponse));
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
        }
    }

    private List<TimetableEntry> getTimetableByTeacherId(String registrationId) {
        List<TimetableEntry> timetableEntries = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            // SQL query to fetch timetable entries including enrolledCourse and courseName based on the teacher's registrationId
            String sql = "SELECT t.enrolledCourse, c.name AS courseName, t.dayOfWeek, t.timeStart, t.timeEnd " +
                    "FROM timetable t " +
                    "JOIN courses c ON t.courseId = c.courseId " +
                    "WHERE t.teacherId = ?";  // Match teacherId from timetable with registrationId

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, registrationId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String enrolledCourse = resultSet.getString("enrolledCourse");
                String courseName = resultSet.getString("courseName");
                String dayOfWeek = resultSet.getString("dayOfWeek");
                String timeStart = resultSet.getString("timeStart");
                String timeEnd = resultSet.getString("timeEnd");

                timetableEntries.add(new TimetableEntry(enrolledCourse, courseName, dayOfWeek, timeStart, timeEnd));
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return timetableEntries;
    }
}
