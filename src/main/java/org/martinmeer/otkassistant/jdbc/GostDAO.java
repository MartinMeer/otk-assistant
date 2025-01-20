package org.martinmeer.otkassistant.jdbc;

import org.martinmeer.utils.Namespace;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public interface GostDAO<T> {
    //T getValue() throws SQLException, IOException;

    String getValue(Map<Namespace, String> sql) throws SQLException, IOException;
}
