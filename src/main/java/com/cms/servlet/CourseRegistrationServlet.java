package com.cms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.cms.dao.DBConnection.getConnection;

@WebServlet("/registerCourse")
public class CourseRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        String courseName = request.getParameter("courseName");

        String sql = "INSERT INTO course_registration (course_id, course_name) VALUES (?, ?)";
        boolean registrationSuccess = false;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, courseId);
            statement.setString(2, courseName);

            int rowsInserted = statement.executeUpdate();
            registrationSuccess = (rowsInserted > 0);

        } catch (SQLException e) {
            e.printStackTrace();
            registrationSuccess = false;
        }

        // Set the registration result and forward to the JSP
        request.setAttribute("registrationSuccess", registrationSuccess);
        request.getRequestDispatcher("course_registration.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
