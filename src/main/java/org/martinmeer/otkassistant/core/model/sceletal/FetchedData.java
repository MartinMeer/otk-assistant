package org.martinmeer.otkassistant.core.model.sceletal;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
@Getter
@Setter
public abstract class FetchedData {
    protected String rawData;
    protected Object fetchedData;
    //protected String sql;

    public abstract void getValueFromDB(Connection connection) throws SQLException;

}
