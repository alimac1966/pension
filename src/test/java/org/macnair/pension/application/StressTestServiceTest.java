package org.macnair.pension.application;

import org.macnair.pension.infrastructure.dto.StressTestRequestDTO;
import org.macnair.pension.infrastructure.dto.StressTestResultDTO;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class StressTestServiceTest {

    @Inject
    StressTestService stressTestService;

    @Test
    void testStressTestServiceRunsSuccessfully() {

        StressTestRequestDTO request = new StressTestRequestDTO();
        request.pensionBalance = 100_000;
        request.defaultGrowthRate = 0.06;
        request.mortgageBalance = 50_000;
        request.defaultInterestRate = 0.04;
        request.mortgageMonthlyPayment = 500;
        request.monthlyDrawdown = 1500;

        StressTestResultDTO result = stressTestService.runStressTest(request);

        assertTrue(result.monthsUntilDepletion > 0);
        assertTrue(result.startYear > 0);
    }
}

