package com.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.cms.dao.DBConnection.getConnection;

public class AttendanceDAO {

    private static final String UPDATE_ATTENDANCE_SQL =
            "UPDATE attendance SET status = ? WHERE studentid = ? AND subjectId = ? AND date = ?";


    public boolean updateAttendance(int studentId, int subjectId, String date, String status) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ATTENDANCE_SQL)) {
            statement.setString(1, status);
            statement.setInt(2, studentId);

            statement.setInt(3, subjectId);
            statement.setString(4, date);

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}
