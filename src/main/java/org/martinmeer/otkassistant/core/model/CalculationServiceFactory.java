package org.martinmeer.otkassistant.core.model;

import org.martinmeer.otkassistant.facets.service.FacetCalculationService;
import org.martinmeer.otkassistant.mthread.service.MTheradCalculationService;
import org.martinmeer.otkassistant.ost22.service.OstCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalculationServiceFactory {

    private final OstCalculationService ostCalculationService;
    private final MTheradCalculationService mTheradCalculationService;
    private final FacetCalculationService facetCalculationService;

    @Autowired
    public CalculationServiceFactory(OstCalculationService ostCalculationService,
                                     MTheradCalculationService mTheradCalculationService,
                                     FacetCalculationService facetCalculationService) {
        this.ostCalculationService = ostCalculationService;
        this.mTheradCalculationService = mTheradCalculationService;
        this.facetCalculationService = facetCalculationService;
    }

    public CalculationService getService(String page) {
        return switch (page) {
            case "ost" -> ostCalculationService;
            case "mthread" -> mTheradCalculationService;
            case "facet" -> facetCalculationService;
            default -> throw new IllegalArgumentException("Unknown page type");
        };
    }
}

