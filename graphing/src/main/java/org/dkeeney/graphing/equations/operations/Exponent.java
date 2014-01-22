package org.dkeeney.graphing.equations.operations;

import org.dkeeney.graphing.equations.Term;

public class Exponent extends Operation {

    public Exponent(Term left, Term right) {
        super(left, right);
    }

    @Override
    public double evaluate() {
        return Math.pow(this.left.valueOf(), this.right.valueOf());
    }

}
