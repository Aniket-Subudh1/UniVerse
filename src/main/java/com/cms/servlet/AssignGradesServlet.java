package com.cms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cms.dao.DBConnection;
import com.cms.model.ViewGrades;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/viewGrades")
class ViewGradesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId"); // Get studentId from request

        if (studentId != null && !studentId.isEmpty()) {
            List<ViewGrades> assignedGrades = getGradesByStudentId(studentId);

            if (!assignedGrades.isEmpty()) {
                request.setAttribute("assignedGrades", assignedGrades);
            } else {
                request.setAttribute("errorMessage", "No grades found for the entered Student ID.");
            }
        } else {
            request.setAttribute("errorMessage", "Please enter a valid Student ID.");
        }

        request.getRequestDispatcher("ViewGrades.jsp").forward(request, response);
    }

    // Fetch the grades assigned to the student by studentId
    private List<ViewGrades> getGradesByStudentId(String studentId) {
        List<ViewGrades> grades = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection()) {

            String sql = "SELECT subject_code, grade FROM student_grades WHERE student_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ViewGrades grade = new ViewGrades(studentId, resultSet.getString("subject_code"), resultSet.getString("grade"));
                grade.setSubjectCode(resultSet.getString("subject_code"));
                grade.setGrade(resultSet.getString("grade"));
                grades.add(grade);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return grades;
    }
}
