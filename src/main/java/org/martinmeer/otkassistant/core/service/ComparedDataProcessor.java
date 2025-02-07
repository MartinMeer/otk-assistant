package org.martinmeer.otkassistant.core.service;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.martinmeer.otkassistant.core.model.ComparedData;
import org.martinmeer.otkassistant.core.model.LowerDeviance;
import org.martinmeer.otkassistant.core.model.NominalDimension;
import org.martinmeer.otkassistant.core.model.UpperDeviance;
import org.martinmeer.otkassistant.ost22.model.OstNSpace;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
@FieldNameConstants
@Getter
public abstract class ComparedDataProcessor<E extends Enum<E>, V> {

    @Setter
    private EnumMap<E, V> inputDataMap;



    private NominalDimension nominalDimension;
    private UpperDeviance upperDeviance;
    private LowerDeviance lowerDeviance;

    public abstract void compareData();



    public Map<String, ComparedData> getComparedData() {
        return Map.of(Fields.nominalDimension, nominalDimension,
                Fields.upperDeviance, upperDeviance,
                Fields.lowerDeviance, lowerDeviance);
    }
}
