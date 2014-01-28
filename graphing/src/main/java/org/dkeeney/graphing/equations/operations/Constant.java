package org.dkeeney.graphing.equations.operations;

import java.math.BigDecimal;
import java.util.Map;

import org.dkeeney.graphing.equations.Evaluable;

public class Constant extends Operation {

    public Constant(Evaluable right) {
        super(right);
    }

    protected Constant() {
    }

    @Override
    public String getOperator() {
        return null;
    }

    @Override
    public double operate(double initialValue,
            Map<String, BigDecimal> variableValues) {
        return this.right.evaluate(variableValues);
    }

}
