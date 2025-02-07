package org.martinmeer.otkassistant.mthread.model;



import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public interface GostDAO<T> {
    //T getValue() throws SQLException, IOException;

    String getValue(Map<MThrdNSpace, String> sql) throws SQLException, IOException;
}
