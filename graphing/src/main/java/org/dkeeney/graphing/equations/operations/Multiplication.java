package org.dkeeney.graphing.equations.operations;

import org.dkeeney.graphing.equations.Valuable;

public class Multiplication extends Operation {
    protected Multiplication(Valuable left, Valuable right) {
        super(left, right);
    }

    protected Multiplication() {
    }

    @Override
    public double evaluate() {
        return this.left.evaluate() * this.right.evaluate();
    }

    @Override
    public String getOperator() {
        return "*";
    }

}
