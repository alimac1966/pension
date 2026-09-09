package org.macnair.pension.domain;

public class StressTestResult {

    private final int monthsUntilDepletion;
    private final int startYear;

    public StressTestResult(int startYear, int monthsUntilDepletion) {
        this.startYear = startYear;
        this.monthsUntilDepletion = monthsUntilDepletion;
    }

    public int getMonthsUntilDepletion() {
        return monthsUntilDepletion;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getYearsUntilDepletion() {
        return monthsUntilDepletion / 12;
    }
}

