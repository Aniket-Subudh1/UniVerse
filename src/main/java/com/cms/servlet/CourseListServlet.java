package com.cms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.cms.model.Course;

import static com.cms.dao.DBConnection.getConnection;

@WebServlet("/courseList")
public class CourseListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Course> courseList = new ArrayList<>();

        String sql = "SELECT course_id, course_name FROM course_registration";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String courseId = resultSet.getString("course_id");
                String courseName = resultSet.getString("course_name");

                Course course = new Course(courseId, courseName);
                courseList.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set the course list in the request attribute and forward to JSP
        request.setAttribute("courseList", courseList);
        request.getRequestDispatcher("course-list.jsp").forward(request, response);
    }
}
