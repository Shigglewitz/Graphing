package org.dkeeney.graphing.equations.operations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.dkeeney.graphing.equations.Term;
import org.dkeeney.graphing.equations.Valuable;
import org.dkeeney.utils.Utils;

public abstract class Operation implements Valuable {
    private static Map<String, Class<? extends Operation>> OPERATION_MAP;
    public static final String OPERATOR_REGEX = "[+-/*^]";

    protected Term left;
    protected Term right;

    protected Operation(Term left, Term right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public abstract double evaluate();

    public abstract String getOperator();

    public static boolean containsOperator(String input) {
        return Utils.containsRegex(input, OPERATOR_REGEX);
    }

    public static Operation getOperation(String operator, Term left, Term right) {
        return createOperation(determineOperation(operator), left, right);
    }

    private static Operation createOperation(Class<? extends Operation> clazz,
            Term left, Term right) {
        Operation o = null;
        try {
            Constructor<? extends Operation> c = clazz.getDeclaredConstructor(
                    Term.class, Term.class);
            o = c.newInstance(left, right);
        } catch (InstantiationException | IllegalAccessException
                | NoSuchMethodException | SecurityException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return o;
    }

    private static Class<? extends Operation> determineOperation(String operator) {
        synchronized (Operation.class) {
            if (OPERATION_MAP == null) {
                OPERATION_MAP = initMap();
            }
        }

        return OPERATION_MAP.get(operator);
    }

    private static Map<String, Class<? extends Operation>> initMap() {
        Map<String, Class<? extends Operation>> ret = new HashMap<String, Class<? extends Operation>>();
        ret.put("+", Addition.class);
        ret.put("-", Subtraction.class);
        ret.put("*", Multiplication.class);
        ret.put("/", Division.class);
        ret.put("^", Exponent.class);
        ret.put(null, Constant.class);
        return ret;
    }

    public Term getLeft() {
        return this.left;
    }

    public void setLeft(Term left) {
        this.left = left;
    }

    public Term getRight() {
        return this.right;
    }

    public void setRight(Term right) {
        this.right = right;
    }
}
