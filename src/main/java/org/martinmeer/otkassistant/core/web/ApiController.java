package org.martinmeer.otkassistant.core.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController // Автоматически конвертирует ответы в JSON
@RequestMapping("/api")
public class ApiController {

    // Пример POST-запроса: принять строку, вернуть JSON
    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processString(@RequestBody String input) {

        Map<String, Object> response = new HashMap<>();
        response.put("original", input);
        response.put("processed", input.toUpperCase());
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }
}