package org.martinmeer;

import lombok.Getter;

import java.util.List;

@Getter
public class RequestBuilder {

    private String request;



    public String buildRequest(List<Table> loadData) {
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
