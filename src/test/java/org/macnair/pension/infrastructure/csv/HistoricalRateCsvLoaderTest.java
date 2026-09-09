package org.macnair.pension.infrastructure.csv;

import org.macnair.pension.domain.HistoricalRate;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HistoricalRateCsvLoaderTest {

    @Test
    void testCsvLoaderReadsRatesCorrectly() {

        HistoricalRateCsvLoader loader = new HistoricalRateCsvLoader();
        List<HistoricalRate> rates = loader.load("test-historical-rates.csv");

        assertEquals(3, rates.size());

        HistoricalRate first = rates.get(0);
        assertEquals(1990, first.getYear());
        assertEquals(0.05, first.getGrowthRate());
        assertEquals(0.04, first.getInterestRate());
    }
}
