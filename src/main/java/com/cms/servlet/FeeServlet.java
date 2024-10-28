// FeeServlet.java
package com.cms.servlet;

import com.cms.dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/viewFees")
public class FeeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String registrationId = (String) session.getAttribute("registrationId");
        String feeType = request.getParameter("feeType");

        Double feeAmount = null;

        if (registrationId != null && feeType != null && !feeType.isEmpty()) {
            try (Connection conn = DBConnection.getConnection()) {
                String query = "SELECT academicFee, hostelFee, examFee FROM fees WHERE registrationId = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, registrationId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    switch (feeType) {
                        case "academic":
                            feeAmount = rs.getDouble("academicFee");
                            break;
                        case "hostel":
                            feeAmount = rs.getDouble("hostelFee");
                            break;
                        case "exam":
                            feeAmount = rs.getDouble("examFee");
                            break;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("feeAmount", feeAmount);
        request.getRequestDispatcher("viewFees.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
