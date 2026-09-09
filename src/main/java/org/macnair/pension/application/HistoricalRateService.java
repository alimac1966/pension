package org.macnair.pension.application;

import org.macnair.pension.domain.HistoricalRate;
import org.macnair.pension.infrastructure.csv.HistoricalRateCsvLoader;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class HistoricalRateService {

    private static final String CSV_PATH = "historical-rates.csv";

    @Inject
    HistoricalRateCsvLoader csvLoader;

    public List<HistoricalRate> loadHistoricalRates() {
        return csvLoader.load(CSV_PATH);
    }
}
