package org.martinmeer.otkassistant.core.model;

import lombok.Getter;
import org.martinmeer.otkassistant.core.model.sceletal.FetchedData;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
@Getter
public class NominalDimension extends FetchedData {

    //private BigDecimal nominalDimension;

    @Override
    public void getValueFromDB(Connection connection) throws SQLException {

    }
}
