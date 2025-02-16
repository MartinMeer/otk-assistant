package org.martinmeer.otkassistant.core.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.martinmeer.otkassistant.core.service.MainService;
import org.martinmeer.otkassistant.core.service.MainServiceFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ApiControllerTest {

    @Mock
    private MainServiceFactory mainServiceFactory;

    @Mock
    private MainService mainService;

    @InjectMocks
    private ApiController apiController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(apiController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

    }

    @Test
    void processRequest_ValidInput_ReturnsServiceOutput() throws Exception {
        when(mainServiceFactory.getService("ost22")).thenReturn(mainService);
        when(mainService.generateOutput("ost22", "type:10.00"))
                .thenReturn(Map.of("result", "success"));
        mockMvc.perform(post("/api/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"inputData\":\"ost22\", \"inputString\":\"type:10.00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()));
    }

    @Test
    void processRequest_UnknownPageId_ThrowsException() throws Exception {
        // 1. Настраиваем мок фабрики на возврат null для неизвестного pageId
        String unknownPageId = "unknownPage";
        when(mainServiceFactory.getService(unknownPageId)).thenReturn(null);

        // 2. Формируем запрос с неизвестным pageId
        String invalidJsonRequest =
                "{ \"inputData\": \"" + unknownPageId + "\", \"inputString\": \"test\" }";

        // 3. Отправляем запрос и проверяем результат
        mockMvc.perform(post("/api/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonRequest))
                .andExpect(status().isBadRequest()) // Ожидаем 400 Bad Request
                .andExpect(jsonPath("$.error").value("Invalid request")) // Проверяем сообщение
                .andExpect(jsonPath("$.message").value("Unknown page: " + unknownPageId));
    }
}

