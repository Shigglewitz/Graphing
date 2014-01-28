package org.dkeeney.graphing.equations.operations;

import java.math.BigDecimal;
import java.util.Map;

import org.dkeeney.graphing.equations.Evaluable;

public class Addition extends Operation {
    protected Addition(Evaluable right) {
        super(right);
    }

    protected Addition() {
    }

    @Override
    public String getOperator() {
        return "+";
    }

    @Override
    public double operate(double initialValue,
            Map<String, BigDecimal> variableValues) {
        return initialValue + this.right.evaluate(variableValues);
    }

}
