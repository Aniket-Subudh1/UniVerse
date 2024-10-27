package com.cms.servlet;

import com.cms.dao.DBConnection;
import com.cms.model.Grade;
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
import java.util.List;

@WebServlet("/grades")
public class GradeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String registrationId = (String) session.getAttribute("registrationId"); // Fetch studentId from session
        String courseId = request.getParameter("courseId");
        List<Grade> grades = new ArrayList<>();

        if (registrationId != null && courseId != null && !courseId.isEmpty()) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "SELECT * FROM grades WHERE studentId = ? AND courseId = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, registrationId); // Use registrationId as studentId
                    stmt.setString(2, courseId);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        grades.add(new Grade(
                                rs.getInt("id"),
                                rs.getString("studentId"),
                                rs.getString("courseId"),
                                rs.getString("grade").charAt(0),
                                rs.getTimestamp("created_at")
                        ));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("grades", grades);
        request.getRequestDispatcher("grades.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Optionally handle POST requests if needed
    }
}
