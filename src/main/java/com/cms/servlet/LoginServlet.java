package com.cms.servlet;

import com.cms.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection connection = DBConnection.getConnection()) {
            String query;
            String role = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                // Check if the user is a student
                query = "SELECT * FROM students WHERE email = ? AND password = ?";
                ps = connection.prepareStatement(query);
                ps.setString(1, email);
                ps.setString(2, password);
                rs = ps.executeQuery();

                if (rs.next()) {
                    role = "student";
                } else {
                    // Check if the user is a teacher
                    query = "SELECT * FROM teachers WHERE email = ? AND password = ?";
                    ps = connection.prepareStatement(query);
                    ps.setString(1, email);
                    ps.setString(2, password);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        role = "teacher";
                    }
                }

                if (role != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("name", rs.getString("name"));
                    session.setAttribute("email", email);
                    session.setAttribute("role", role);

                    // Retrieve and store the photo in session
                    byte[] photo = rs.getBytes("photo");
                    session.setAttribute("photo", photo);

                    // Redirect based on role
                    if ("student".equalsIgnoreCase(role)) {
                        response.sendRedirect("student-dashboard.jsp");
                    } else if ("teacher".equalsIgnoreCase(role)) {
                        response.sendRedirect("teacher-dashboard.jsp");
                    }
                } else {
                    response.sendRedirect("index.jsp?error=Invalid email or password");
                }
            } finally {
                // Close ResultSet and PreparedStatement
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=Database error");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}