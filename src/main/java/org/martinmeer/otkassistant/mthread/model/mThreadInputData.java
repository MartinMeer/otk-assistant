package org.martinmeer.otkassistant.mthread.model;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.springframework.stereotype.Component;

@Component
public class mThreadInputData extends AbstractInputData {
    @Override
    protected String normalize(String str) {
        return "";
    }

    @Override
    protected void refineData(String input) {

    }
}
