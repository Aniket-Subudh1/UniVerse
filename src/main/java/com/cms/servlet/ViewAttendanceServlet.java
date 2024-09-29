package com.cms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cms.dao.DBConnection;
import com.cms.model.Attendance;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/ViewAttendanceServlet")
public class ViewAttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String regId = request.getParameter("regId");
        List<Attendance> attendanceList = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try (Connection conn = DBConnection.getConnection()) {
            // Prepare SQL query to fetch attendance
            String sql = "SELECT subjectId, date, status FROM attendance WHERE regId = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, regId);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setSubjectId(rs.getString("subjectId"));
                attendance.setDate(rs.getString("date"));
                attendance.setStatus(rs.getString("status"));
                attendanceList.add(attendance);
            }

            request.setAttribute("attendanceList", attendanceList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("ViewAttendance.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Exception occurred: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
