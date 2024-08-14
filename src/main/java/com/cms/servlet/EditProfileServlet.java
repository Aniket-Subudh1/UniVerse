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
        String name = request.getParameter("name");
        String dob = request.getParameter("dob");
        Part photoPart = request.getPart("photo");

        System.out.println("Received name: " + name);
        System.out.println("Received dob: " + dob);

        InputStream photoInputStream = null;
        if (photoPart != null && photoPart.getSize() > 0) {
            photoInputStream = photoPart.getInputStream();
            System.out.println("Received file: " + photoPart.getSubmittedFileName());
        }

        try (Connection connection = DBConnection.getConnection()) {
            String query = "UPDATE teachers SET name = ?, dob = ?, photo = ? WHERE email = ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setDate(2, java.sql.Date.valueOf(dob));
            if (photoInputStream != null) {
                ps.setBlob(3, photoInputStream);
            } else {
                ps.setNull(3, java.sql.Types.BLOB);
            }
            ps.setString(4, email);

            int result = ps.executeUpdate();
            System.out.println("SQL Update Result: " + result);

            if (result > 0) {
                session.setAttribute("name", name);
                session.setAttribute("dob", dob);
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
