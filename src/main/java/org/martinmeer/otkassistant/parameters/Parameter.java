package org.martinmeer.otkassistant.parameters;

public interface Parameter {
    //void getFromDb();

    void getFromDb(String input);

    void setValue();
}
