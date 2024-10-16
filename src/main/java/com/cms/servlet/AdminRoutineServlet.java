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
import java.util.*;

@WebServlet("/adminRoutine")
public class AdminRoutineServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Class to represent a teacher with assigned courses
    public class Teacher {
        private String registrationId;
        private String name;
        private String photo;
        private List<String> assignedCourses;

        public Teacher(String registrationId, String name, String photo, List<String> assignedCourses) {
            this.registrationId = registrationId;
            this.name = name;
            this.photo = photo;
            this.assignedCourses = assignedCourses;
        }
    }

    // Class to represent a course
    public class Course {
        private String courseId;
        private String name;

        public Course(String courseId, String name) {
            this.courseId = courseId;
            this.name = name;
        }
    }

    // Class to represent a timetable entry
    public class TimetableEntry {
        private int id;
        private String enrolledCourse;
        private String dayOfWeek;
        private String timeStart;
        private String timeEnd;
        private String courseName;
        private String teacherName;

        public TimetableEntry(int id, String enrolledCourse, String dayOfWeek, String timeStart, String timeEnd, String courseName, String teacherName) {
            this.id = id;
            this.enrolledCourse = enrolledCourse;
            this.dayOfWeek = dayOfWeek;
            this.timeStart = timeStart;
            this.timeEnd = timeEnd;
            this.courseName = courseName;
            this.teacherName = teacherName;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("loadTeachers".equals(action)) {
            loadTeachers(response);
        } else if ("loadCourses".equals(action)) {
            loadCourses(response);
        } else if ("loadTimetable".equals(action)) {
            loadTimetable(request,response);
        }
    }

    private void loadTeachers(HttpServletResponse response) throws IOException {
        List<Teacher> teachers = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            // Query to get teachers with their assigned courses
            String sql = "SELECT t.registrationId, t.name, t.photo, c.name AS courseName " +
                    "FROM teachers t " +
                    "LEFT JOIN assigncourses ac ON t.registrationId = ac.registrationId " +
                    "LEFT JOIN courses c ON ac.courseId = c.courseId " +
                    "ORDER BY t.registrationId, c.name";  // Ordered to group teachers with their courses

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Map<String, Teacher> teacherMap = new HashMap<>();

            while (rs.next()) {
                String registrationId = rs.getString("registrationId");
                String name = rs.getString("name");
                byte[] photoBytes = rs.getBytes("photo");
                String photoBase64 = (photoBytes != null) ? Base64.getEncoder().encodeToString(photoBytes) : null;
                String courseName = rs.getString("courseName");

                // If the teacher already exists in the map, append the course name
                if (teacherMap.containsKey(registrationId)) {
                    Teacher teacher = teacherMap.get(registrationId);
                    if (courseName != null) {
                        teacher.assignedCourses.add(courseName);
                    }
                } else {
                    // Create a new teacher object if it doesn't exist in the map
                    List<String> courses = new ArrayList<>();
                    if (courseName != null) {
                        courses.add(courseName);
                    }
                    Teacher teacher = new Teacher(registrationId, name, photoBase64, courses);
                    teacherMap.put(registrationId, teacher);
                }
            }

            teachers.addAll(teacherMap.values());

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Send JSON response
        sendJsonResponse(response, Map.of("teachers", teachers));
    }


    private void loadCourses(HttpServletResponse response) throws IOException {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT courseId, name FROM courses";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String courseId = rs.getString("courseId");
                String name = rs.getString("name");
                courses.add(new Course(courseId, name));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendJsonResponse(response, Map.of("courses", courses));
    }
    // Load the timetable entries with request and response
    private void loadTimetable(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<TimetableEntry> timetable = new ArrayList<>();

        // Capture filters from the request parameters
        String courseIdFilter = request.getParameter("courseId");


        try (Connection connection = DBConnection.getConnection()) {
            StringBuilder sql = new StringBuilder(
                    "SELECT t.id, t.enrolledCourse, t.dayOfWeek, t.timeStart, t.timeEnd, c.name AS courseName, tea.name AS teacherName " +
                            "FROM timetable t " +
                            "JOIN courses c ON t.courseId = c.courseId " +
                            "JOIN teachers tea ON t.teacherId = tea.registrationId WHERE 1=1"
            );

            List<Object> params = new ArrayList<>();

            // Apply filters if they are provided
            if (courseIdFilter != null && !courseIdFilter.isEmpty()) {
                sql.append(" AND t.courseId = ?");
                params.add(courseIdFilter);
            }


            PreparedStatement ps = connection.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String enrolledCourse = rs.getString("enrolledCourse");
                String dayOfWeek = rs.getString("dayOfWeek");
                String timeStart = rs.getString("timeStart");
                String timeEnd = rs.getString("timeEnd");
                String courseName = rs.getString("courseName");
                String teacherName = rs.getString("teacherName");

                timetable.add(new TimetableEntry(id, enrolledCourse, dayOfWeek, timeStart, timeEnd, courseName, teacherName));
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sendJsonResponse(response, Map.of("timetable", timetable));
    }

    // Utility method to send JSON response
    private void sendJsonResponse(HttpServletResponse response, Map<String, Object> data) throws IOException {
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(data);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String enrolledCourse = request.getParameter("enrolledCourse");
        String dayOfWeek = request.getParameter("dayOfWeek");
        String timeStart = request.getParameter("timeStart");
        String timeEnd = request.getParameter("timeEnd");
        String teacherId = request.getParameter("teacherId");
        String courseId = request.getParameter("courseId");

        try (Connection connection = DBConnection.getConnection()) {
            // Step 1: Check if there's already a schedule conflict for the same teacher
            String checkSql = "SELECT COUNT(*) FROM timetable WHERE teacherId = ? AND dayOfWeek = ? AND timeStart = ? AND timeEnd = ?";
            PreparedStatement checkPs = connection.prepareStatement(checkSql);
            checkPs.setString(1, teacherId);
            checkPs.setString(2, dayOfWeek);
            checkPs.setString(3, timeStart);
            checkPs.setString(4, timeEnd);

            ResultSet rs = checkPs.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Conflict found, don't insert the new schedule
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Schedule conflict: The teacher is already assigned at this time.")));
                return;
            }

            // Step 2: Insert the new routine if no conflict
            String sql = "INSERT INTO timetable (enrolledCourse, dayOfWeek, timeStart, timeEnd, teacherId, courseId) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, enrolledCourse);
            ps.setString(2, dayOfWeek);
            ps.setString(3, timeStart);
            ps.setString(4, timeEnd);
            ps.setString(5, teacherId);
            ps.setString(6, courseId);

            ps.executeUpdate();
            ps.close();

            // Redirect or send a success response
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "success", "message", "Routine created successfully")));
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Error creating routine")));
        }
    }
}