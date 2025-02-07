package org.martinmeer.otkassistant.core.service;

import org.martinmeer.otkassistant.facets.service.FacetMainService;
import org.martinmeer.otkassistant.mthread.service.MTheradMainService;
import org.martinmeer.otkassistant.ost22.service.OstMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainServiceFactory {

    private final OstMainService ostCalculationService;
    private final MTheradMainService mTheradCalculationService;
    private final FacetMainService facetCalculationService;

    @Autowired
    public MainServiceFactory(OstMainService ostCalculationService,
                              MTheradMainService mTheradCalculationService,
                              FacetMainService facetCalculationService) {
        this.ostCalculationService = ostCalculationService;
        this.mTheradCalculationService = mTheradCalculationService;
        this.facetCalculationService = facetCalculationService;
    }

    public MainService getService(String page) {
        return switch (page) {
            case "ost" -> ostCalculationService;
            case "mthread" -> mTheradCalculationService;
            case "facet" -> facetCalculationService;
            default -> throw new IllegalArgumentException("Unknown page type");
        };
    }
}

