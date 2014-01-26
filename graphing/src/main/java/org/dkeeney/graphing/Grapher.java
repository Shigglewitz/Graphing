package org.dkeeney.graphing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.dkeeney.graphing.equations.Equation;
import org.dkeeney.graphing.equations.exceptions.InsufficientVariableInformationException;
import org.dkeeney.graphing.equations.exceptions.InvalidEquationException;

public class Grapher {
    private final Equation e;
    private double[] values;

    public Grapher(String equation) throws InvalidEquationException {
        this.e = new Equation(equation);
        this.values = null;
    }

    public void calculateValues(BigDecimal minRange, BigDecimal maxRange,
            BigDecimal delta, String var)
            throws InsufficientVariableInformationException {
        Map<String, BigDecimal> vars = new HashMap<>();
        this.values = new double[(int) Math.floor(maxRange.subtract(minRange)
                .divide(delta, RoundingMode.CEILING).add(new BigDecimal(0.5))
                .doubleValue())];

        int i = 0;
        for (BigDecimal eval = minRange; eval.compareTo(maxRange) < 0; eval = eval
                .add(delta), i++) {
            vars.put(var, eval);
            this.values[i] = this.e.solve(vars);
        }
    }

    public double[] getValues() {
        return this.values;
    }
}
