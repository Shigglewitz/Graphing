package org.dkeeney.graphing.equations.operations;

import org.dkeeney.graphing.equations.Term;

public class Exponent extends Operation {
    protected Exponent(Term left, Term right) {
        super(left, right);
    }

    @Override
    public double evaluate() {
        return Math.pow(this.left.evaluate(), this.right.evaluate());
    }

    @Override
    public String getOperator() {
        return "^";
    }

}
