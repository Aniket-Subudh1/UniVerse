package com.cms.servlet;

import com.cms.dao.DBConnection;
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

@WebServlet("/editProfile")
@MultipartConfig(maxFileSize = 16177215) // Upload file's size up to 16MB
public class EditProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String email = (String) session.getAttribute("email");
        String role = (String) session.getAttribute("role");
        String name = request.getParameter("name");
        String dob = request.getParameter("dob");
        Part photoPart = request.getPart("photo");

        InputStream photoInputStream = null;
        if (photoPart != null && photoPart.getSize() > 0) {
            photoInputStream = photoPart.getInputStream();
        }

        // Start building the query based on provided fields
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("UPDATE ");
        if ("teacher".equalsIgnoreCase(role)) {
            queryBuilder.append("teachers");
        } else if ("student".equalsIgnoreCase(role)) {
            queryBuilder.append("students");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid role");
            return;
        }
        queryBuilder.append(" SET ");

        boolean firstField = true;

        if (name != null && !name.isEmpty()) {
            queryBuilder.append("name = ?");
            firstField = false;
        }

        if (dob != null && !dob.isEmpty()) {
            if (!firstField) queryBuilder.append(", ");
            queryBuilder.append("dob = ?");
            firstField = false;
        }

        if (photoInputStream != null) {
            if (!firstField) queryBuilder.append(", ");
            queryBuilder.append("photo = ?");
        }

        queryBuilder.append(" WHERE email = ?");

        // Prepare the statement
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(queryBuilder.toString());
            int parameterIndex = 1;

            if (name != null && !name.isEmpty()) {
                ps.setString(parameterIndex++, name);
            }

            if (dob != null && !dob.isEmpty()) {
                ps.setDate(parameterIndex++, java.sql.Date.valueOf(dob));
            }

            if (photoInputStream != null) {
                ps.setBlob(parameterIndex++, photoInputStream);
            }

            ps.setString(parameterIndex, email);  // Last parameter is always the email

            int result = ps.executeUpdate();
            System.out.println("SQL Update Result: " + result);

            if (result > 0) {
                if (name != null && !name.isEmpty()) {
                    session.setAttribute("name", name);
                }
                if (dob != null && !dob.isEmpty()) {
                    session.setAttribute("dob", dob);
                }
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
