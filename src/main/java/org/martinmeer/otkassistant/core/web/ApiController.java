package org.martinmeer.otkassistant.core.web;

import lombok.Setter;
import org.martinmeer.otkassistant.ost22.domain.OstOutputBody;
import org.martinmeer.otkassistant.ost22.service.OstCalculationService;
import org.martinmeer.otkassistant.ost22.service.OstOutputMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController // Автоматически конвертирует ответы в JSON
@RequestMapping("/api")
@Setter
public class ApiController {

    private OstCalculationService ostCalculationService;

    @Autowired
    public ApiController(OstCalculationService ostCalculationService) {
        this.ostCalculationService = ostCalculationService;
    }

    @PostMapping("/process-string")
    public @ResponseBody Map<String, String> output(@RequestBody StringRequest request) {
        String input = request.getInputString();

        // Передаем строку в сервисный слой для обработки
        Map<String, String> output = ostCalculationService.generateOutputMap(input);

        // Возвращаем результат в виде объекта StringResponse
        return output;
    }

    // Вложенный класс для представления входного запроса
    public static class StringRequest {
        private String inputString;

        public String getInputString() {
            return inputString;
        }

        public void setInputString(String inputString) {
            this.inputString = inputString;
        }
    }
}

