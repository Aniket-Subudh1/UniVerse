package com.cms.servlet;

import com.cms.dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/uploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,       // 10MB
        maxRequestSize = 1024 * 1024 * 50)    // 50MB
public class notesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Directory where uploaded files will be saved
    private static final String SAVE_DIR = "uploadFiles";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        // Directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;

        // Creates the save directory if it does not exist
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        // Retrieves the file part from the form
        Part filePart = request.getPart("file");
        String fileName = extractFileName(filePart);
        String filePath = savePath + File.separator + fileName;

        // Save the file on the server
        filePart.write(filePath);

        // Save file information in the database
        try(Connection conn = DBConnection.getConnection()) {


            String sql = "INSERT INTO notes (filename, filetype, filepath) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, fileName);
            statement.setString(2, filePart.getContentType());
            statement.setString(3, filePath);
            statement.executeUpdate();

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.getWriter().println("File " + fileName + " uploaded and saved in the database successfully.");
    }

    // Extracts file name from HTTP header content-disposition
    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}

