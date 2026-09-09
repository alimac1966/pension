package org.macnair.pension.domain;

public class PensionProjection {

    private final int monthsUntilDepletion;

    public PensionProjection(int monthsUntilDepletion) {
        this.monthsUntilDepletion = monthsUntilDepletion;
    }

    public int getMonthsUntilDepletion() {
        return monthsUntilDepletion;
    }

    public int getYearsUntilDepletion() {
        return monthsUntilDepletion / 12;
    }
}
