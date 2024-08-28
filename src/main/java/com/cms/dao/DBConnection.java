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
    private static final String PASSWORD = "7008";

    // Singleton instance
    private static Connection connection = null;

    // Private constructor to prevent instantiation
    private DBConnection() {}

    // Public method to provide access to the connection
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            synchronized (DBConnection.class) {
                if (connection == null || connection.isClosed()) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection(URL, USER, PASSWORD);
                        logger.info("Database connection established successfully.");
                    } catch (ClassNotFoundException e) {
                        logger.error("MySQL JDBC Driver not found", e);
                        throw new SQLException("Unable to load JDBC Driver", e);
                    } catch (SQLException e) {
                        logger.error("Failed to create a connection to the database", e);
                        throw e;  // Re-throw the SQLException for the caller to handle
                    }
                }
            }
        }
        return connection;
    }

    // Public method to close the connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed successfully.");
            } catch (SQLException e) {
                logger.error("Failed to close the database connection", e);
            }
        }
    }
}
