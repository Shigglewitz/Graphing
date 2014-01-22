package org.dkeeney.graphing.equations;

public class Term implements Valuable {
    private final double value;

    public Term(String value) {
        this(Double.valueOf(value));
    }

    public Term(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return this.value;
    }
}
