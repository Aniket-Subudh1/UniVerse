package com.cms.servlet;

// AddFeeServlet.java
import java.io.IOException;

import com.cms.dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/AddFeeServlet")
public class AddFeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String registrationId = request.getParameter("registrationId");
        double academicFee = Double.parseDouble(request.getParameter("academicFee"));
        double hostelFee = Double.parseDouble(request.getParameter("hostelFee"));
        double examFee = Double.parseDouble(request.getParameter("examFee"));


        PreparedStatement stmt = null;

        try(Connection conn = DBConnection.getConnection()) {

            // Assuming a fees table for tracking fees associated with a student
            String query = "INSERT INTO fees (registrationId, academicFee, hostelFee, examFee) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, registrationId);
            stmt.setDouble(2, academicFee);
            stmt.setDouble(3, hostelFee);
            stmt.setDouble(4, examFee);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                response.getWriter().write("Fees added successfully!");
            } else {
                response.getWriter().write("Failed to add fees!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
