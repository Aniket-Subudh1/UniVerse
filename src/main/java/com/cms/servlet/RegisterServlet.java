package com.cms.servlet;

import com.cms.util.DBConnection;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.HttpSession;

@WebServlet("/register")
@MultipartConfig(maxFileSize = 16177215)
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String dob = request.getParameter("dob");
        String role = request.getParameter("role"); // Added role
        Part photoPart = request.getPart("photo");

        System.out.println("Received name: " + name);
        System.out.println("Received email: " + email);
        System.out.println("Received password: " + password);
        System.out.println("Received dob: " + dob);
        System.out.println("Received role: " + role);

        InputStream photoInputStream = null;
        if (photoPart != null) {
            photoInputStream = photoPart.getInputStream();
            System.out.println("Received file: " + photoPart.getSubmittedFileName());
        }

        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO users (name, email, password, dob, role, photo) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setDate(4, java.sql.Date.valueOf(dob));
            ps.setString(5, role); // Set role in the database
            if (photoInputStream != null) {
                ps.setBlob(6, photoInputStream);
            } else {
                ps.setNull(6, java.sql.Types.BLOB); // Handle null case for photo
            }

            int result = ps.executeUpdate();
            System.out.println("SQL Update Result: " + result);

            HttpSession session = request.getSession();
            if (result > 0) {
                session.setAttribute("name", name);
                session.setAttribute("email", email);
                session.setAttribute("role", role); // Add role to the session
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("success");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("failure");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("failure");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
