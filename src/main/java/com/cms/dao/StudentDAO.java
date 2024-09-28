package com.cms.dao;

import com.cms.model.Student;
import com.cms.dao.DBConnection;
import org.mindrot.jbcrypt.BCrypt; // Import BCrypt for password hashing

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class  StudentDAO {

    public boolean registerStudent(Student student) {
        String query = "INSERT INTO students (name, email, password, dob, photo) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            // Hash the password before storing it
            String hashedPassword = BCrypt.hashpw(student.getPassword(), BCrypt.gensalt());

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, hashedPassword); // Store the hashed password
            ps.setDate(4, java.sql.Date.valueOf(student.getDob()));

            if (student.getPhoto() != null) {
                ps.setBlob(5, student.getPhoto());
            } else {
                ps.setNull(5, java.sql.Types.BLOB);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Student getStudentByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM students WHERE email = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedPasswordHash = rs.getString("password");

                // Verify the password
                if (BCrypt.checkpw(password, storedPasswordHash)) {
                    Student student = new Student();
                    student.setId(rs.getInt("studentId"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    student.setPassword(storedPasswordHash); // Store the hashed password
                    student.setDob(rs.getDate("dob").toLocalDate());

                    // Convert Blob to InputStream or byte array
                    if (rs.getBlob("photo") != null) {
                        InputStream photoStream = rs.getBlob("photo").getBinaryStream();
                        student.setPhoto(photoStream);
                    } else {
                        student.setPhoto(null); // Handle case where photo is null
                    }
                    return student;
                } else {
                    // Password didn't match
                    System.out.println("Password mismatch for email: " + email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
