
package com.cms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cms.dao.DBConnection;
import com.cms.model.ViewAssignedSub;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/teacherDashboard")
public class ViewAssignSubServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String teacherId = request.getParameter("teacherId"); // Get teacherId from request

        if (teacherId != null && !teacherId.isEmpty()) {
            List<ViewAssignedSub> assignedCourses = getCoursesByTeacherId(teacherId);

            if (!assignedCourses.isEmpty()) {
                request.setAttribute("assignedCourses", assignedCourses);
            } else {
                request.setAttribute("errorMessage", "No courses found for the entered Teacher ID.");
            }
        } else {
            request.setAttribute("errorMessage", "Please enter a valid Teacher ID.");
        }

        request.getRequestDispatcher("ViewAssignSub.jsp").forward(request, response);
    }

    // Fetch the courses assigned to the teacher by teacherId
    private List<ViewAssignedSub> getCoursesByTeacherId(String teacherId) {
        List<ViewAssignedSub> courses = new ArrayList<>();

        try(Connection connection = DBConnection.getConnection()) {


            String sql = "SELECT course_id, course_name FROM assigncourses WHERE teacherId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, teacherId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ViewAssignedSub course = new ViewAssignedSub();
                course.setCourseId(resultSet.getString("course_id"));
                course.setCourseName(resultSet.getString("course_name"));
                courses.add(course);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return courses;
    }
}
