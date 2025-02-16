package org.martinmeer.otkassistant.core.service.sceletal;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractCalculatedData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractDefinedData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractOutputData;
import org.martinmeer.otkassistant.core.service.SchemaAwareNamedParameterJdbcTemplate;

import java.util.Map;
import java.util.stream.Collectors;


public abstract class MainService<T> {

    protected final SchemaAwareNamedParameterJdbcTemplate jdbcTemplate;
    protected final AbstractInputData inputData;
    protected final AbstractDefinedData definedData;
    //private final AbstractCalculatedData<T> calculatedData;
    //private final AbstractOutputData<T> outputData;

    protected Map<String, String> output;

    public MainService(SchemaAwareNamedParameterJdbcTemplate jdbcTemplate,
                       AbstractInputData inputData,
                       AbstractDefinedData definedData)
                       //AbstractCalculatedData<T> calculatedData,
                       //AbstractOutputData<T> outputData)
                       {
        this.jdbcTemplate = jdbcTemplate;
        this.inputData = inputData;
        this.definedData = definedData;
        //this.calculatedData = calculatedData;
        //this.outputData = outputData;
    }

    public Map<String, String> generateOutput(String page, String input) {
        jdbcTemplate.setSchemaName(page);
        inputData.createInputData(input);
        definedData.generateDefinedData(jdbcTemplate);
        outputMapper();
        return output;
    }

    //protected abstract void setOutputMap();

    protected abstract void outputMapper();

}
