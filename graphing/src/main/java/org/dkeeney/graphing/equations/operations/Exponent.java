package org.dkeeney.graphing.equations.operations;

import java.math.BigDecimal;
import java.util.Map;

import org.dkeeney.graphing.equations.Evaluable;

public class Exponent extends Operation {
    protected Exponent(Evaluable right) {
        super(right);
    }

    protected Exponent() {
    }

    @Override
    public String getOperator() {
        return "^";
    }

    @Override
    public double operate(double initialValue,
            Map<String, BigDecimal> variableValues) {
        return Math.pow(initialValue, this.right.evaluate(variableValues));
    }

}
