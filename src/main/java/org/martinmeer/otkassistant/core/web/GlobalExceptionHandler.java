package org.martinmeer.otkassistant.core.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // Помечаем класс как глобальный обработчик исключений
public class GlobalExceptionHandler {

    // Перехватываем IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        // Формируем тело ответа
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Invalid request");
        errorResponse.put("message", ex.getMessage()); // Сообщение из исключения

        // Возвращаем HTTP 400 и JSON-тело
        return ResponseEntity.badRequest().body(errorResponse);
    }

    // Можно добавить обработку других исключений
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Internal error");
        errorResponse.put("message", "Something went wrong");
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}