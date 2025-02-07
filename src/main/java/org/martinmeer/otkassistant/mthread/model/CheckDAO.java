package org.martinmeer.otkassistant.mthread.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static org.martinmeer.otkassistant.mthread.service.utils.Connector.psqlConnection;

public class CheckDAO{


    public String getValue(Map<MThrdNSpace, String> sql) throws SQLException, IOException {
        String parameter = sql.get(MThrdNSpace.SQL_PARAMETER);
        String query = sql.get(MThrdNSpace.DB_QUERY);
        String dbColumn = sql.get(MThrdNSpace.DB_ALIAS);
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
