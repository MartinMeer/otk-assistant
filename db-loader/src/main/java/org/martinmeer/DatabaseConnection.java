package org.martinmeer;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Static utility class for managing database connections.
 * Provides thread-safe singleton connection management for simple JDBC applications.
 */
@Getter
public class DatabaseConnection {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    private static String url;
    private static String username;
    private static String password;
    private static boolean initialized = false;
    private static Connection connection;

    private static Properties properties;


    // Private constructor to prevent instantiation
    private DatabaseConnection() {
    }

    /**
     * Initialize the database connection parameters from properties file
     */
   static {
        loadProperties();
    }

    /**
     * Load database configuration from properties file
     */
    private static void loadProperties() {
        logger.info("Loading db properties");
        properties = new Properties();
        try (InputStream inputStream = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream("database.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Properties file not found");
        }
        try {
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            initialized = true;
            logger.info("Properties loaded successfully");
        } catch (Exception e) {
            logger.error("Cannot load properties from file");
        }

    }

    /**
     * Set default database connection properties
     */

    /**
     * Get a database connection. Creates a new connection if one doesn't exist
     * or if the existing connection is closed.
     *
     * @return Database connection
     * @throws SQLException if connection cannot be established
     */
    public static Connection getConnection() throws SQLException{
        connection = DriverManager.getConnection(url, username, password);
        logger.info("Connection established");
        return connection;
    }



    /**
     * Close the database connection
     */
    public static synchronized void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    logger.info("Database connection closed successfully");
                }
            } catch (SQLException e) {
                logger.error("Error closing database connection: {}", e.getMessage());
            } finally {
                connection = null;
            }
        }
    }



    /**
     * Test the database connection
     *
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection testConn = getConnection()) {
            return testConn != null && !testConn.isClosed();
        } catch (SQLException e) {
            logger.error("Database connection test failed: {}", e.getMessage());
            return false;
        }
    }


    /**
     * Execute a simple query to verify database connectivity
     *
     * @return true if query executes successfully, false otherwise
     */
    public static boolean isConnectionValid() {
        try (Connection conn = getConnection()) {
            return conn != null && conn.isValid(5); // 5 second timeout
        } catch (SQLException e) {
            logger.error("Connection validation failed: {}", e.getMessage());
            return false;
        }
    }


    /**
     * Get current connection URL (for debugging purposes)
     *
     * @return database URL
     */


    /**
     * Get current username (for debugging purposes)
     *
     * @return database username
     */

}