package org.dkeeney.equations.operations.trigonometry;

import java.math.BigDecimal;
import java.util.Map;

import org.dkeeney.equations.Token;
import org.dkeeney.equations.terms.ConstantAmount;
import org.dkeeney.equations.terms.Term;

public class Cosine extends TrigonometricOperation {

    @Override
    public Token cloneToken() {
        return new Cosine();
    }

    @Override
    public ConstantAmount operate(Term[] inputs,
            Map<String, BigDecimal> variableValues) {
        return new ConstantAmount(Math.cos(this.convertInput(inputs[0]
                .evaluate(variableValues))));
    }

    @Override
    public String getOperator() {
        return "cos";
    }
}
