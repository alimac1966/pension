package org.macnair.pension.application;


import org.macnair.pension.domain.RetirementScenario;
import org.macnair.pension.domain.Mortgage;
import org.macnair.pension.domain.PensionPot;
import org.macnair.pension.infrastructure.dto.StressTestRequestDTO;

import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.time.LocalDate;

@ApplicationScoped
public class StressTestMapper {

    public RetirementScenario toDomain(StressTestRequestDTO dto) {

        PensionPot pot = new PensionPot.Builder()
                .currentBalance(BigDecimal.valueOf(dto.pensionBalance))
                .annualGrowthRate(dto.defaultGrowthRate)
                .build();

        Mortgage mortgage = new Mortgage.Builder()
                .outstandingBalance(BigDecimal.valueOf(dto.mortgageBalance))
                .annualInterestRate(dto.defaultInterestRate)
                .monthlyPayment(BigDecimal.valueOf(dto.mortgageMonthlyPayment))
                .build();

        return new RetirementScenario.Builder()
                .pensionPot(pot)
                .mortgage(mortgage)
                .monthlyDrawdown(BigDecimal.valueOf(dto.monthlyDrawdown))
                .retirementStartDate(LocalDate.now())
                .build();
    }
}
