package org.martinmeer.otkassistant.mthread.domain;

public interface Parameter {
    //void getFromDb();

    void getFromDb(String input);

    void setValue();
}
