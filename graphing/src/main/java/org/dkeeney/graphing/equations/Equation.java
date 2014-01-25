package org.dkeeney.graphing.equations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.dkeeney.graphing.equations.operations.Multiplication;
import org.dkeeney.graphing.equations.operations.Operation;
import org.dkeeney.utils.Utils;

public class Equation {
    private static String VALID_EQUATION_REGEX = "\\(*[0-9]+\\)*("
            + Operation.OPERATOR_REGEX + "\\(*[0-9]+\\)*)*";
    private static final String IMPLIED_BEFORE_PAREN_REGEX = "([0-9)]\\()";
    private static final String IMPLIED_AFTER_PAREN_REGEX = "(\\)[(0-9])";
    private static final Pattern IMPLIED_BEFORE_PAREN = Pattern
            .compile(IMPLIED_BEFORE_PAREN_REGEX);
    private static final Pattern IMPLIED_AFTER_PAREN = Pattern
            .compile(IMPLIED_AFTER_PAREN_REGEX);

    private final String equation;

    public Equation(String input) {
        input = Utils.removeAllWhiteSpace(input);
        input = addImpliedMultiplication(input);
        if (!Equation.isValidEquation(input)) {
            throw new InvalidEquationException();
        }
        this.equation = input;
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

    public double solve() {
        return this.evaluate(this.equation);
    }

    private double evaluate(String evaluate) {
        while (evaluate.indexOf(')') > -1) {
            int closeParen = evaluate.indexOf(')');
            int openParen = evaluate.substring(0, closeParen).lastIndexOf('(');
            double parenValue = this.evaluate(evaluate.substring(openParen + 1,
                    closeParen));
            evaluate = evaluate.substring(0, openParen)
                    + Double.toString(parenValue)
                    + evaluate.substring(closeParen + 1);
        }

        String[] parse = Utils.splitWithDelimiter(evaluate,
                Operation.OPERATOR_REGEX);
        List<String> parsedList = new ArrayList<String>();
        for (String s : parse) {
            parsedList.add(s);
        }
        boolean shouldEvaluate = false;
        for (Operation[] ops : Operation.getOrderOfOperations()) {
            for (int i = 0; i < parsedList.size(); i++) {
                shouldEvaluate = false;
                if (Operation.isOperator(parsedList.get(i))) {
                    for (Operation o : ops) {
                        if (o.getOperator().equals(parsedList.get(i))) {
                            shouldEvaluate = true;
                        }
                    }

                    if (shouldEvaluate) {
                        if (i == 0) {
                            throw new InvalidEquationException(
                                    "Operator found without a left operand.");
                        } else if (i >= parsedList.size() - 1) {
                            throw new InvalidEquationException(
                                    "Operator found without a right operand.");
                        } else {
                            try {
                                Constructor<? extends Operation> constructor = Operation
                                        .determineOperation(parsedList.get(i))
                                        .getDeclaredConstructor(Valuable.class,
                                                Valuable.class);
                                constructor.setAccessible(true);
                                double value = constructor.newInstance(
                                        new Term(parsedList.get(i - 1)),
                                        new Term(parsedList.get(i + 1)))
                                        .evaluate();
                                parsedList.set(i - 1, Double.toString(value));
                                parsedList.remove(i);
                                parsedList.remove(i);
                                i--;
                            } catch (InstantiationException
                                    | IllegalAccessException
                                    | IllegalArgumentException
                                    | InvocationTargetException
                                    | NoSuchMethodException | SecurityException e) {
                                e.printStackTrace();
                                throw new InvalidEquationException(
                                        "Exception evaluating expression", e);
                            }
                        }
                    }
                }
            }
        }

        // should be down to just a constant now

        return Double.parseDouble(parsedList.get(0));
    }
}
