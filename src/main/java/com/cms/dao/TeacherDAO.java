package com.cms.dao;

import com.cms.model.Teacher;
import com.cms.dao.DBConnection;
import org.mindrot.jbcrypt.BCrypt; // Ensure BCrypt is imported

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherDAO {

    public boolean registerTeacher(Teacher teacher) {
        String query = "INSERT INTO teachers (name, email, password, dob, photo) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            // Hash the password before storing it
            String hashedPassword = BCrypt.hashpw(teacher.getPassword(), BCrypt.gensalt());

            ps.setString(1, teacher.getName());
            ps.setString(2, teacher.getEmail());
            ps.setString(3, hashedPassword);  // Store the hashed password
            ps.setDate(4, java.sql.Date.valueOf(teacher.getDob()));

            if (teacher.getPhoto() != null) {
                ps.setBlob(5, teacher.getPhoto());
            } else {
                ps.setNull(5, java.sql.Types.BLOB);
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Teacher getTeacherByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM teachers WHERE email = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedPasswordHash = rs.getString("password");

                // Verify the password
                if (BCrypt.checkpw(password, storedPasswordHash)) {
                    Teacher teacher = new Teacher();
                    teacher.setId(rs.getInt("teacherId"));
                    teacher.setName(rs.getString("name"));
                    teacher.setEmail(rs.getString("email"));
                    teacher.setPassword(storedPasswordHash);  // Store the hashed password
                    teacher.setDob(rs.getDate("dob").toLocalDate());

                    // Convert Blob to InputStream or byte array
                    if (rs.getBlob("photo") != null) {
                        InputStream photoStream = rs.getBlob("photo").getBinaryStream();
                        teacher.setPhoto(photoStream);
                    } else {
                        teacher.setPhoto(null); // Handle case where photo is null
                    }

                    return teacher;
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
