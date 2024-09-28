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
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();

        try (Connection connection = DBConnection.getConnection()) {
            String role = null;
            String name = null;
            String registrationId = null;  // Change to String
            String enrolledCourse = null;
            byte[] photo = null;

            // Check Admin
            String query = "SELECT * FROM admins WHERE email = ? AND password = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, email);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        role = "admin";
                        name = rs.getString("name");
                    }
                }
            }

            // Check Student
            if (role == null) {
                query = "SELECT * FROM students WHERE email = ?";
                try (PreparedStatement ps = connection.prepareStatement(query)) {
                    ps.setString(1, email);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next() && BCrypt.checkpw(password, rs.getString("password"))) {
                            role = "student";
                            name = rs.getString("name");
                            registrationId = rs.getString("registrationId");  // Fetch as String
                            enrolledCourse = rs.getString("enrolledCourse");
                            photo = rs.getBytes("photo");
                        }
                    }
                }
            }

            // Check Teacher
            if (role == null) {
                query = "SELECT * FROM teachers WHERE email = ?";
                try (PreparedStatement ps = connection.prepareStatement(query)) {
                    ps.setString(1, email);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next() && BCrypt.checkpw(password, rs.getString("password"))) {
                            role = "teacher";
                            name = rs.getString("name");
                            registrationId = rs.getString("registrationId");  // Fetch as String
                            photo = rs.getBytes("photo");
                        }
                    }
                }
            }

            if (role != null) {
                HttpSession session = request.getSession();
                session.setAttribute("name", name);
                session.setAttribute("email", email);
                session.setAttribute("role", role);

                if ("student".equalsIgnoreCase(role)) {
                    session.setAttribute("registrationId", registrationId);
                    session.setAttribute("enrolledCourse", enrolledCourse);
                    session.setAttribute("photo", photo);
                    response.sendRedirect("student-dashboard.jsp");
                } else if ("teacher".equalsIgnoreCase(role)) {
                    session.setAttribute("registrationId", registrationId);
                    session.setAttribute("photo", photo);
                    response.sendRedirect("teacher-dashboard.jsp");
                } else if ("admin".equalsIgnoreCase(role)) {
                    response.sendRedirect("admin-dashboard.jsp");
                }
            } else {
                response.sendRedirect("home.jsp?error=Invalid email or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("home.jsp?error=Database error");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
