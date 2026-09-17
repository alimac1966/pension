package org.macnair.pension.domain;

import java.util.List;

public class DetailedStressTestSummary {

    private int startYear;
    private int monthsUntilDepletion;
    private int yearsUntilDepletion;
    private List<MonthlyResult> monthlyBreakdown;

    public int getStartYear() { return startYear; }
    public void setStartYear(int startYear) { this.startYear = startYear; }

    public int getMonthsUntilDepletion() { return monthsUntilDepletion; }
    public void setMonthsUntilDepletion(int monthsUntilDepletion) { this.monthsUntilDepletion = monthsUntilDepletion; }

    public int getYearsUntilDepletion() { return yearsUntilDepletion; }
    public void setYearsUntilDepletion(int yearsUntilDepletion) { this.yearsUntilDepletion = yearsUntilDepletion; }

    public List<MonthlyResult> getMonthlyBreakdown() { return monthlyBreakdown; }
    public void setMonthlyBreakdown(List<MonthlyResult> monthlyBreakdown) { this.monthlyBreakdown = monthlyBreakdown; }
}
