package com.cms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBConnection {

    private static final Logger logger = LogManager.getLogger(DBConnection.class);

    private static final String URL = "jdbc:mysql://localhost:3306/universe";
    private static final String USER = "root";
    private static final String PASSWORD = "durga";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            logger.error("MySQL JDBC Driver not found", e);
            throw new SQLException("Unable to load JDBC Driver", e);
        } catch (SQLException e) {
            logger.error("Failed to create a connection to the database", e);
            throw e;  // Re-throw the SQLException for the caller to handle
        }
        return connection;
    }
}
