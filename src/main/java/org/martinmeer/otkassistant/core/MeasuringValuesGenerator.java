package org.martinmeer.otkassistant.core;

public interface MeasuringValuesGenerator<T> {

    //Map<T, String> generateValuesMap();
    T generateValue();
}
