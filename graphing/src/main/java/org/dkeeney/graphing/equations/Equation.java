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
    private static final String VARIABLE_REGEX = "[a-z]";
    private static final String NUMBER_REGEX = "[0-9]+([.][0-9]+)?";
    private static final String NON_OPERATOR_REGEX = "((" + NUMBER_REGEX + ")|"
            + VARIABLE_REGEX + ")";
    private static final String VALID_EQUATION_REGEX = "\\(*"
            + NON_OPERATOR_REGEX + "\\)*(" + Operation.OPERATOR_REGEX + "\\(*"
            + NON_OPERATOR_REGEX + "\\)*)*";
    private static final String IMPLIED_BEFORE_PAREN_REGEX = "([a-z0-9)]\\()";
    private static final String IMPLIED_AFTER_PAREN_REGEX = "(\\)[(0-9a-z])";
    private static final String IMPLIED_WITH_VAR_REGEX = "((" + NUMBER_REGEX
            + ")|" + VARIABLE_REGEX + ")(?=" + VARIABLE_REGEX + ")";
    private static final Pattern IMPLIED_BEFORE_PAREN = Pattern
            .compile(IMPLIED_BEFORE_PAREN_REGEX);
    private static final Pattern IMPLIED_AFTER_PAREN = Pattern
            .compile(IMPLIED_AFTER_PAREN_REGEX);
    private static final Pattern IMPLIED_WITH_VAR = Pattern
            .compile(IMPLIED_WITH_VAR_REGEX);

    private final String equation;
    private final List<String> operations;

    public Equation(String input) {
        this.equation = input;
        input = Utils.removeAllWhiteSpace(input);
        input = addImpliedMultiplication(input);
        if (!Equation.isValidEquation(input)) {
            throw new InvalidEquationException();
        }
        this.operations = this.reduce(input);
    }

    public static String addImpliedMultiplication(String equation) {
        if (equation == null) {
            return null;
        }

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

        Matcher variables = IMPLIED_WITH_VAR.matcher(equation);
        while (variables.find()) {
            ret += equation.substring(position, variables.start())
                    + variables.group(0) + Multiplication.OPERATOR;
            position = variables.end();
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
        return this.evaluate();
    }

    public String getEquation() {
        return this.equation;
    }

    private String flatten(List<String> input) {
        String ret = "";
        for (String s : input) {
            ret += s;
        }

        return ret;
    }

    private List<String> reduce(String equation) {
        while (equation.indexOf(')') > -1) {
            int closeParen = equation.indexOf(')');
            int openParen = equation.substring(0, closeParen).lastIndexOf('(');
            String parenValue = this.flatten(this.reduce(equation.substring(
                    openParen + 1, closeParen)));
            equation = equation.substring(0, openParen) + parenValue
                    + equation.substring(closeParen + 1);
        }

        String[] parse = Utils.splitWithDelimiter(equation,
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
                                        .getDeclaredConstructor(Term.class,
                                                Term.class);
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

        return parsedList;
    }

    private double evaluate() {
        return Double.parseDouble(this.operations.get(0));
    }
}
