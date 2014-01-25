package org.dkeeney.graphing.equations;

import static org.junit.Assert.assertEquals;

import org.dkeeney.utils.Utils;
import org.junit.Test;

public class EquationTest {
    private static final double DELTA = 0.0000001;

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
        String[] input = { ".3*4.5", "0.567 + 9.", "0.45 * 78..12" };

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
    public void testConstants() {
        String[] input = { "1", "20", "5", "4", "(40)" };
        double[] expected = { 1, 20, 5, 4, 40 };
        for (int i = 0; i < input.length; i++) {
            this.testEquation(input[i], expected[i]);
        }
    }

    @Test
    public void testSimpleEquations() {
        String[] input = { "1+1", "1*3", "3/5", "0-4", "5^0", "5^1", "3^3" };
        double[] output = { 2, 3, 0.6, -4, 1, 5, 27 };

        for (int i = 0; i < input.length; i++) {
            this.testEquation(input[i], output[i]);
        }
    }

    @Test
    public void testLongEquations() {
        // none of these tests should test the order of operations
        String[] input = { "1 + 1  + 1 +  1", "1 * 3 * 2 * 4 ", "3 * 2 + 1",
                "2^3*2+1" };
        double[] output = { 4, 24, 7, 17 };

        for (int i = 0; i < input.length; i++) {
            this.testEquation(input[i], output[i]);
        }
    }

    @Test
    public void testOrderOfOperations() {
        String[] input = { "1+2*3", "1+3^3+1*4", "0/4+1*3",
                "1*30+45+67*89*56*12" };
        double[] output = { 7, 32, 3, 4007211 };

        for (int i = 0; i < input.length; i++) {
            this.testEquation(input[i], output[i]);
        }
    }

    @Test
    public void testEquationsWithParens() {
        String[] input = { "(20)*(19)", "(1+2)*3", "1+3^(3+1)*4", "0/(4+1)3",
                "1*30+(45+67)89(56)*12" };
        double[] output = { 380, 9, 325, 0, 6698526 };

        for (int i = 0; i < input.length; i++) {
            this.testEquation(input[i], output[i]);
        }
    }

    private void testEquationValidity(String equation, boolean valid) {
        equation = Utils.removeAllWhiteSpace(equation);
        equation = Equation.addImpliedMultiplication(equation);
        assertEquals("Failed validity test for equation " + equation + " "
                + valid + ".", Equation.isValidEquation(equation), valid);
    }

    private void testEquation(String equation, double expected) {
        assertEquals("Equation " + equation + " did not evaluate to "
                + expected, expected, new Equation(equation).solve(), DELTA);
    }
}
