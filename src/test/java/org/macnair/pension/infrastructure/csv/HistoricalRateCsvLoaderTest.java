package org.macnair.pension.infrastructure.csv;

import org.macnair.pension.domain.HistoricalRate;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HistoricalRateCsvLoaderTest {

    @Test
    void loadsRatesFromCsvResource() {
        HistoricalRateCsvLoader loader = new HistoricalRateCsvLoader();
        List<HistoricalRate> rates = loader.load("test-historical-rates.csv");

        assertEquals(3, rates.size());

        HistoricalRate first = rates.get(0);
        assertEquals(1990, first.getYear());
        assertEquals(0.05, first.getGrowthRate());
        assertEquals(0.04, first.getInterestRate());

        assertEquals(1991, rates.get(1).getYear());
        assertEquals(-0.02, rates.get(1).getGrowthRate());
        assertEquals(0.05, rates.get(1).getInterestRate());
    }

    @Test
    void throwsUsefulErrorWhenResourceIsMissing() {
        HistoricalRateCsvLoader loader = new HistoricalRateCsvLoader();

        RuntimeException error = assertThrows(RuntimeException.class,
                () -> loader.load("missing-rates.csv"));

        assertEquals("Failed to load historical rates CSV", error.getMessage());
        assertInstanceOf(IllegalStateException.class, error.getCause());
        assertTrue(error.getCause().getMessage().contains("missing-rates.csv"));
    }

    @Test
    void wrapsInvalidNumericValues() {
        HistoricalRateCsvLoader loader = new HistoricalRateCsvLoader();

        RuntimeException error = assertThrows(RuntimeException.class,
                () -> loader.load("invalid-historical-rates.csv"));

        assertEquals("Failed to load historical rates CSV", error.getMessage());
        assertInstanceOf(NumberFormatException.class, error.getCause());
    }
}
