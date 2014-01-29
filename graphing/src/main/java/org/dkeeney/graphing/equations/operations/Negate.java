package org.dkeeney.graphing.equations.operations;

import java.math.BigDecimal;
import java.util.Map;

import org.dkeeney.graphing.equations.Token;
import org.dkeeney.graphing.equations.terms.ConstantAmount;
import org.dkeeney.graphing.equations.terms.Term;

public class Negate extends Operation {
    public Negate() {
    }

    @Override
    public ConstantAmount operate(Term[] inputs,
            Map<String, BigDecimal> variableValues) {
        return new ConstantAmount(-1 * inputs[0].evaluate(variableValues));
    }

    @Override
    public String getOperator() {
        return "-";
    }

    @Override
    public Precedence getPrecedence() {
        return Precedence.ADDITION_SUBTRACTION;
    }

    @Override
    public Associativity getAssociativity() {
        return Associativity.LEFT;
    }

    @Override
    public int getNumberOfInputs() {
        return 1;
    }

    @Override
    public Token cloneToken() {
        return new Negate();
    }

}
