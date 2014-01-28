package org.dkeeney.graphing.equations.terms;

import java.math.BigDecimal;
import java.util.Map;

import org.dkeeney.graphing.equations.Evaluable;

public class ConstantAmount implements Term {
    private final double amount;

    private ConstantAmount(String amount) {
        this.amount = Double.parseDouble(amount);
    }

    @Override
    public double evaluate(Map<String, BigDecimal> varValues) {
        return this.amount;
    }

    public static Evaluable getTerm(String amount) {
        return new ConstantAmount(amount);
    }

}
