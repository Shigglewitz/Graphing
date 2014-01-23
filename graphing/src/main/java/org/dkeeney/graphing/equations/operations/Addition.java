package org.dkeeney.graphing.equations.operations;

import org.dkeeney.graphing.equations.Valuable;

public class Addition extends Operation {
    protected Addition(Valuable left, Valuable right) {
        super(left, right);
    }

    protected Addition() {
    }

    @Override
    public double evaluate() {
        return this.left.evaluate() + this.right.evaluate();
    }

    @Override
    public String getOperator() {
        return "+";
    }

}
