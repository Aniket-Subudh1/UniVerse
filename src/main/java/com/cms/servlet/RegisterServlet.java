package com.cms.servlet;

import com.cms.dao.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.HttpSession;

@WebServlet("/reg")
@MultipartConfig(maxFileSize = 16177215) // Upload file's size up to 16MB
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String dob = request.getParameter("dob");
        String role = request.getParameter("role");
        String registrationId = request.getParameter("registrationId");
        Part photoPart = request.getPart("photo");

        InputStream photoInputStream = null;
        if (photoPart != null && photoPart.getSize() > 0) {
            photoInputStream = photoPart.getInputStream();
        }

        try (Connection connection = DBConnection.getConnection()) {
            System.out.println("Database connection established.");

            String checkQuery;
            String updateQuery;

            if ("student".equalsIgnoreCase(role)) {
                checkQuery = "SELECT * FROM students WHERE registrationId = ?";
                updateQuery = "UPDATE students SET name = ?, email = ?, password = ?, dob = ?, photo = ? WHERE registrationId = ?";
            } else if ("teacher".equalsIgnoreCase(role)) {
                checkQuery = "SELECT * FROM teachers WHERE registrationId = ?";
                updateQuery = "UPDATE teachers SET name = ?, email = ?, password = ?, dob = ?, photo = ? WHERE registrationId = ?";
            } else {
                response.sendRedirect("home.jsp?error=Invalid role");
                return;
            }

            try (PreparedStatement checkPs = connection.prepareStatement(checkQuery)) {
                checkPs.setString(1, registrationId);
                ResultSet rs = checkPs.executeQuery();

                if (rs.next()) {
                    System.out.println("Registration ID found, proceeding with update.");

                    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                    try (PreparedStatement updatePs = connection.prepareStatement(updateQuery)) {
                        updatePs.setString(1, name);
                        updatePs.setString(2, email);
                        updatePs.setString(3, hashedPassword);
                        updatePs.setDate(4, java.sql.Date.valueOf(dob));
                        if (photoInputStream != null) {
                            updatePs.setBlob(5, photoInputStream);
                        } else {
                            updatePs.setNull(5, java.sql.Types.BLOB);
                        }
                        updatePs.setString(6, registrationId);

                        int result = updatePs.executeUpdate();

                        if (result > 0) {
                            System.out.println("Update successful, registration complete.");
                            HttpSession session = request.getSession();
                            session.setAttribute("name", name);
                            session.setAttribute("email", email);
                            session.setAttribute("role", role);
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().write("success");
                        } else {
                            System.out.println("Update failed, registration failed.");
                            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                            response.getWriter().write("failure");
                        }
                    }
                } else {
                    System.out.println("Registration ID not found.");
                    response.sendRedirect("home.jsp?error=Invalid registration ID");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("failure");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
