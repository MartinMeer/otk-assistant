package org.martinmeer.otkassistant.core;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @CrossOrigin(origins = "http://martinmeer.com")
    @GetMapping("/api/data")
    public String getData() {
        return "Данные с бэкенда!";
    }
}