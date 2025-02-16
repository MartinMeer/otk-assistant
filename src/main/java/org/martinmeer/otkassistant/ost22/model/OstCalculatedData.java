package org.martinmeer.otkassistant.ost22.model;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractCalculatedData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractDefinedData;
import org.martinmeer.otkassistant.core.service.ValueCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OstCalculatedData extends AbstractCalculatedData<BigDecimal> {


    @Override
    @Autowired
    public void generateCalculatedData(@Qualifier("ostDefinedData")  AbstractDefinedData<BigDecimal> definedData) {
        definedData.
    }



    //public void generateCalculatedData(OstDefinedData definedData) {

    }



