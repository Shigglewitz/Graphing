package org.dkeeney.graphing.equations.operations.trigonometry;

import java.math.BigDecimal;
import java.util.Map;

import org.dkeeney.graphing.equations.Token;
import org.dkeeney.graphing.equations.terms.ConstantAmount;
import org.dkeeney.graphing.equations.terms.Term;

public class Sine extends TrigonometricOperation {

    @Override
    public ConstantAmount operate(Term[] inputs,
            Map<String, BigDecimal> variableValues) {
        return new ConstantAmount(Math.sin(this.convertInput(inputs[0]
                .evaluate(variableValues))));
    }

    @Override
    public String getOperator() {
        return "sin";
    }

    @Override
    public Token cloneToken() {
        return new Sine();
    }

}
