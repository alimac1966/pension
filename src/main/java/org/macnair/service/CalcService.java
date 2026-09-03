package org.macnair.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.macnair.model.CalcResult;

@ApplicationScoped
public class CalcService {

    @ConfigProperty(name = "calc.value1")
    double value1;

    @ConfigProperty(name = "calc.value2")
    double value2;

    public CalcResult calculate() {
        double answer = value1 * value2;
        double rounded = Math.round(answer * 100.0) / 100.0;

        return new CalcResult(value1, value2, rounded);
    }

    public CalcResult calculateDynamic(double x, double y) {
        double answer = x * y;
        double rounded = Math.round(answer * 100.0) / 100.0;

        return new CalcResult(x, y, rounded);
    }
}
