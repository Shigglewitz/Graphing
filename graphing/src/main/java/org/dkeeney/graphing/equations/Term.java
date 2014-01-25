package org.dkeeney.graphing.equations;

public class Term {
    private final double value;

    public Term(String value) {
        this(Double.valueOf(value));
    }

    public Term(double value) {
        this.value = value;
    }

    public double evaluate() {
        return this.value;
    }
}
