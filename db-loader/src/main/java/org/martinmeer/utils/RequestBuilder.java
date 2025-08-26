package org.martinmeer.utils;

import lombok.Getter;

@Getter
public class RequestBuilder {

    private String request;



    public String buildRequest() {
        return request;
    }

    private static String sqlCommander() {
        return "";
    }

    private static String sqlReader() {

        return "";
    }
    private static String sqlUpdater() {
        return "";
    }

}
