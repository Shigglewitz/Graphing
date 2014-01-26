package org.dkeeney.graphing.equations.operations;

import org.dkeeney.graphing.equations.Term;

public class Subtraction extends Operation {
    public static final String OPERATOR = "-";

    protected Subtraction(Term left, Term right) {
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
        return OPERATOR;
    }

}
