package com.cms.servlet;

import com.cms.dao.DBConnection;
import org.mindrot.jbcrypt.BCrypt;
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
                // Check if the user is an admin
                query = "SELECT * FROM admins WHERE email = ? AND password = ?";
                ps = connection.prepareStatement(query);
                ps.setString(1, email);
                ps.setString(2, password); // Admin passwords are stored in plain text
                rs = ps.executeQuery();

                if (rs.next()) {
                    role = "admin";
                } else {
                    // Check if the user is a student
                    query = "SELECT * FROM students WHERE email = ?";
                    ps = connection.prepareStatement(query);
                    ps.setString(1, email);
                    rs = ps.executeQuery();

                    if (rs.next() && BCrypt.checkpw(password, rs.getString("password"))) {
                        role = "student";
                    } else {
                        // Check if the user is a teacher
                        query = "SELECT * FROM teachers WHERE email = ?";
                        ps = connection.prepareStatement(query);
                        ps.setString(1, email);
                        rs = ps.executeQuery();

                        if (rs.next() && BCrypt.checkpw(password, rs.getString("password"))) {
                            role = "teacher";
                        }
                    }
                }

                if (role != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("name", rs.getString("name"));
                    session.setAttribute("email", email);
                    session.setAttribute("role", role);

                    // Only retrieve and store the photo if the user is a student or teacher
                    if (!"admin".equalsIgnoreCase(role)) {
                        byte[] photo = rs.getBytes("photo");
                        session.setAttribute("photo", photo);
                    }

                    // Redirect based on role
                    if ("student".equalsIgnoreCase(role)) {
                        response.sendRedirect("student-dashboard.jsp");
                    } else if ("teacher".equalsIgnoreCase(role)) {
                        response.sendRedirect("teacher-dashboard.jsp");
                    } else if ("admin".equalsIgnoreCase(role)) {
                        response.sendRedirect("admin-dashboard.jsp");
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
