package org.dkeeney.graphing.equations;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EquationTest {
    private static final double DELTA = 0.0000001;

    @Test
    public void testConstants() {
        String[] input = { "1", "20", "5", "4" };
        for (String element : input) {
            this.testEquation(element, Double.valueOf(element));
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

    private void testEquation(String equation, double expected) {
        assertEquals("Equation " + equation + " did not evaluate to "
                + expected, expected, new Equation(equation).evaluate(), DELTA);
    }
}
