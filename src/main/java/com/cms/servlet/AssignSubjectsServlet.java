package com.cms.servlet;

import com.cms.dao.CourseDAO;
import com.cms.model.Course1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/AssignSubjectsServlet")
public class AssignSubjectsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        String courseName = request.getParameter("courseName");

        // Create a Course object
        Course1 course = new Course1(courseId, courseName, teacherId);

        // Save course assignment using the service layer
        CourseDAO courseService = new CourseDAO();
        boolean isAssigned = courseService.assignCourseToTeacher(course);

        // Generate dynamic response without redirect
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("<html><body>");

        if (isAssigned) {
            // JavaScript pop-up for successful assignment
            response.getWriter().write("<script>alert('Assignment Successful!');</script>");
        } else {
            // JavaScript pop-up for assignment failure
            response.getWriter().write("<script>alert('Assignment Failed! Please try again.');</script>");
        }


    }
}
