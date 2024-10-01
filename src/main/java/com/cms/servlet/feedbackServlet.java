package com.cms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.cms.dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SubmitFeedbackServlet")
public class feedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection parameters

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve form data
        String teacherId = request.getParameter("teacherId");
        String studentName = request.getParameter("studentName");
        String feedbackText = request.getParameter("feedback");

        PreparedStatement stmt = null;

        try (Connection conn = DBConnection.getConnection()){

            // SQL query to insert feedback into the feedback table
            String sql = "INSERT INTO feedback (teacher_id, student_name, feedback_text) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(teacherId));
            stmt.setString(2, studentName);
            stmt.setString(3, feedbackText);

            // Execute the update
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                out.println("<h2>Thank you! Your feedback has been submitted successfully.</h2>");
            } else {
                out.println("<h2>Error: Feedback submission failed.</h2>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h2>Error: Unable to submit feedback. Please try again later.</h2>");
        } finally {
            // Clean up database resources
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

