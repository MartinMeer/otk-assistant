package org.martinmeer.otkassistant.ost22.service;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.martinmeer.otkassistant.core.service.FetchedDataService;
import org.martinmeer.otkassistant.core.service.SchemaAwareNamedParameterJdbcTemplate;
import org.martinmeer.otkassistant.ost22.model.OstInputData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
@FieldNameConstants
@Getter
public class OstFetchedDataService extends FetchedDataService {

    private OstInputData ostInputData;

    protected OstFetchedDataService(SchemaAwareNamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Override
    protected void init() {
        nominalDimension.setBaseData(ostInputData.getInputDimension());
    }

    @Override
    public void fetchData() {



    }

    @Override
    protected void setSchema() {

    }
}
