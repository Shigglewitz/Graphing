package org.dkeeney.graphing.equations.operations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.dkeeney.graphing.equations.Evaluable;
import org.dkeeney.graphing.equations.Token;
import org.dkeeney.graphing.equations.terms.ConstantAmount;
import org.dkeeney.graphing.equations.terms.Term;
import org.dkeeney.utils.Utils;

public abstract class Operation implements Token {
    private static final Operation[] SUPPORTED_OPERATIONS = { new Addition(),
            new Subtraction(), new Multiplication(), new Division(),
            new Exponent() };
    private static Map<String, Class<? extends Operation>> OPERATION_MAP;
    public static final String OPERATOR_REGEX = "[\\^*/+-]";

    public Operation() {
    };

    public enum Associativity {
        LEFT, RIGHT
    }

    public enum Precedence implements Comparable<Precedence> {
        EXPONENT(4), MULTIPLY_DIVIDE(3), ADDITION_SUBTRACTION(2);
        private int precedence;

        private Precedence(int precedence) {
            this.precedence = precedence;
        }

        public int getPrecedence() {
            return this.precedence;
        }
    }

    public abstract ConstantAmount operate(Term[] inputs,
            Map<String, BigDecimal> variableValues);

    public abstract String getOperator();

    public abstract Precedence getPrecedence();

    public abstract Associativity getAssociativity();

    public abstract int getNumberOfInputs();

    public static boolean containsOperator(String input) {
        return Utils.containsRegex(input, OPERATOR_REGEX);
    }

    public static boolean isOperator(String input) {
        return input.matches(OPERATOR_REGEX);
    }

    public static Operation getOperation(String operator, Evaluable right) {
        return createOperation(determineOperation(operator), right);
    }

    public static Class<? extends Operation> determineOperation(String operator) {
        synchronized (Operation.class) {
            if (OPERATION_MAP == null) {
                OPERATION_MAP = initMap();
            }
        }

        return OPERATION_MAP.get(operator);
    }

    private static Operation createOperation(Class<? extends Operation> clazz,
            Evaluable right) {
        Operation o = null;
        try {
            Constructor<? extends Operation> c = clazz
                    .getDeclaredConstructor(Evaluable.class);
            o = c.newInstance(right);
        } catch (InstantiationException | IllegalAccessException
                | NoSuchMethodException | SecurityException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return o;
    }

    private static Map<String, Class<? extends Operation>> initMap() {

        Map<String, Class<? extends Operation>> ret = new HashMap<String, Class<? extends Operation>>();
        for (Operation o : SUPPORTED_OPERATIONS) {
            ret.put(o.getOperator(), o.getClass());
        }
        return ret;
    }
}
