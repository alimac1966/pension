package org.macnair.pension.infrastructure.dto;

import java.util.List;

public class DetailedStressTestSummaryDTO {
    public int startYear;
    public int monthsUntilDepletion;
    public int yearsUntilDepletion;
    public List<MonthlyResultDTO> monthlyBreakdown;
}

