package org.macnair.pension.domain;

public class StressTestSummary {

    private final int startYear;
    private final int monthsUntilDepletion;

    public StressTestSummary(int startYear, int monthsUntilDepletion) {
        this.startYear = startYear;
        this.monthsUntilDepletion = monthsUntilDepletion;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getMonthsUntilDepletion() {
        return monthsUntilDepletion;
    }

    public int getYearsUntilDepletion() {
        return monthsUntilDepletion / 12;
    }
}


