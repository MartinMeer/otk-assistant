package org.martinmeer;

import org.martinmeer.repo.MainReference;
import org.martinmeer.repo.Range;
import org.martinmeer.repo.RangeByToleranceByType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SQLExecutor {
    Logger logger = LoggerFactory.getLogger(SQLExecutor.class);

    public void saveRange(Connection conn, Range range) throws SQLException {
        Statement statement = conn.createStatement();
        String request = rangeRequest(range);
        int rowCount = statement.executeUpdate(request);
        if (rowCount == 0) {
            logger.error("Range saving failed, duplicate found: {}", range.range());
        } else {
            logger.info("{} saved successfully to range", range.range());
        }
    }

    public void saveRttAndMainReference(Connection conn, InputProcessor inputProcessor) throws SQLException {
        saveRangeByToleranceByType(conn, inputProcessor.createRangeByToleranceByType());
        saveMainReference(conn, inputProcessor.createMainReferencesList());
    }

    private void saveRangeByToleranceByType(Connection conn, RangeByToleranceByType rtt) throws SQLException {
        Statement statement = conn.createStatement();
        String request = rangeByToleranceByTypeRequest(rtt);
        int rowCount = statement.executeUpdate(request);
        if (rowCount == 0) {
            logger.error("RangeByToleranceByType Saving failed, duplicate found: {}", rtt);
        } else {
            logger.info("{} to RangeByToleranceByType saved successfully", rtt);
        }
    }

    private void saveMainReference(Connection conn, List<MainReference> mainReferenceList) throws SQLException {
        String request = "INSERT INTO esdp.main_reference (rtt_id, dev_code, es, ei) VALUES (\n" +
                "?,\n" +
                "?,\n" +
                "?,\n" +
                "?\n" +
                ");\n";
        PreparedStatement psmt = conn.prepareStatement(request);
        for (MainReference mainReference : mainReferenceList) {
            psmt.setObject(1, mainReference.rtt_id());
            psmt.setInt(2, mainReference.dev_code());
            psmt.setInt(3, mainReference.es());
            psmt.setInt(4, mainReference.ei());
            psmt.addBatch();
        }
        psmt.executeBatch();
    }

    private String rangeRequest(Range range) {
        String rangeValue = range.range();
        return "INSERT INTO esdp.size_range (s_range) VALUES ('" + rangeValue + "':: numrange) ON CONFLICT (s_range) DO NOTHING;";
    }

    private String rangeByToleranceByTypeRequest(RangeByToleranceByType rtt) {
        String rtt_id = rtt.rtt_id().toString();
        String el_type = rtt.el_type();
        String bas_tol = rtt.bas_tol();
        String range = rtt.range();

        return "INSERT INTO esdp.range_tolerance_by_type (rtt_id, el_type, bas_tol_id, range_id ) VALUES (\n" +
                "'" + rtt_id + "',\n" +
                "'" + el_type + "',\n" +
                "(SELECT bas_tol_id FROM esdp.basic_tolerance WHERE bas_tol = '" + bas_tol + "'),\n" +
                "(SELECT range_id FROM esdp.range WHERE RANGE = '" + range + "':: numrange)\n" +
                ");";
    }
}
