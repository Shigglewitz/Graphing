package org.dkeeney.graphing.equations.operations;

import java.math.BigDecimal;
import java.util.Map;

import org.dkeeney.graphing.equations.Evaluable;

public class Division extends Operation {
    protected Division(Evaluable right) {
        super(right);
    }

    protected Division() {
    }

    @Override
    public double operate(double initialValue,
            Map<String, BigDecimal> variableValues) {
        return initialValue / this.right.evaluate(variableValues);
    }

    @Override
    public String getOperator() {
        return "/";
    }

}
