package com.cms.dao;

import com.cms.model.Course1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseDAO {

    public boolean assignCourseToTeacher(Course1 course) {
        boolean status = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO assigncourses (course_id, course_name, teacherId) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, course.getCourseId());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getTeacherId());

            int rowsAffected = stmt.executeUpdate();
            status = rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }
}
