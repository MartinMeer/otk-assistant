package org.martinmeer.otkassistant.core.web;

import lombok.Getter;
import lombok.Setter;
import org.martinmeer.otkassistant.core.service.MainServiceFactory;
import org.martinmeer.otkassistant.core.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@CrossOrigin(origins = "https://otk-help.martinmeer.com/")
@RestController // Parse JSON for response automatically
@RequestMapping("/api")
@Setter

public class ApiController {

    private MainServiceFactory mainServiceFactory;

    @Autowired
    public ApiController(MainServiceFactory mainServiceFactory) {
        this.mainServiceFactory = mainServiceFactory;
    }

    @PostMapping("/process")
    public @ResponseBody Map<String, String> output(@RequestBody StringRequest request) {

        /*JSON from frontend: { pageId = "ost22", 'm-thread'
                               "inputString": "typeValue:sizeValue"}*/

        String page = request.getInputData(); // "ost22"
        String input = request.getInputString(); // "exampleType:10.00"


        MainService mainService = mainServiceFactory.getService(page);

        if (mainService == null) {
            throw new IllegalArgumentException("Unknown page: " + page);
        }

        //Возвращаем результат в виде объекта StringResponse/
        return mainService.generateOutput(page, input);
    }

    // Вложенный класс для представления входного запроса
    @Getter
    @Setter
    public static class StringRequest {
        private String inputData;
        private String inputString;
    }
}

