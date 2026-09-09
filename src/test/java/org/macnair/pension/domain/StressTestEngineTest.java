package org.macnair.pension.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class StressTestEngineTest {

    @Test
    void testStressTestEngineDepletesPot() {

        RetirementScenario scenario = new RetirementScenario.Builder()
                .pensionPot(new PensionPot.Builder()
                        .currentBalance(BigDecimal.valueOf(100_000))
                        .annualGrowthRate(0.06)
                        .build())
                .mortgage(new Mortgage.Builder()
                        .outstandingBalance(BigDecimal.valueOf(50_000))
                        .annualInterestRate(0.04)
                        .monthlyPayment(BigDecimal.valueOf(500))
                        .build())
                .monthlyDrawdown(BigDecimal.valueOf(1500))
                .retirementStartDate(java.time.LocalDate.now())
                .build();

        List<HistoricalRate> rates = List.of(
                new HistoricalRate(2000, 0.05, 0.04),
                new HistoricalRate(2001, -0.10, 0.04),
                new HistoricalRate(2002, 0.02, 0.04)
        );

        StressTestEngine engine = new StressTestEngine();
        StressTestResult result = engine.run(scenario, rates);

        assertTrue(result.getMonthsUntilDepletion() > 0);
        assertEquals(2000, result.getStartYear());
    }
}
