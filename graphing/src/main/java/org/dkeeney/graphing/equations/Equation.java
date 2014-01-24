package org.dkeeney.graphing.equations;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.dkeeney.graphing.equations.operations.Addition;
import org.dkeeney.graphing.equations.operations.Division;
import org.dkeeney.graphing.equations.operations.Exponent;
import org.dkeeney.graphing.equations.operations.Multiplication;
import org.dkeeney.graphing.equations.operations.Operation;
import org.dkeeney.graphing.equations.operations.Subtraction;
import org.dkeeney.utils.Utils;

public class Equation implements Valuable {
    private final List<Operation> operations;

    private static String VALID_EQUATION_REGEX = "\\(*[0-9]+\\)*("
            + Operation.OPERATOR_REGEX + "\\(*[0-9]+\\)*)*";
    private static final String IMPLIED_BEFORE_PAREN_REGEX = "([0-9)]\\()";
    private static final String IMPLIED_AFTER_PAREN_REGEX = "(\\)[(0-9])";
    private static final Pattern IMPLIED_BEFORE_PAREN = Pattern
            .compile(IMPLIED_BEFORE_PAREN_REGEX);
    private static final Pattern IMPLIED_AFTER_PAREN = Pattern
            .compile(IMPLIED_AFTER_PAREN_REGEX);

    public Equation(String input) {
        input = Utils.removeAllWhiteSpace(input);
        input = addImpliedMultiplication(input);
        if (!Equation.isValidEquation(input)) {
            throw new InvalidEquationException();
        }
        this.operations = new ArrayList<Operation>();
        this.recursivelyAddOperations(input);
        System.out.println("Equation for " + input);
    }

    public static String addImpliedMultiplication(String equation) {
        Matcher before = IMPLIED_BEFORE_PAREN.matcher(equation);
        String ret = "";
        int position = 0;
        while (before.find()) {
            ret += equation.substring(position, before.start())
                    + before.group(1).replace("(",
                            Multiplication.OPERATOR + "(");
            position = before.end();
        }

        if (position != 0) {
            ret += equation.substring(position, equation.length());
            position = 0;
            equation = ret;
            ret = "";
        }

        Matcher after = IMPLIED_AFTER_PAREN.matcher(equation);
        while (after.find()) {
            ret += equation.substring(position, after.start())
                    + after.group(1)
                            .replace(")", ")" + Multiplication.OPERATOR);
            position = after.end();
        }

        if (position != 0) {
            ret += equation.substring(position, equation.length());
            position = 0;
            equation = ret;
            ret = "";
        }

        return equation;
    }

    public static boolean isValidEquation(String equation) {
        if (equation == null || "".equals(equation)) {
            return false;
        }
        return equation.matches(VALID_EQUATION_REGEX)
                && StringUtils.countMatches(equation, "(") == StringUtils
                        .countMatches(equation, ")");
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
        Class<?>[][] orderOfOperations = { { Exponent.class },
                { Multiplication.class, Division.class },
                { Addition.class, Subtraction.class } };

        for (Class<?>[] orderOfOperation : orderOfOperations) {
            boolean shouldEvaluate = false;
            for (int j = 0; j < this.operations.size(); j++) {
                shouldEvaluate = false;
                for (Class<?> clazz : orderOfOperation) {
                    if (clazz.isInstance(this.operations.get(j))) {
                        shouldEvaluate = true;
                    }
                }
                if (shouldEvaluate) {
                    this.evaluateAndDelete(j);
                    j--;
                }
            }
        }

        // should be down to just a constant
        ret = this.operations.get(0).evaluate();

        return ret;
    }

    private void evaluateAndDelete(int index) {
        double value = this.operations.get(index).evaluate();
        if (index > 0) {
            this.operations.get(index - 1).setRight(new Term(value));
        }
        if (index < this.operations.size() - 1) {
            this.operations.get(index + 1).setLeft(new Term(value));
        }
        this.operations.remove(index);
    }
}
