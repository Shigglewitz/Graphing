package org.dkeeney.graphing.equations.operations;

import org.dkeeney.graphing.equations.Valuable;

public class Constant extends Operation {

    protected Constant(Valuable left, Valuable right) {
        super(left, right);
    }

    protected Constant() {
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
