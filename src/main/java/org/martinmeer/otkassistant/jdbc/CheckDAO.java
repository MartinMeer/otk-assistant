package org.martinmeer.otkassistant.jdbc;

import org.martinmeer.utils.Namespace;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static org.martinmeer.utils.Connector.psqlConnection;

public class CheckDAO{


    public String getValue(Map<Namespace, String> sql) throws SQLException, IOException {
        String parameter = sql.get(Namespace.PARAMETER);
        String query = sql.get(Namespace.DB_QUERY);
        String dbColumn = sql.get(Namespace.DB_ALIAS);
        String value = "";
        try (Connection conn = psqlConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, parameter);
            try (ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()) {
                    value = rs.getString(dbColumn);
                }
            } catch (SQLException e) {
                System.out.println("PostgreSQL JDBC Driver not found");
                throw new RuntimeException(e);
            }
        }
        return value;
    }
}
