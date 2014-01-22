package org.dkeeney.graphing.equations.operations;

import org.dkeeney.graphing.equations.Term;

public class Constant extends Operation {

    protected Constant(Term left, Term right) {
        super(left, right);
    }

    @Override
    public double evaluate() {
        return this.right == null ? this.left.evaluate() : this.right
                .evaluate();
    }

    @Override
    public String getOperator() {
        return null;
    }

}
