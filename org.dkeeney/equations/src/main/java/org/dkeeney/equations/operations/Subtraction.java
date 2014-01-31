package org.dkeeney.equations.operations;

import java.math.BigDecimal;
import java.util.Map;

import org.dkeeney.equations.Token;
import org.dkeeney.equations.terms.ConstantAmount;
import org.dkeeney.equations.terms.Term;

public class Subtraction extends Operation {
    public static final String OPERATOR = "-";

    public Subtraction() {
    }

    @Override
    public String getOperator() {
        return OPERATOR;
    }

    @Override
    public ConstantAmount operate(Term[] inputs,
            Map<String, BigDecimal> variableValues) {
        return new ConstantAmount(inputs[1].evaluate(variableValues)
                - inputs[0].evaluate(variableValues));
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
        return 2;
    }

    @Override
    public Token cloneToken() {
        return new Subtraction();
    }

}
