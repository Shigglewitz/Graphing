package org.dkeeney.graphing.equations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dkeeney.graphing.equations.exceptions.EvaluationException;
import org.dkeeney.graphing.equations.operations.Constant;
import org.dkeeney.graphing.equations.operations.Multiplication;
import org.dkeeney.graphing.equations.operations.Operation;
import org.dkeeney.graphing.equations.operations.Subtraction;
import org.dkeeney.graphing.equations.terms.ConstantAmount;
import org.dkeeney.graphing.equations.terms.Variable;
import org.dkeeney.utils.Utils;

/**
 * going to switch to djikstra's algorithm:
 * 
 * need:
 * 1 stack for operators
 * 1 queue for output
 * 1 array (or other list) for tokens
 * 
 * algorithm:
 *  while there are tokens to read
 *      read a token
 *      if it's a number, add to queue
 *      if it's an operator:
 *          while there's an operator on top of stack with greater precedence:
 *              pop operators from the stack onto the output queue
 *          push the current operator onto the stack
 *      if it's a left bracket '(', push it onto the stack
 *      if it's a right bracket ')'
 *          while there's not a left bracket at the top of the stack
 *              pop operators from the stack onto the output queue
 *          pop left bracket from the stack but discard
 *  while there are operators on the stack, pop them to the queue    
 * @author Daniel
 *
 */
public class Equation implements Evaluable {
    public static final String VARIABLE_REGEX = "[a-z]";
    private static final String NUMBER_REGEX = "-?[0-9]+(\\.[0-9]+)?";
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

    public Equation(String input) {
        this.originalEquation = input;
        input = Utils.removeAllWhiteSpace(input);
        input = addImpliedMultiplication(input);
        this.operations = parseOperations(input);
        this.orderOperations(this.operations);
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

    public static List<Operation> parseOperationsWithoutParens(String equation) {
        List<Operation> ret = new ArrayList<>();

        String[] parse = Utils.splitWithDelimiter(equation,
                Operation.OPERATOR_REGEX);
        List<String> parsedList = new ArrayList<String>();
        Constructor<? extends Operation> constructor;
        Evaluable evaluable;

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

        if (parsedList.get(0).matches(NUMBER_REGEX)) {
            ret.add(new Constant(ConstantAmount.getTerm(parsedList.get(0))));
            parsedList.remove(0);
        }

        for (int i = 0; i < parsedList.size(); i++) {
            if (Operation.isOperator(parsedList.get(i))) {
                if (i == parsedList.size() - 1) {
                    evaluable = null;
                } else {
                    evaluable = getEvaluable(parsedList.get(i + 1));
                }
                try {
                    constructor = Operation.determineOperation(
                            parsedList.get(i)).getDeclaredConstructor(
                            Evaluable.class);
                    constructor.setAccessible(true);
                    ret.add(constructor.newInstance(evaluable));
                    // parsedList.set(i - 1, Double.toString(value));
                    parsedList.remove(i);
                    if (evaluable != null) {
                        parsedList.remove(i);
                    }
                    i--;
                } catch (InstantiationException | IllegalAccessException
                        | IllegalArgumentException | InvocationTargetException
                        | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                    throw new EvaluationException(
                            "Exception evaluating expression " + equation, e);
                }
            }
        }

        return ret;
    }

    public static Evaluable getEvaluable(String input) {
        if (input.matches(NUMBER_REGEX)) {
            return ConstantAmount.getTerm(input);
        } else {
            return Variable.getTerm(input);
        }
    }

    public void orderOperations(List<Operation> operations) {
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
