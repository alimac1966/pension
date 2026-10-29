package org.macnair.pension.infrastructure.dto;


public class ProjectionSummaryDTO {
    public int myAge;
    public double taxFreePension;
    public double taxablePension;
    public double spending;
    public double incomeTFPension;
    public double incomeTaxPension;
    public double incomeStatePension;
    public double incomeOther;

    public ProjectionSummaryDTO() {}

    public ProjectionSummaryDTO(int myAge,
                               double taxFreePension,
                               double taxablePension,
                               double spending,
                               double incomeTFPension,
                               double incomeTaxPension,
                               double incomeStatePension,
                               double incomeOther) {
        this.myAge = myAge;
        this.taxFreePension = taxFreePension;
        this.taxablePension = taxablePension;
        this.spending = spending;
        this.incomeTFPension = incomeTFPension;
        this.incomeTaxPension = incomeTaxPension;
        this.incomeStatePension = incomeStatePension;
        this.incomeOther = incomeOther;
    }
}

