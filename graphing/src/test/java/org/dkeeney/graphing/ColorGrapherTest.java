package org.dkeeney.graphing;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.dkeeney.graphing.equations.exceptions.InvalidEquationException;
import org.dkeeney.utils.ImageComparison;
import org.junit.Test;

public class ColorGrapherTest {
    @Test
    public void testGetGraph() throws InvalidEquationException, IOException {
        ColorGrapher cg = new ColorGrapher("X", "Y", "X+Y");
        cg.enableDiagnostics();
        BufferedImage experiment = cg.getGraph();
        ImageComparison.compareRandomPixels("color-graph-1", experiment);
    }

    @Test
    public void testTrigonometricGraph() throws InvalidEquationException,
            IOException {
        ColorGrapher cg = new ColorGrapher("X^2+Y^2", "sin(Y*X)*128", "X");
        cg.enableDiagnostics();
        BufferedImage experiment = cg.getGraph();
        ImageComparison
                .compareRandomPixels("trigonometric-graph-1", experiment);
    }
}
