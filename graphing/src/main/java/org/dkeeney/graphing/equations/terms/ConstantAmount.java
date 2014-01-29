package org.dkeeney.graphing.equations.terms;

import java.math.BigDecimal;
import java.util.Map;

import org.dkeeney.graphing.equations.Evaluable;
import org.dkeeney.graphing.equations.Token;

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

    public ConstantAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public Token cloneToken() {
        return new ConstantAmount(this.amount);
    }

}
