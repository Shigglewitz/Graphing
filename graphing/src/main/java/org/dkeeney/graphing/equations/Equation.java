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
        this.recursivelyAddOperations(input);
    }

    private void recursivelyAddOperations(String input) {
        String[] parsed = null;

        do {
            parsed = Utils.splitWithDelimiter(input, Operation.OPERATOR_REGEX,
                    3);
            if (parsed.length == 1) {
                this.operations.add(Operation.getOperation(null, new Term(
                        parsed[0]), null));
            } else {
                this.operations
                        .add(Operation.getOperation(
                                parsed[1],
                                new Term(parsed[0]),
                                new Term(parsed[2]
                                        .split(Operation.OPERATOR_REGEX)[0])));
                input = parsed[2];
            }
        } while (parsed.length > 1);
    }

    @Override
    public double evaluate() {
        double ret = 0;

        for (int i = 0; i < this.operations.size(); i++) {
            ret = this.operations.get(i).evaluate();
            if (i < this.operations.size() - 1) {
                this.operations.get(i + 1).setLeft(new Term(ret));
            }
        }

        return ret;
    }
}
