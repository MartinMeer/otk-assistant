package org.martinmeer.otkassistant.jdbc;



import org.martinmeer.otkassistant.utils.Namespace;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static org.martinmeer.otkassistant.utils.Connector.psqlConnection;


public class PitchDAO{


    public String getValue(Map<Namespace, String> sql) throws SQLException, IOException {
        String parameter = sql.get(Namespace.NOM_DIAMETER);
        String query = sql.get(Namespace.DB_QUERY);
        //String alias = "pitch_default";
        String value = "";
        try (Connection conn = psqlConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, parameter);
            try (ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()) {
                    value = rs.getString("pitch_default");
                }
            } catch (SQLException e) {
                System.out.println("PostgreSQL JDBC Driver not found");
                throw new RuntimeException(e);
            }
        }
        return value;
    }
}
