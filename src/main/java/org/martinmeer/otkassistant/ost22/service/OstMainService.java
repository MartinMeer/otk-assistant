package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractCalculatedData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractDefinedData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractOutputData;
import org.martinmeer.otkassistant.core.service.*;
import org.martinmeer.otkassistant.core.service.sceletal.MainService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

 /*const pageId = 'ost';
       value = response.deviance_values || '';
        response.min_mes_value || '';
       response.max_mes_value || '';*/

@Service
public class OstMainService extends MainService<BigDecimal> {


    public OstMainService(SchemaAwareNamedParameterJdbcTemplate jdbcTemplate,
                          @Qualifier("ostInputData") AbstractInputData inputData,
                          @Qualifier("ostDefinedData") AbstractDefinedData<BigDecimal> definedData) {
        super(jdbcTemplate, inputData, definedData);
    }

    @Override
    protected void outputMapper() {
        String devianceValues = definedData.getLowerDeviance().toString()
                + definedData.getUpperDeviance().toString();
        output = new HashMap<>();
        output.put("deviance_values", devianceValues);
        output.put("max_mes_value", );
        output.put("min_mes_value", null);
    }
}
