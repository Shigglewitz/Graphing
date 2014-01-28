package org.dkeeney.graphing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;

import org.dkeeney.graphing.equations.exceptions.InsufficientVariableInformationException;
import org.dkeeney.graphing.equations.exceptions.InvalidEquationException;
import org.junit.AfterClass;
import org.junit.Test;

public class GrapherTest {
    private static final double DELTA = 0.0000001;

    @AfterClass
    public static void cleanUp() {
        // Utils.cleanDirectory(ImageMaker.DEFAULT_DIRECTORY);
    }

    @Test
    public void testCalculateValues() throws InvalidEquationException,
            InsufficientVariableInformationException {
        Grapher g = new Grapher("x^2");
        assertNull(g.getValues());
        g.calculateValues(new BigDecimal(-2), new BigDecimal(2.005),
                new BigDecimal(0.01), "x");
        assertNotNull(g.getValues());
        assertEquals("Calculated the wrong number of values", 401,
                g.getValues().length);
        assertEquals(0, g.getValues()[200], DELTA);
        assertEquals(4, g.getValues()[0], DELTA);
        assertEquals(4, g.getValues()[400], DELTA);
        assertEquals(3.0625, g.getValues()[375], DELTA);
    }

    @Test
    public void testGetGraph() throws InvalidEquationException, IOException {
        Grapher g = new Grapher("x^2");
        ImageMaker.saveImage(g.getGraph(), "graph1", "png");
        g = new Grapher("0-x^2");
        g.setGraphBackground(Color.CYAN);
        g.setAxisColor(Color.DARK_GRAY);
        g.setGridColor(Color.WHITE);
        g.setGraphColor(Color.RED);
        g.setExtraGraphPadding(1);
        g.setDrawGrid(true);
        ImageMaker.saveImage(g.getGraph(-1, 10, -5, 2), "graph2", "png");
    }
}
