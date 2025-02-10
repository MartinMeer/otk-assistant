package org.martinmeer.otkassistant.ost22.service;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.martinmeer.otkassistant.core.service.FetchedDataProcessor;
import org.martinmeer.otkassistant.ost22.model.OstInputData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
@FieldNameConstants
@Getter
public class OstFetchedDataProcessor extends FetchedDataProcessor {

    private OstInputData ostInputData;


    public OstFetchedDataProcessor(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }
    @Autowired
    public void setOstInputData(OstInputData ostInputData) {
        this.ostInputData = ostInputData;
    }



    @Override
    protected void init() {
        nominalDimension.setBaseData(ostInputData.getInputDimension());
    }

    @Override
    public void fetchData() {



    }
}
