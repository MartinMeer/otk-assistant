package org.martinmeer.otkassistant.core.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
@Getter
@Setter
public abstract class ComparedData {
    private String rawData;
    private Object comparedData;
    private String sql;

    public abstract void getValueFromDB(Connection connection) throws SQLException;

}
