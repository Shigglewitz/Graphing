package org.dkeeney.graphing.equations.operations;

import org.dkeeney.graphing.equations.Term;

public class Addition extends Operation {

    public Addition(Term left, Term right) {
        super(left, right);
    }

    @Override
    public double evaluate() {
        return this.left.valueOf() + this.right.valueOf();
    }

}
