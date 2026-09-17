package org.macnair.pension.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.macnair.pension.domain.*;
import org.macnair.pension.infrastructure.dto.*;

import java.util.stream.Collectors;

@ApplicationScoped
public class DetailedStressTestMapper {

    public DetailedStressTest toDomain(DetailedStressTestRequestDTO dto) {
        DetailedStressTest domain = new DetailedStressTest();
        domain.setStartYear(dto.startYear);
        domain.setMonthlyDrawdown(dto.monthlyDrawdown);
        domain.setDefaultGrowthRate(dto.defaultGrowthRate);
        domain.setDefaultInterestRate(dto.defaultInterestRate);
        domain.setMortgageBalance(dto.mortgageBalance);
        domain.setMortgageMonthlyPayment(dto.mortgageMonthlyPayment);
        return domain;
    }

    public DetailedStressTestSummaryDTO toDetailedSummaryDTO(DetailedStressTestSummary summary) {
        DetailedStressTestSummaryDTO dto = new DetailedStressTestSummaryDTO();
        dto.startYear = summary.getStartYear();
        dto.monthsUntilDepletion = summary.getMonthsUntilDepletion();
        dto.yearsUntilDepletion = summary.getYearsUntilDepletion();

        dto.monthlyBreakdown = summary.getMonthlyBreakdown().stream()
                .map(m -> {
                    MonthlyResultDTO mdto = new MonthlyResultDTO();
                    mdto.month = m.getMonth();
                    mdto.balance = m.getBalance();
                    return mdto;
                })
                .collect(Collectors.toList());

        return dto;
    }
}
