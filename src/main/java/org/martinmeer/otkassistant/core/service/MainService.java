package org.martinmeer.otkassistant.core.service;

import java.util.Map;

public interface MainService {

    Map<String, String> generateOutput(String page, String input);
}
