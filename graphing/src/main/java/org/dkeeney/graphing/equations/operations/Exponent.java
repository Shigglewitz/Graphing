package org.dkeeney.graphing.equations.operations;

import org.dkeeney.graphing.equations.Valuable;

public class Exponent extends Operation {
    protected Exponent(Valuable left, Valuable right) {
        super(left, right);
    }

    protected Exponent() {
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
