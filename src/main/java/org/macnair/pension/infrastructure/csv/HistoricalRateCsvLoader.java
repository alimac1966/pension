package org.macnair.pension.infrastructure.csv;

import org.macnair.pension.domain.HistoricalRate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class HistoricalRateCsvLoader {

    public List<HistoricalRate> load(String resourcePath) {

        List<HistoricalRate> rates = new ArrayList<>();

        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
            if (is == null) {
                throw new IllegalStateException("CSV resource not found: " + resourcePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

                // skip header
                String line = reader.readLine();

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");

                    if (line == null || line.trim().isEmpty()) continue;
                    if (parts.length < 3) continue;

                    int year = Integer.parseInt(parts[0].trim());
                    double growthRate = Double.parseDouble(parts[1].trim());
                    double interestRate = Double.parseDouble(parts[2].trim());

                    rates.add(new HistoricalRate(year, growthRate, interestRate));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load historical rates CSV", e);
        }

        return rates;
    }
}
