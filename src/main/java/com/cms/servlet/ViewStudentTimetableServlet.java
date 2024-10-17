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

    // Static inner class to represent a timetable entry
    public static class TimetableEntry {
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

        // Get studentId and enrolledCourse from session
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("Session is null. Unauthorized access.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
            return;
        }
        String studentId = (String) session.getAttribute("registrationId");
        String enrolledCourse = (String) session.getAttribute("enrolledCourse");

        // Log the session values
        System.out.println("Student ID (Registration ID): " + studentId);
        System.out.println("Enrolled Course: " + enrolledCourse);

        if (studentId != null && enrolledCourse != null) {
            // Fetch timetable entries for the student
            List<TimetableEntry> timetableEntries = getTimetableByStudentIdAndCourse(studentId, enrolledCourse);

            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("timetable", timetableEntries);

            // Log the JSON response
            System.out.println("Timetable Entries: " + timetableEntries);

            // Return JSON response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson(jsonResponse));
        } else {
            System.out.println("Unauthorized access. Missing session attributes.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
        }
    }

    private List<TimetableEntry> getTimetableByStudentIdAndCourse(String studentId, String enrolledCourse) {
        List<TimetableEntry> timetableEntries = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {

            // Fetch courseId associated with studentId from student_course_tracking table
            String courseTrackingSql = "SELECT courseId FROM student_course_tracking WHERE studentId = ?";
            PreparedStatement courseTrackingStatement = connection.prepareStatement(courseTrackingSql);
            courseTrackingStatement.setString(1, studentId);

            // Log the query being executed
            System.out.println("Executing course tracking query: " + courseTrackingStatement.toString());

            ResultSet courseTrackingResult = courseTrackingStatement.executeQuery();

            List<String> courseIds = new ArrayList<>();
            while (courseTrackingResult.next()) {
                courseIds.add(courseTrackingResult.getString("courseId"));
            }
            courseTrackingResult.close();
            courseTrackingStatement.close();

            // Log the found course IDs
            System.out.println("Found course IDs: " + courseIds);

            if (courseIds.isEmpty()) {
                System.out.println("No courses found for student: " + studentId);
                return timetableEntries; // No courses found
            }

            // Build the SQL query to fetch timetable entries
            StringBuilder sql = new StringBuilder(
                    "SELECT c.name AS courseName, t.dayOfWeek, t.timeStart, t.timeEnd " +
                            "FROM timetable t " +
                            "JOIN courses c ON t.courseId = c.courseId " +
                            "WHERE t.enrolledCourse = ? AND t.courseId IN ("
            );

            // Dynamically append courseId placeholders
            for (int i = 0; i < courseIds.size(); i++) {
                sql.append("?");
                if (i < courseIds.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(")");

            PreparedStatement statement = connection.prepareStatement(sql.toString());

            // Set the enrolledCourse parameter
            statement.setString(1, enrolledCourse);

            // Set courseIds in the IN clause
            for (int i = 0; i < courseIds.size(); i++) {
                statement.setString(i + 2, courseIds.get(i)); // Start from index 2 because index 1 is for enrolledCourse
            }

            // For debugging: Build the full SQL query with parameters substituted
            String debugSql = sql.toString();
            debugSql = debugSql.replaceFirst("\\?", "'" + enrolledCourse + "'");
            for (String courseId : courseIds) {
                debugSql = debugSql.replaceFirst("\\?", "'" + courseId + "'");
            }
            // Log the final query being executed
            System.out.println("Executing timetable query: " + debugSql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String courseName = resultSet.getString("courseName");
                String dayOfWeek = resultSet.getString("dayOfWeek");
                String timeStart = resultSet.getString("timeStart");
                String timeEnd = resultSet.getString("timeEnd");

                timetableEntries.add(new TimetableEntry(courseName, dayOfWeek, timeStart, timeEnd));

                // Log each added timetable entry
                System.out.println("Added timetable entry: " + courseName + " " + dayOfWeek + " " + timeStart + "-" + timeEnd);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("An error occurred while fetching the timetable:");
            e.printStackTrace();
        }

        return timetableEntries;
    }
}
