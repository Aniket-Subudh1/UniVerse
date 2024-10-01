package com.cms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cms.dao.DBConnection;
import com.cms.model.feedback;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RetrieveFeedbackServlet")
public class  ViewfeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String teacherId = request.getParameter("teacherId");
        List<feedback> feedbackList = new ArrayList<>();

        String sql = "SELECT student_name, feedback_text FROM feedback WHERE teacher_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(teacherId));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                feedback feedback = new feedback();
                feedback.setStudentName(rs.getString("student_name"));
                feedback.setFeedbackText(rs.getString("feedback_text"));
                feedbackList.add(feedback);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while retrieving feedback.");
        }

        // Set feedback list in the request attribute
        request.setAttribute("feedbackList", feedbackList);
        // Forward to the JSP page to display feedback
        request.getRequestDispatcher("Viewfeedback.jsp").forward(request, response);
    }
}
