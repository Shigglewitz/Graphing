package org.dkeeney.graphing.equations;

import java.util.ArrayList;
import java.util.List;

import org.dkeeney.graphing.equations.operations.Operation;
import org.dkeeney.utils.Utils;

public class Equation implements Valuable {
    private final List<Operation> operations;

    public Equation(String input) {
        this.operations = new ArrayList<Operation>();
        input = Utils.removeAllWhiteSpace(input);
        String[] parsed = Utils.splitWithDelimiter(input,
                Operation.OPERATOR_REGEX);
        this.operations.add(Operation.getOperation(parsed[1], new Term(
                parsed[0]), new Term(parsed[2])));
    }

    @Override
    public double evaluate() {
        double ret = 0;

        for (int i = 0; i < this.operations.size(); i++) {
            ret = this.operations.get(0).evaluate();
        }

        return ret;
    }
}
