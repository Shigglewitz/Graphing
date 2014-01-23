package org.dkeeney.graphing.equations.operations;

import org.dkeeney.graphing.equations.Valuable;

public class Division extends Operation {
    protected Division(Valuable left, Valuable right) {
        super(left, right);
    }

    protected Division() {
    }

    @Override
    public double evaluate() {
        return this.left.evaluate() / this.right.evaluate();
    }

    @Override
    public String getOperator() {
        return "/";
    }

}
