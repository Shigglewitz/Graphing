package org.dkeeney.graphing.equations.operations;

import org.dkeeney.graphing.equations.Term;

public abstract class Operation {
    protected Term left;
    protected Term right;

    public Operation(Term left, Term right) {
        this.left = left;
        this.right = right;
    }

    public abstract double evaluate();
}
