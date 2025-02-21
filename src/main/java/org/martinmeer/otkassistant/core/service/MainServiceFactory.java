package org.martinmeer.otkassistant.core.service;

import org.martinmeer.otkassistant.facets.service.FacetMainService;
import org.martinmeer.otkassistant.ost22.service.OstMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainServiceFactory {

    private final OstMainService ostMainService;
    //private final MThreadMainService mThreadCalculationService;
    private final FacetMainService facetMainService;

    @Autowired
    public MainServiceFactory(OstMainService ostMainService,
                              //MThreadMainService mThreadCalculationService,
                              FacetMainService facetMainService) {
        this.ostMainService = ostMainService;
        //this.mThreadCalculationService = mThreadCalculationService;
        this.facetMainService = facetMainService;
    }

    public MainService getService(String page) {

        return switch (page) {
            case "ost22" -> ostMainService;
            //case "thread_m" -> mThreadCalculationService;
            case "facet" -> facetMainService;
            default -> throw new IllegalArgumentException("Unknown page type");
        };
    }
}

