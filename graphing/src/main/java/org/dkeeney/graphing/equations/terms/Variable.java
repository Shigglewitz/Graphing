package org.dkeeney.graphing.equations.terms;

import java.math.BigDecimal;
import java.util.Map;

import org.dkeeney.graphing.equations.Evaluable;
import org.dkeeney.graphing.equations.exceptions.InsufficientVariableInformationException;

public class Variable implements Term {
    private final String variable;

    private Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public double evaluate(Map<String, BigDecimal> varValues) {
        if (varValues.get(this.variable) == null) {
            throw new InsufficientVariableInformationException();
        } else {
            return varValues.get(this.variable).doubleValue();
        }
    }

    public static Evaluable getTerm(String amount) {
        return new Variable(amount);
    }

}
