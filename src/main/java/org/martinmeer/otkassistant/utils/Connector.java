package org.martinmeer.otkassistant.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class Connector {

    private static Connection connection;

    public static Connection psqlConnection() throws SQLException, IOException {
        Map<String, String> connSettings = PropertyManager.generateProps();
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("PostgeSQL JDBC Driver not found");
                throw new RuntimeException(e);
            }
            connection = DriverManager.getConnection(
                    connSettings.get("url"),
                    connSettings.get("username"),
                    connSettings.get("password"));
        }
        return connection;
    }
}
