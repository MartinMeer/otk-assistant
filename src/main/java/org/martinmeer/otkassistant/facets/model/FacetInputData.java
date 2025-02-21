package org.martinmeer.otkassistant.facets.model;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.springframework.stereotype.Component;

@Component
public class FacetInputData extends AbstractInputData {
    @Override
    protected String normalize(String str) {
        return "";
    }

    @Override
    protected void refineData(String input) {

    }
}
