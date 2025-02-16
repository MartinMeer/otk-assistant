package org.martinmeer.otkassistant.core.model.sceletal;

import lombok.Setter;
import org.martinmeer.otkassistant.core.service.ValueCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

public abstract class AbstractCalculatedData<T> {


    //protected final AbstractDefinedData<T> definedData;
    protected T baseDimension;
    protected T maxMeasuringValue;
    protected T minMeasuringValue;


    public abstract void generateCalculatedData(AbstractDefinedData<T> definedData);
}
