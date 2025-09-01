package org.martinmeer;

import org.martinmeer.utils.DatabaseConnection;
import org.martinmeer.utils.RequestBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLExecutor {


    private final InputDataProcessor inputDataProcessor;

    public SQLExecutor(InputDataProcessor inputDataProcessor) {
        this.inputDataProcessor = inputDataProcessor;
    }

    public void execute() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement rangeStatement = connection.createStatement();
            rangeStatement.execute(saveRange());
            Statement rangeByTolStatement = connection.createStatement();
            rangeByTolStatement.execute(saveRangeByToleranceByType());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

        }

    }

    private String saveRange() {
        String range = inputDataProcessor.getRange().range();
        return "INSERT INTO esdp.RANGE (\"range\") VALUES ('" + range + "':: numrange);";
    }

    private String saveRangeByToleranceByType() {
        String rtt_id = inputDataProcessor.getRangeByToleranceByType().rtt_id().toString();
        String el_type = inputDataProcessor.getRangeByToleranceByType().el_type();
        String bas_tol = inputDataProcessor.getRangeByToleranceByType().bas_tol();
        String range = inputDataProcessor.getRangeByToleranceByType().range();

        return "INSERT INTO esdp.range_tolerance_by_type (rtt_id, el_type, bas_tol_id, range_id ) VALUES (\n" +
                "'" + rtt_id + "',\n" +
                "'" + el_type + "',\n" +
                "(SELECT bas_tol_id FROM esdp.basic_tolerance WHERE bas_tol = '" + bas_tol + "'),\n" +
                "(SELECT range_id FROM esdp.range WHERE RANGE = '" + range + "':: numrange)\n" +
                ");";
    }

    private String saveMainReference() {

        return "INSERT INTO esdp.main_reference (rtt_id, dev_code, es, ei) VALUES (\n" +
                "'06a1488f-1c7a-4cf8-b518-e090a7e65ef7',\n" +
                "9,\n" +
                "295,\n" +
                "270\n" +
                ");\n";
    }
}
