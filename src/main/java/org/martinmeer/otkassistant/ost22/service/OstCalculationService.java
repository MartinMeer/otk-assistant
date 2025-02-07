package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.core.model.CalculationService;
import org.martinmeer.otkassistant.ost22.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

 /*const pageId = 'ost';
       value = response.deviance_values || '';
        response.min_mes_value || '';
       response.max_mes_value || '';*/

@Component
public class OstCalculationService implements CalculationService {


    private final OstInputDataMap ostInputDataMap;
    private final OstInputConverter ostInputConverter;
    private final OstInputValidator ostInputValidator;
    private final DeviancesIdentifier deviancesIdentifier;
    private final OstCalculator ostCalculator;
    private final OstOutputMap outputMap;
    private final OstOutputMap ostOutputMap;

    @Autowired
    public OstCalculationService(OstInputDataMap ostInputDataMap, OstInputConverter ostInputConverter, OstInputValidator ostInputValidator, DeviancesIdentifier deviancesIdentifier, OstCalculator ostCalculator, OstOutputMap outputMap, OstOutputMap ostOutputMap) {
        this.ostInputDataMap = ostInputDataMap;
        this.ostInputConverter = ostInputConverter;
        this.ostInputValidator = ostInputValidator;
        this.deviancesIdentifier = deviancesIdentifier;
        this.ostCalculator = ostCalculator;
        this.outputMap = outputMap;
        this.ostOutputMap = ostOutputMap;
    }


    @Override
    public Map<String, String> generateOutput(String input) {
        // Step 1: inputData class init
        ostInputDataMap.setInputDataMap(input);

        // Step 2: Validate input
        if (!ostInputValidator.validate()) {
            throw new IllegalArgumentException("Размер должен быть не менее 0.1 мм и не более 10000 мм");
        }

        //Step 3. Refine values from DB
        Map<String, Object> refinedValues = deviancesIdentifier.getDeviances(dataMap);

        //Шаг 4. Вычисление значений для проведения измерений
        Map<String, Object> calculatedValues = ostCalculator.calculateMeasuringValues(nominalDimension, deviances);

        //Шаг 4. Формирование ответа на фронтенд
        /*Map<String, String> outputMap = new HashMap<>();
        outputMap.put(OstNSpace.DEVIANCE_VALUES.toString(),
                deviances.get(OstNSpace.UPPER_DEVIANCE)
                        + ", "
                        + deviances.get(OstNSpace.LOWER_DEVIANCE));
        outputMap.put(OstNSpace.MAX_MES_VALUE.toString(),
                measuringValues.get(OstNSpace.MAX_MES_VALUE).toString());
        outputMap.put(OstNSpace.MIN_MES_VALUE.toString(),
                measuringValues.get(OstNSpace.MIN_MES_VALUE).toString());*/

        Map<String, String> outputMap = ostOutputMap.getOutputMap(refinedValues, calculatedValues);

        return outputMap;
    }
}
