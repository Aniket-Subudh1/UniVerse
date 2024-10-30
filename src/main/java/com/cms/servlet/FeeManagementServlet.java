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
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

@WebServlet("/FeeManagementServlet")
public class FeeManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Inner class to represent request data
    public static class RequestData {
        private String registrationId;
        private String enrolledCourse;
        private List<String> registrationIds;
        private BigDecimal academicFee;
        private BigDecimal hostelFee;
        private BigDecimal examFee;
        private BigDecimal amountPaid;

        // Getters and setters
        public String getRegistrationId() {
            return registrationId;
        }

        public void setRegistrationId(String registrationId) {
            this.registrationId = registrationId;
        }

        public String getEnrolledCourse() {
            return enrolledCourse;
        }

        public void setEnrolledCourse(String enrolledCourse) {
            this.enrolledCourse = enrolledCourse;
        }

        public List<String> getRegistrationIds() {
            return registrationIds;
        }

        public void setRegistrationIds(List<String> registrationIds) {
            this.registrationIds = registrationIds;
        }

        public BigDecimal getAcademicFee() {
            return academicFee;
        }

        public void setAcademicFee(BigDecimal academicFee) {
            this.academicFee = academicFee;
        }

        public BigDecimal getHostelFee() {
            return hostelFee;
        }

        public void setHostelFee(BigDecimal hostelFee) {
            this.hostelFee = hostelFee;
        }

        public BigDecimal getExamFee() {
            return examFee;
        }

        public void setExamFee(BigDecimal examFee) {
            this.examFee = examFee;
        }

        public BigDecimal getAmountPaid() {
            return amountPaid;
        }

        public void setAmountPaid(BigDecimal amountPaid) {
            this.amountPaid = amountPaid;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        response.setContentType("application/json");

        try (Connection conn = DBConnection.getConnection()) {
            if ("loadDropdowns".equals(action)) {
                Map<String, Object> data = new HashMap<>();
                data.put("registrationIds", getRegistrationIds(conn));
                data.put("courses", getEnrolledCourses(conn));
                response.getWriter().write(new Gson().toJson(data));
            } else if ("getFees".equals(action)) {
                String registrationId = request.getParameter("registrationId");
                Map<String, Object> feeDetails = getFeeDetails(conn, registrationId);
                response.getWriter().write(new Gson().toJson(Map.of("status", "success", "fees", feeDetails)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Database error: " + e.getMessage())));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();

        try (BufferedReader reader = request.getReader()) {
            RequestData requestData = gson.fromJson(reader, RequestData.class);

            String registrationId = requestData.getRegistrationId();
            String enrolledCourse = requestData.getEnrolledCourse();
            List<String> registrationIds = requestData.getRegistrationIds();
            BigDecimal academicFee = requestData.getAcademicFee();
            BigDecimal hostelFee = requestData.getHostelFee();
            BigDecimal examFee = requestData.getExamFee();
            BigDecimal amountPaid = requestData.getAmountPaid();

            try (Connection conn = DBConnection.getConnection()) {
                if (registrationId != null && !registrationId.isEmpty()) {
                    // Update fees for single student
                    updateSingleStudentFees(conn, registrationId, academicFee, hostelFee, examFee, amountPaid);
                } else if (registrationIds != null && !registrationIds.isEmpty()) {
                    // Update fees for multiple students by registration IDs
                    for (String regId : registrationIds) {
                        updateSingleStudentFees(conn, regId, academicFee, hostelFee, examFee, amountPaid);
                    }
                } else if (enrolledCourse != null && !enrolledCourse.isEmpty()) {
                    // Update fees for all students enrolled in a course
                    updateFeesForCourse(conn, enrolledCourse, academicFee, hostelFee, examFee, amountPaid);
                } else {
                    response.getWriter().write(gson.toJson(Map.of("status", "error", "message", "Invalid parameters")));
                    return;
                }

                response.getWriter().write(gson.toJson(Map.of("status", "success", "message", "Fees updated successfully")));

            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write(gson.toJson(Map.of("status", "error", "message", "Database error: " + e.getMessage())));
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Error parsing JSON data: " + e.getMessage())));
        }
    }

    private List<String> getRegistrationIds(Connection conn) throws SQLException {
        List<String> registrationIds = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT registrationId FROM students")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                registrationIds.add(rs.getString("registrationId"));
            }
        }
        return registrationIds;
    }

    private List<String> getEnrolledCourses(Connection conn) throws SQLException {
        List<String> courses = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT enrolledCourse FROM students")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(rs.getString("enrolledCourse"));
            }
        }
        return courses;
    }

    private Map<String, Object> getFeeDetails(Connection conn, String registrationId) throws SQLException {
        String query = "SELECT registrationId, academicFee, hostelFee, examFee, totalFees, amountPaid, balance FROM fees WHERE registrationId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, registrationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Map<String, Object> feeDetails = new HashMap<>();
                feeDetails.put("registrationId", rs.getString("registrationId"));
                feeDetails.put("academicFee", rs.getBigDecimal("academicFee"));
                feeDetails.put("hostelFee", rs.getBigDecimal("hostelFee"));
                feeDetails.put("examFee", rs.getBigDecimal("examFee"));
                feeDetails.put("totalFees", rs.getBigDecimal("totalFees"));
                feeDetails.put("amountPaid", rs.getBigDecimal("amountPaid"));
                feeDetails.put("balance", rs.getBigDecimal("balance"));
                return feeDetails;
            } else {
                // Return empty map if no data is found
                return Map.of();
            }
        }
    }

    private void updateSingleStudentFees(Connection conn, String registrationId, BigDecimal academicFee, BigDecimal hostelFee, BigDecimal examFee, BigDecimal amountPaid) throws SQLException {
        String query = "INSERT INTO fees (registrationId, academicFee, hostelFee, examFee, amountPaid) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE academicFee = VALUES(academicFee), hostelFee = VALUES(hostelFee), examFee = VALUES(examFee), amountPaid = VALUES(amountPaid)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, registrationId);
            stmt.setBigDecimal(2, academicFee != null ? academicFee : BigDecimal.ZERO);
            stmt.setBigDecimal(3, hostelFee != null ? hostelFee : BigDecimal.ZERO);
            stmt.setBigDecimal(4, examFee != null ? examFee : BigDecimal.ZERO);
            stmt.setBigDecimal(5, amountPaid != null ? amountPaid : BigDecimal.ZERO);
            stmt.executeUpdate();
        }
    }

    private void updateFeesForCourse(Connection conn, String enrolledCourse, BigDecimal academicFee, BigDecimal hostelFee, BigDecimal examFee, BigDecimal amountPaid) throws SQLException {
        String query = "SELECT registrationId FROM students WHERE enrolledCourse = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, enrolledCourse);
            ResultSet rs = stmt.executeQuery();
            List<String> registrationIds = new ArrayList<>();
            while (rs.next()) {
                registrationIds.add(rs.getString("registrationId"));
            }

            for (String regId : registrationIds) {
                updateSingleStudentFees(conn, regId, academicFee, hostelFee, examFee, amountPaid);
            }
        }
    }
}