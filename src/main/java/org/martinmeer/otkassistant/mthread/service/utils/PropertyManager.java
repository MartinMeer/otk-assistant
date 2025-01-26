package org.martinmeer.otkassistant.mthread.service.utils;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
public class PropertyManager {

    //private static PathMap pathMap;

    /*private PropertyManager(PathMap pathMap) {
        this.pathMap = pathMap;
    }*/

    public static Map<String, String> generateProps() throws IOException {
        //Map<String,Path> pathToProperties = pathMap.getPathToProperties();
        Map<String, String> connSettings = new HashMap<>(OtkParser.parseYaml(Path.of("src/main/resources/db_users.yml")
                .toAbsolutePath()
                .normalize()));
        Map<String, String> url = OtkParser.parseYaml(Path.of("src/main/resources/psql_url.yml")
                .toAbsolutePath()
                .normalize());
        connSettings.put("url", "jdbc:"
                + url.get("subprotocol")
                + ":"
                + url.get("subname")
                + url.get("subsubname"));
        return connSettings;
    }
}
