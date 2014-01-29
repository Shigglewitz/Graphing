package org.dkeeney.graphing.equations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.dkeeney.graphing.equations.exceptions.InsufficientVariableInformationException;
import org.dkeeney.graphing.equations.exceptions.InvalidEquationException;
import org.dkeeney.utils.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

public class EquationTest {
    private static final double DELTA = 0.0000001;
    private static final Map<String, BigDecimal> STANDARD_VARS = new HashMap<>();

    @BeforeClass
    public static void before() {
        char var;
        for (int i = 0; i < 26; i++) {
            var = (char) ('a' + i);
            STANDARD_VARS.put(Character.toString(var), new BigDecimal(i + 1));
        }
    }

    @Test
    public void testInvalidEquations() {
        String[] input = { "", " ", null, "+", "+1", "1*2*8*" };

        for (String s : input) {
            this.testEquationValidity(s, false);
        }
    }

    @Test
    public void testValidEquations() {
        String[] input = { "1+2", "3 * 4 / 5" };

        for (String s : input) {
            this.testEquationValidity(s, true);
        }
    }

    @Test
    public void testInvalidEquationsWithParens() {
        String[] input = { "1+(2", "(1*3)) * (4 / 5)" };

        for (String s : input) {
            this.testEquationValidity(s, false);
        }
    }

    @Test
    public void testValidEquationsWithParens() {
        String[] input = { "(1+2)", "(1+(3*4)) " };

        for (String s : input) {
            this.testEquationValidity(s, true);
        }
    }

    @Test
    public void testValidDecimals() {
        String[] input = { "1.0004 * 0.01", "0.1(5.6)/4.3" };

        for (String s : input) {
            this.testEquationValidity(s, true);
        }
    }

    @Test
    public void testInvalidDecimals() {
        String[] input = { ".3*4.5", "0.567 + 9.", "0.45 * 78..12", "1+2+3.4.5" };

        for (String s : input) {
            this.testEquationValidity(s, false);
        }
    }

    @Test
    public void testValidVariables() {
        String[] input = { "1+2+x", "((4*5)^x-1)", "x-y-z+t^f", "1(2.05x)(y)",
                "xyz", "20xy", "2(x)+1", "x(2*y)z" };

        for (String s : input) {
            this.testEquationValidity(s, true);
        }
    }

    @Test
    public void testInvalidVariables() {
        String[] input = { "2Xy" };

        for (String s : input) {
            this.testEquationValidity(s, false);
        }
    }

    @Test
    public void testImplicitMultiplication() {
        String[] tests = { "1+2(3*4)7+8", "(1)30+45+67(89)(56)(12)" };
        String[] expected = { "1+2*(3*4)*7+8", "(1)*30+45+67*(89)*(56)*(12)" };

        for (int i = 0; i < tests.length; i++) {
            assertEquals(expected[i],
                    Equation.addImpliedMultiplication(tests[i]));
        }
    }

    @Test
    public void testOriginalEquation() throws InvalidEquationException {
        String[] equation = { "1(2) b  (c)", "4^5(67   )" };
        for (String e : equation) {
            assertEquals(e, new Equation(e).getOriginalEquation());
        }
    }

    @Test
    public void testInsufficientVariables() {
        String[] tests = { "x+4+y+z*45", "(a)*b^((c)+d)" };
        Map<String, BigDecimal> vars = new HashMap<>();
        String baseMessage = "Missing variable value for ";
        String[] expectedMessages = { baseMessage + "x", baseMessage + "a" };

        for (int i = 0; i < tests.length; i++) {
            try {
                new Equation(tests[i]).evaluate(vars);
                assertTrue("Exception not thrown for " + tests[i], false);
            } catch (InsufficientVariableInformationException e) {
                assertEquals(expectedMessages[i], e.getMessage());
            }
        }
    }

    @Test
    public void testConstants() throws InvalidEquationException,
            InsufficientVariableInformationException {
        String[] input = { "1", "20", "5", "4", "(40)", "-5" };
        double[] expected = { 1, 20, 5, 4, 40, -5 };
        for (int i = 0; i < input.length; i++) {
            this.testEquation(input[i], expected[i]);
        }
    }

    @Test
    public void testSimpleEquations() throws InvalidEquationException,
            InsufficientVariableInformationException {
        String[] input = { "1+1", "1*3", "3/5", "0-4", "5^0", "5^1", "3^3",
                "-1-2" };
        double[] output = { 2, 3, 0.6, -4, 1, 5, 27, -3 };

        for (int i = 0; i < input.length; i++) {
            this.testEquation(input[i], output[i]);
        }
    }

    @Test
    public void testLongEquations() throws InvalidEquationException,
            InsufficientVariableInformationException {
        // none of these tests should test the order of operations
        String[] input = { "1 + 1  + 1 +  1", "1 * 3 * 2 * 4 ", "3 * 2 + 1",
                "2^3*2+1" };
        double[] output = { 4, 24, 7, 17 };

        for (int i = 0; i < input.length; i++) {
            this.testEquation(input[i], output[i]);
        }
    }

    @Test
    public void testOrderOfOperations() throws InvalidEquationException,
            InsufficientVariableInformationException {
        String[] input = { "1+2*3", "1+3^3+1*4", "0/4+1*3",
                "1*30+45+67*89*56*12", "1*3+-4" };
        double[] output = { 7, 32, 3, 4007211, -1 };

        for (int i = 0; i < input.length; i++) {
            this.testEquation(input[i], output[i]);
        }
    }

    @Test
    public void testSplit() {
        Equation.split("-1.0/X+2*(4/5)^34");
    }

    @Test
    public void testEquationsWithParens() throws InvalidEquationException,
            InsufficientVariableInformationException {
        String[] input = { "(20)*(19)", "(1+2)*3", "1+3^(3+1)*4", "0/(4+1)3",
                "1*30+(45+67)89(56)*12", "1+((2*(5+2)))" };
        double[] output = { 380, 9, 325, 0, 6698526, 15, };

        for (int i = 0; i < input.length; i++) {
            this.testEquation(input[i], output[i]);
        }
    }

    @Test
    public void testEquationsWithVariables() throws InvalidEquationException,
            InsufficientVariableInformationException {
        String[] tests = { "x+y", "b^c", "b*b", "b(c)/(d+e)", "x^2" };
        double[] expected = { 49, 8, 4, 2.0 / 3.0, 576 };

        for (int i = 0; i < tests.length; i++) {
            this.testEquation(tests[i], expected[i], STANDARD_VARS);
        }
    }

    private void testEquationValidity(String equation, boolean valid) {
        equation = Utils.removeAllWhiteSpace(equation);
        equation = Equation.addImpliedMultiplication(equation);
        // assertEquals("Failed validity test for equation " + equation + " "
        // + valid + ".", valid, Equation.isValidEquation(equation));
    }

    private void testEquation(String equation, double expected)
            throws InvalidEquationException,
            InsufficientVariableInformationException {
        this.testEquation(equation, expected, null);
    }

    private void testEquation(String equation, double expected,
            Map<String, BigDecimal> vars) throws InvalidEquationException,
            InsufficientVariableInformationException {
        assertEquals("Equation " + equation + " did not evaluate to "
                + expected, expected, new Equation(equation).evaluate(vars),
                DELTA);
    }
}
