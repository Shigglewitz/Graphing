package org.dkeeney.graphing;

import java.io.IOException;

import org.dkeeney.graphing.equations.exceptions.InvalidEquationException;
import org.junit.Test;

public class ColorGrapherTest {
    @Test
    public void testGetGraph() throws InvalidEquationException, IOException {
        ColorGrapher cg = new ColorGrapher("x", "y", "x+y");
        ImageMaker.saveImage(cg.getGraph(), "color-graph-1", "png");
    }
}
