package org.martinmeer.otkassistant.core.service;

import org.martinmeer.otkassistant.facets.service.FacetMainService;
import org.martinmeer.otkassistant.mthread.service.MThreadMainService;
import org.martinmeer.otkassistant.ost22.service.OstMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainServiceFactory {

    private final OstMainService ostCalculationService;
    private final MThreadMainService mThreadCalculationService;
    private final FacetMainService facetCalculationService;

    @Autowired
    public MainServiceFactory(OstMainService ostCalculationService,
                              MThreadMainService mThreadCalculationService,
                              FacetMainService facetCalculationService) {
        this.ostCalculationService = ostCalculationService;
        this.mThreadCalculationService = mThreadCalculationService;
        this.facetCalculationService = facetCalculationService;
    }

    public MainService getService(String page) {

        return switch (page) {
            case "ost22" -> ostCalculationService;
            case "thread_m" -> mThreadCalculationService;
            case "facet" -> facetCalculationService;
            default -> throw new IllegalArgumentException("Unknown page type");
        };
    }
}

