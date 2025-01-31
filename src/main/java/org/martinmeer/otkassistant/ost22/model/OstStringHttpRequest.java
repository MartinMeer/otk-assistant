package org.martinmeer.otkassistant.ost22.model;

import org.martinmeer.otkassistant.core.web.HttpStringRequest;

public class OstStringHttpRequest implements HttpStringRequest {

    private String inputString;

    @Override
    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    @Override
    public String getInputString() {
        return inputString;
    }
}
