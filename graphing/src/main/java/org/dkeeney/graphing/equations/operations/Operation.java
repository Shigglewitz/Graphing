package org.dkeeney.graphing.equations.operations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.dkeeney.graphing.equations.Evaluable;
import org.dkeeney.utils.Utils;

public abstract class Operation {
    private static final Operation[] SUPPORTED_OPERATIONS = { new Constant(),
            new Addition(), new Subtraction(), new Multiplication(),
            new Division(), new Exponent() };
    private static final Operation[][] ORDER_OF_OPERATIONS = {
            { new Exponent() }, { new Multiplication(), new Division() },
            { new Addition(), new Subtraction() } };
    private static Map<String, Class<? extends Operation>> OPERATION_MAP;
    public static final String OPERATOR_REGEX = "[\\^*/+-]";

    protected Evaluable right;

    protected Operation() {
    };

    protected Operation(Evaluable right) {
        this.right = right;
    }

    public abstract double operate(double initialValue,
            Map<String, BigDecimal> variableValues);

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

    public Evaluable getRight() {
        return this.right;
    }

    public void setRight(Evaluable right) {
        this.right = right;
    }
}
