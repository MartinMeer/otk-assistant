package org.martinmeer.otkassistant.core.model;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractFetchedData;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class LowerDeviance extends AbstractFetchedData {
    @Override
    public void getValueFromDB(Connection connection) throws SQLException {

    }
}
