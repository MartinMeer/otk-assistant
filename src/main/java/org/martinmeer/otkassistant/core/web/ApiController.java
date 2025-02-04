package org.martinmeer.otkassistant.core.web;

import lombok.Getter;
import lombok.Setter;
import org.martinmeer.otkassistant.core.model.CalculationService;
import org.martinmeer.otkassistant.core.model.CalculationServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController // Автоматически конвертирует ответы в JSON
@RequestMapping("/api")
@Setter
public class ApiController {

    private CalculationServiceFactory calculationServiceFactory;

    @Autowired
    public ApiController(CalculationServiceFactory calculationServiceFactory) {
        this.calculationServiceFactory = calculationServiceFactory;
    }

    @PostMapping("/process")
    public @ResponseBody Map<String, String> output(@RequestBody StringRequest request) {

        /*{
  pageId = "ost", 'm-thread'
  "inputString": "typeValue:sizeValue"
}*/
        String page = request.getInputData(); // "ost"
        String input = request.getInputString(); // "exampleType:10.00"

        CalculationService calculationService = calculationServiceFactory.getService(page);

        // Передаем строку в сервисный слой для обработки

        // Возвращаем результат в виде объекта StringResponse
        return calculationService.generateOutput(input);
    }

    // Вложенный класс для представления входного запроса
    @Getter
    @Setter
    public static class StringRequest {
        private String inputData;
        private String inputString;
    }
}

