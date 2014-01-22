package org.dkeeney.graphing.equations.operations;

import org.dkeeney.graphing.equations.Term;

public class Subtraction extends Operation {

    public Subtraction(Term left, Term right) {
        super(left, right);
    }

    @Override
    public double evaluate() {
        return this.left.valueOf() - this.right.valueOf();
    }

}
