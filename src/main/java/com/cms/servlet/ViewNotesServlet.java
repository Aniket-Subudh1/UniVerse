package com.cms.servlet;

import com.cms.dao.DBConnection;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/viewNotes")
public class ViewNotesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Inner class to represent a note entry
    public class NoteEntry {
        private String teacherName;
        private String subjectName;
        private String topicName;
        private String filePath;

        public NoteEntry(String teacherName, String subjectName, String topicName, String filePath) {
            this.teacherName = teacherName;
            this.subjectName = subjectName;
            this.topicName = topicName;
            this.filePath = filePath;
        }

        public String getTeacherName() { return teacherName; }
        public String getSubjectName() { return subjectName; }
        public String getTopicName() { return topicName; }
        public String getFilePath() { return filePath; }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("download".equals(action)) {
            // Handle file download
            String filePath = request.getParameter("filePath");
            handleFileDownload(filePath, response);
        } else {
            // Default action: List all notes
            listNotes(response);
        }
    }

    private void listNotes(HttpServletResponse response) throws IOException {
        List<NoteEntry> notes = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT teacherName, subjectName, topicName, filePath FROM notes";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String teacherName = rs.getString("teacherName");
                String subjectName = rs.getString("subjectName");
                String topicName = rs.getString("topicName");
                String filePath = rs.getString("filePath");
                notes.add(new NoteEntry(teacherName, subjectName, topicName, filePath));
            }
            rs.close();
            ps.close();

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "success", "notes", notes)));

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(Map.of("status", "error", "message", "Error fetching notes")));
        }
    }

    private void handleFileDownload(String filePath, HttpServletResponse response) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            response.getWriter().write("Error: File path not specified.");
            return;
        }

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            response.getWriter().write("Error: File not found.");
            return;
        }

        // Set response headers
        response.setContentType(getServletContext().getMimeType(file.getName()));
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        // Stream the file to the response
        try (FileInputStream in = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
 }
}
}
