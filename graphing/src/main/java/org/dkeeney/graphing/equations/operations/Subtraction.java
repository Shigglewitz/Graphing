package org.dkeeney.graphing.equations.operations;

import java.math.BigDecimal;
import java.util.Map;

import org.dkeeney.graphing.equations.Evaluable;

public class Subtraction extends Operation {
    public static final String OPERATOR = "-";

    protected Subtraction(Evaluable right) {
        super(right);
    }

    protected Subtraction() {
    }

    @Override
    public String getOperator() {
        return OPERATOR;
    }

    @Override
    public double operate(double initialValue,
            Map<String, BigDecimal> variableValues) {
        return initialValue - this.right.evaluate(variableValues);
    }

}
