package org.martinmeer.otkassistant.mthread.service;

import org.martinmeer.otkassistant.core.model.CalculationService;
import org.springframework.stereotype.Component;

import java.util.Map;

/*
pageId = 'm-thread';
value = response.pitch_diameter || '';
        value = response.es_d2 || '';
        value = response.ei_d2 || '';
        value = response.max_mes_value_d2 || '';
       value = response.min_mes_value_d2 || '';


        value = response.nom_diameter || '';
        value = response.es_d || '';
        value = response.ei_d || '';
        value = response.max_mes_value_d || '';
        value = response.min_mes_value_d || '';
*/



@Component
public class MTheradCalculationService implements CalculationService {
    @Override
    public Map<String, String> generateOutput(String input) {
        return Map.of();
    }
}
