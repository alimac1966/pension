package org.macnair.pension.infrastructure.dto;

public class PensionRequestDTO {

    public double pensionBalance;
    public double growthRate;               // e.g. 0.06 for 6%

    public double mortgageBalance;
    public double mortgageInterestRate;     // e.g. 0.04 for 4%
    public double mortgageMonthlyPayment;

    public double monthlyDrawdown;
}
