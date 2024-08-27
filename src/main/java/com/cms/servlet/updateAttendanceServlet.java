package com.cms.servlet;

import com.cms.dao.AttendanceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/updateAttendance")
public class updateAttendanceServlet extends HttpServlet {

    private AttendanceDAO attendanceDAO;

    public void init() {
        attendanceDAO = new AttendanceDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        try {
            boolean isUpdated = attendanceDAO.updateAttendance(studentId, subjectId, date, status);
            if (isUpdated) {
                request.setAttribute("message", "Attendance updated successfully.");
            } else {
                request.setAttribute("message", "No matching record found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error updating attendance.");
        }

        request.getRequestDispatcher("updateAttendance..jsp").forward(request, response);
    }
}
