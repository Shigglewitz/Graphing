package org.dkeeney.graphing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.dkeeney.graphing.equations.exceptions.InsufficientVariableInformationException;
import org.dkeeney.graphing.equations.exceptions.InvalidEquationException;
import org.junit.Test;

public class GrapherTest {
    @Test
    public void testCalculateValues() throws InvalidEquationException,
            InsufficientVariableInformationException {
        Grapher g = new Grapher("x^2");
        assertNull(g.getValues());
        g.calculateValues(-2, 2.005, 0.01, "x");
        assertNotNull(g.getValues());
        assertEquals("Calculated the wrong number of values", 401,
                g.getValues().length);
    }
}
