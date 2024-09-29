package com.cms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.cms.dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateAttendanceServlet")
public class updateAttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String regId = request.getParameter("regId");
        String subjectId = request.getParameter("subjectId");
        String date = request.getParameter("date");
        String status = request.getParameter("status");


        PreparedStatement pstmt = null;

        try(Connection conn = DBConnection.getConnection()){


            // Prepare SQL query
            String sql = "INSERT INTO attendance (regId, subjectId, date, status) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, regId);
            pstmt.setString(2, subjectId);
            pstmt.setString(3, date);
            pstmt.setString(4, status);

            // Execute update
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                response.getWriter().println("Attendance updated successfully!");
            } else {
                response.getWriter().println("Error updating attendance.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Exception occurred: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
