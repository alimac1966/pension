package org.macnair.pension.application;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.macnair.pension.domain.HistoricalRate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@QuarkusTest
class HistoricalRateServiceTest {

    @Inject
    HistoricalRateService service;

    @Test
    void loadsBundledHistoricalRates() {
        List<HistoricalRate> rates = service.loadHistoricalRates();

        assertFalse(rates.isEmpty());
        assertEquals(1900, rates.get(0).getYear());
        assertEquals(0.045, rates.get(0).getGrowthRate());
        assertEquals(0.027, rates.get(0).getInterestRate());
    }
}
