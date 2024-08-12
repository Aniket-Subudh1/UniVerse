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
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                String role = rs.getString("role");
                session.setAttribute("name", rs.getString("name"));
                session.setAttribute("email", email);
                session.setAttribute("role", role);

                // Redirect based on role
                if ("student".equalsIgnoreCase(role)) {
                    response.sendRedirect("student-dashboard.jsp");
                } else if ("teacher".equalsIgnoreCase(role)) {
                    response.sendRedirect("teacher-dashboard.jsp");
                } else {
                    response.sendRedirect("index.jsp?error=Unknown user role");
                }
            } else {
                response.sendRedirect("index.jsp?error=Invalid email or password");
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
