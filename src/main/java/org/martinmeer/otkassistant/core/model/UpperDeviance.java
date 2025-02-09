package org.martinmeer.otkassistant.core.model;

import org.martinmeer.otkassistant.core.model.sceletal.FetchedData;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class UpperDeviance extends FetchedData {
    @Override
    public void getValueFromDB(Connection connection) throws SQLException {
    }
}
