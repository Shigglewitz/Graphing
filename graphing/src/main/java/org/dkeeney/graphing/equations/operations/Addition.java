package org.dkeeney.graphing.equations.operations;

import org.dkeeney.graphing.equations.Term;

public class Addition extends Operation {
    protected Addition(Term left, Term right) {
        super(left, right);
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
