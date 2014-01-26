package org.dkeeney.graphing;

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

    public void calculateValues(double minRange, double maxRange, double delta,
            String var) throws InsufficientVariableInformationException {
        Map<String, Double> vars = new HashMap<>();
        this.values = new double[(int) Math.floor((maxRange - minRange) / delta
                + .5)];

        int i = 0;
        for (double eval = minRange; eval <= maxRange; eval += delta, i++) {
            vars.put(var, eval);
            this.values[i] = this.e.solve(vars);
        }
    }

    public double[] getValues() {
        return this.values;
    }
}
