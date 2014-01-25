package org.dkeeney.graphing.equations.operations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.dkeeney.graphing.equations.Valuable;
import org.dkeeney.utils.Utils;

public abstract class Operation implements Valuable {
    private static final Operation[] SUPPORTED_OPERATIONS = { new Constant(),
            new Addition(), new Subtraction(), new Multiplication(),
            new Division(), new Exponent() };
    private static final Operation[][] ORDER_OF_OPERATIONS = {
            { new Exponent() }, { new Multiplication(), new Division() },
            { new Addition(), new Subtraction() } };
    private static Map<String, Class<? extends Operation>> OPERATION_MAP;
    public static final String OPERATOR_REGEX = "[\\^*/+-]";

    protected Valuable left;
    protected Valuable right;

    protected Operation() {
    };

    protected Operation(Valuable left, Valuable right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public abstract double evaluate();

    public abstract String getOperator();

    public static Operation[][] getOrderOfOperations() {
        Operation[][] ret = new Operation[ORDER_OF_OPERATIONS.length][];

        for (int i = 0; i < ORDER_OF_OPERATIONS.length; i++) {
            ret[i] = new Operation[ORDER_OF_OPERATIONS[i].length];
            for (int j = 0; j < ORDER_OF_OPERATIONS[i].length; j++) {
                ret[i][j] = ORDER_OF_OPERATIONS[i][j];
            }
        }
        return ret;
    }

    public static boolean containsOperator(String input) {
        return Utils.containsRegex(input, OPERATOR_REGEX);
    }

    public static boolean isOperator(String input) {
        return input.matches(OPERATOR_REGEX);
    }

    public static Operation getOperation(String operator, Valuable left,
            Valuable right) {
        return createOperation(determineOperation(operator), left, right);
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
            Valuable left, Valuable right) {
        Operation o = null;
        try {
            Constructor<? extends Operation> c = clazz.getDeclaredConstructor(
                    Valuable.class, Valuable.class);
            o = c.newInstance(left, right);
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

    public Valuable getLeft() {
        return this.left;
    }

    public void setLeft(Valuable left) {
        this.left = left;
    }

    public Valuable getRight() {
        return this.right;
    }

    public void setRight(Valuable right) {
        this.right = right;
    }
}
