package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.core.model.OutputDataMapper;
import org.martinmeer.otkassistant.core.service.CalculatedDataProcessor;
import org.martinmeer.otkassistant.core.service.ComparedDataProcessor;
import org.martinmeer.otkassistant.core.service.InputRefiner;
import org.martinmeer.otkassistant.core.service.MainService;
import org.martinmeer.otkassistant.ost22.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

 /*const pageId = 'ost';
       value = response.deviance_values || '';
        response.min_mes_value || '';
       response.max_mes_value || '';*/

@Component
public class OstMainService extends MainService {


    public OstMainService(@Qualifier("ostInputRefiner") InputRefiner inputRefiner, @Qualifier("ostComparedDataProcessor") ComparedDataProcessor comparedDataProcessor, @Qualifier("ostCalculatedDataProcessor") CalculatedDataProcessor calculatedDataProcessor, OutputDataMapper outputDataMapper) {
        super(inputRefiner, comparedDataProcessor, calculatedDataProcessor, outputDataMapper);
    }

    /*@Override
    public Map<String, String> generateOutput(String input) {
        // Шаг 1: Конвертация строки
        Map<OstNSpace, Object> dataMap = ostInputRefiner.generateDataMap(input);

        BigDecimal nominalDimension = (BigDecimal) dataMap.get(OstNSpace.NOM_DIMENSION);

        // Шаг 2: Валидация числа
        if (!ostInputValidator.validate(nominalDimension)) {
            throw new IllegalArgumentException("Размер должен быть не менее 0.1 мм и не более 10000 мм");
        }

        //Шаг 3. Определение предельных отклонений по ОСТ
        Map<OstNSpace, BigDecimal> deviances = deviancesIdentifier.getDeviances(dataMap);

        //Шаг 4. Вычиление значений для проведения измерений
        Map<OstNSpace, BigDecimal> measuringValues = ostCalculator.calculateMeasuringValues(nominalDimension, deviances);

        //Шаг 4. Формирование ответа на фронтенд
        Map<String, String> outputMap = new HashMap<>();
        outputMap.put(OstNSpace.DEVIANCE_VALUES.toString(),
                deviances.get(OstNSpace.UPPER_DEVIANCE)
                        + ", "
                        + deviances.get(OstNSpace.LOWER_DEVIANCE));
        outputMap.put(OstNSpace.MAX_MES_VALUE.toString(),
                measuringValues.get(OstNSpace.MAX_MES_VALUE).toString());
        outputMap.put(OstNSpace.MIN_MES_VALUE.toString(),
                measuringValues.get(OstNSpace.MIN_MES_VALUE).toString());
        return outputMap;
    }*/
}
