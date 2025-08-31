package org.martinmeer;

import org.martinmeer.utils.DatabaseConnection;
import org.martinmeer.utils.RequestBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SQLExecutor {
    //private static Connection connection;




    public static void getInputString(String inputString) {

    }
    public static void execute(List<ElementTypeTable> loadData) throws SQLException {
        String request = new RequestBuilder().buildRequest();
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

        }


    }
}
