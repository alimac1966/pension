package org.macnair.pension.domain;

public class DetailedStressTest {

    private int startYear;
    private double monthlyDrawdown;
    private double defaultGrowthRate;
    private double defaultInterestRate;
    private double mortgageBalance;
    private double mortgageMonthlyPayment;

    public int getStartYear() { return startYear; }
    public void setStartYear(int startYear) { this.startYear = startYear; }

    public double getMonthlyDrawdown() { return monthlyDrawdown; }
    public void setMonthlyDrawdown(double monthlyDrawdown) { this.monthlyDrawdown = monthlyDrawdown; }

    public double getDefaultGrowthRate() { return defaultGrowthRate; }
    public void setDefaultGrowthRate(double defaultGrowthRate) { this.defaultGrowthRate = defaultGrowthRate; }

    public double getDefaultInterestRate() { return defaultInterestRate; }
    public void setDefaultInterestRate(double defaultInterestRate) { this.defaultInterestRate = defaultInterestRate; }

    public double getMortgageBalance() { return mortgageBalance; }
    public void setMortgageBalance(double mortgageBalance) { this.mortgageBalance = mortgageBalance; }

    public double getMortgageMonthlyPayment() { return mortgageMonthlyPayment; }
    public void setMortgageMonthlyPayment(double mortgageMonthlyPayment) { this.mortgageMonthlyPayment = mortgageMonthlyPayment; }
}
