package com.cms.servlet;

import com.cms.dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/uploadNotes")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
        maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class UploadNotesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "uploaded_notes";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve teacher details and file
        HttpSession session = request.getSession(false);
        String teacherId = (String) session.getAttribute("registrationId");
        String teacherName = (String) session.getAttribute("name"); // Assuming "name" is stored in session
        String subjectName = request.getParameter("subjectName");
        String topicName = request.getParameter("topicName");
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">

        // Check if required parameters are available
        if (teacherId == null || teacherName == null || subjectName == null || topicName == null || filePart == null) {
            response.getWriter().write("Error: Missing required parameters.");
            return;
        }

        // Get file name and validate file type
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (!(fileExtension.equals("pdf") || fileExtension.equals("docx"))) {
            response.getWriter().write("Error: Only PDF and DOCX files are allowed.");
            return;
        }

        // Define the upload directory
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        // Save the file to the upload directory
        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        // Save metadata to the database
        try (Connection connection = DBConnection.getConnection()) {
            String insertQuery = "INSERT INTO notes (teacherId, teacherName, subjectName, topicName, filePath) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(insertQuery);
            ps.setString(1, teacherId);
            ps.setString(2, teacherName);
            ps.setString(3, subjectName);
            ps.setString(4, topicName);
            ps.setString(5, filePath);
            int rowsAffected = ps.executeUpdate();
            ps.close();

            if (rowsAffected > 0) {
                response.getWriter().write("Notes uploaded successfully.");
            } else {
                response.getWriter().write("Error uploading notes.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Database error: " + e.getMessage());
 }
}
}
