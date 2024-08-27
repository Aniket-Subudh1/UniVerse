package com.cms.servlet;

import com.cms.dao.DBConnection;
import org.mindrot.jbcrypt.BCrypt;
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
        Part photoPart = request.getPart("photo");

        InputStream photoInputStream = null;
        if (photoPart != null && photoPart.getSize() > 0) {
            photoInputStream = photoPart.getInputStream();
        }

        try (Connection connection = DBConnection.getConnection()) {
            String query;
            if ("student".equalsIgnoreCase(role)) {
                query = "INSERT INTO students (name, email, password, dob, photo) VALUES (?, ?, ?, ?, ?)";
            } else if ("teacher".equalsIgnoreCase(role)) {
                query = "INSERT INTO teachers (name, email, password, dob, photo) VALUES (?, ?, ?, ?, ?)";
            } else {
                response.sendRedirect("index.jsp?error=Invalid role");
                return;
            }

            // Hash the password before storing it
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, hashedPassword);
                ps.setDate(4, java.sql.Date.valueOf(dob));
                if (photoInputStream != null) {
                    ps.setBlob(5, photoInputStream);
                } else {
                    ps.setNull(5, java.sql.Types.BLOB);
                }

                int result = ps.executeUpdate();

                HttpSession session = request.getSession();
                if (result > 0) {
                    session.setAttribute("name", name);
                    session.setAttribute("email", email);
                    session.setAttribute("role", role);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("success");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("failure");
                }
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
