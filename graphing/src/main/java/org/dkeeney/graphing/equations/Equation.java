package org.dkeeney.graphing.equations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.dkeeney.graphing.equations.exceptions.EvaluationException;
import org.dkeeney.graphing.equations.operations.Multiplication;
import org.dkeeney.graphing.equations.operations.Operation;
import org.dkeeney.graphing.equations.operations.Subtraction;
import org.dkeeney.graphing.equations.terms.ConstantAmount;
import org.dkeeney.graphing.equations.terms.Variable;
import org.dkeeney.utils.Utils;

public class Equation implements Evaluable {
    public static final String VARIABLE_REGEX = "[a-z]";
    private static final String NUMBER_REGEX = "-?[0-9]+(\\.[0-9]+)?";
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

    private final String originalEquation;
    private final List<Operation> operations;
    private final NumberFormat nf;

    private static final int DEFAULT_NUM_DECIMAL_DIGITS = 5;

    public Equation(String input) {
        this(input, DEFAULT_NUM_DECIMAL_DIGITS);
    }

    public Equation(String input, int maxDecimalDigits) {
        this.originalEquation = input;
        input = Utils.removeAllWhiteSpace(input);
        input = addImpliedMultiplication(input);
        // if (!Equation.isValidEquation(input)) {
        // throw new InvalidEquationException();
        // }
        this.operations = parseOperations(input);
        this.nf = NumberFormat.getInstance();
        this.nf.setMinimumIntegerDigits(1);
        this.nf.setMinimumFractionDigits(0);
        this.nf.setMaximumFractionDigits(maxDecimalDigits);
    }

    public static List<Operation> parseOperationsWithoutParens(String equation) {
        List<Operation> ret = new ArrayList<>();

        String[] parse = Utils.splitWithDelimiter(equation,
                Operation.OPERATOR_REGEX);
        List<String> parsedList = new ArrayList<String>();
        Constructor<? extends Operation> constructor;
        Evaluable evaluable;
        boolean shouldEvaluate = false;

        for (int i = 0; i < parse.length; i++) {
            if ("".equals(parse[i])) {
                continue;
            }
            if (Subtraction.OPERATOR.equals(parse[i])
                    && (parsedList.size() == 0 || Operation
                            .isOperator(parse[i - 1])) && i < parse.length - 1) {
                parsedList.add(parse[i] + parse[++i]);
            } else {
                parsedList.add(parse[i]);
            }
        }

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
                        if (i == parsedList.size() - 1) {
                            evaluable = null;
                        } else if (parsedList.get(i).matches(NUMBER_REGEX)) {
                            evaluable = ConstantAmount.getTerm(parsedList
                                    .get(i + 1));
                        } else {
                            evaluable = Variable.getTerm(parsedList.get(i + 1));
                        }
                        try {
                            constructor = Operation.determineOperation(
                                    parsedList.get(i)).getDeclaredConstructor(
                                    Evaluable.class);
                            constructor.setAccessible(true);
                            ret.add(constructor.newInstance(evaluable));
                            // parsedList.set(i - 1, Double.toString(value));
                            parsedList.remove(i);
                            parsedList.remove(i);
                            i--;
                        } catch (InstantiationException
                                | IllegalAccessException
                                | IllegalArgumentException
                                | InvocationTargetException
                                | NoSuchMethodException | SecurityException e) {
                            e.printStackTrace();
                            throw new EvaluationException(
                                    "Exception evaluating expression "
                                            + equation, e);
                        }
                    }
                }
            }
        }

        return ret;
    }

    public static List<Operation> parseOperations(String equation) {
        List<Operation> ret;

        if (equation.indexOf(')') > -1) {
            int[] range = Utils.getMatchedGroup(equation, '(', ')');
            String beforeParens = equation.substring(0, range[0]);
            String inParens = equation.substring(range[0] + 1, range[1]);
            String afterParens = equation.substring(range[1] + 1);
            if (!"".equals(beforeParens)) {
                ret = parseOperations(beforeParens);
                ret.get(ret.size() - 1).setRight(new Equation(inParens));
            } else {
                ret = new Equation(inParens).getOperations();
            }
            if (!"".equals(afterParens)) {
                List<Operation> after = parseOperations(afterParens);
                for (Operation o : after) {
                    ret.add(o);
                }
            }
        } else {
            ret = parseOperationsWithoutParens(equation);
        }

        return ret;
    }

    public void adjustPrecision(int minIntDigits, int maxIntDigits,
            int minDecDigits, int maxDecDigits) {
        if (minIntDigits >= 0) {
            this.nf.setMinimumIntegerDigits(minIntDigits);
        }
        if (maxIntDigits >= 0) {
            this.nf.setMaximumIntegerDigits(maxIntDigits);
        }
        if (minDecDigits >= 0) {
            this.nf.setMinimumFractionDigits(minDecDigits);
        }
        if (maxDecDigits >= 0) {
            this.nf.setMaximumFractionDigits(maxDecDigits);
        }
    }

    public String formatDouble(double value) {
        return this.nf.format(value);
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

    public double solve(Map<String, BigDecimal> variableValues) {
        return 0;
    }

    public static boolean isValidEquation(String equation) {
        if (equation == null || "".equals(equation)) {
            return false;
        }
        return equation.matches(VALID_EQUATION_REGEX)
                && StringUtils.countMatches(equation, "(") == StringUtils
                        .countMatches(equation, ")");
    }

    public String getOriginalEquation() {
        return this.originalEquation;
    }

    public List<Operation> getOperations() {
        return this.operations;
    }

    @Override
    public double evaluate(Map<String, BigDecimal> varValues) {
        double ret = 0;

        // constants should always be the first operation, and they ignore
        // what's passed in
        for (Operation o : this.operations) {
            ret = o.operate(ret, varValues);
        }
        return ret;
    }

}
