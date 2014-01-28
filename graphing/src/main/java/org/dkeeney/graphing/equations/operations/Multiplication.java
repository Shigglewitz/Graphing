package org.dkeeney.graphing.equations.operations;

import java.math.BigDecimal;
import java.util.Map;

import org.dkeeney.graphing.equations.Evaluable;

public class Multiplication extends Operation {
    public static final String OPERATOR = "*";

    protected Multiplication(Evaluable right) {
        super(right);
    }

    protected Multiplication() {
    }

    @Override
    public String getOperator() {
        return OPERATOR;
    }

    @Override
    public double operate(double initialValue,
            Map<String, BigDecimal> variableValues) {
        return initialValue * this.right.evaluate(variableValues);
    }

}
