package org.martinmeer.otkassistant.core.model;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class LowerDeviance extends ComparedData{
    @Override
    public void getValueFromDB(Connection connection) throws SQLException {

    }
}
