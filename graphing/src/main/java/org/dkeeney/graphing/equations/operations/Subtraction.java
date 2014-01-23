package org.dkeeney.graphing.equations.operations;

import org.dkeeney.graphing.equations.Valuable;

public class Subtraction extends Operation {
    protected Subtraction(Valuable left, Valuable right) {
        super(left, right);
    }

    protected Subtraction() {
    }

    @Override
    public double evaluate() {
        return this.left.evaluate() - this.right.evaluate();
    }

    @Override
    public String getOperator() {
        return "-";
    }

}
