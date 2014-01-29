package org.dkeeney.graphing;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.dkeeney.graphing.equations.exceptions.InvalidEquationException;
import org.dkeeney.utils.ImageComparison;
import org.junit.Ignore;
import org.junit.Test;

public class ColorGrapherTest {
    @Ignore
    @Test
    public void testGetGraph() throws InvalidEquationException, IOException {
        ColorGrapher cg = new ColorGrapher("X", "Y", "X+Y");
        cg.enableDiagnostics();
        BufferedImage experiment = cg.getGraph();
        ImageComparison.compareRandomPixels("color-graph-1", experiment);
    }

    @Ignore
    @Test
    public void testTrigonometricGraph() throws InvalidEquationException,
            IOException {
        ColorGrapher cg = new ColorGrapher("sin(X)", "cos(Y)", "tan(X+Y)");
        cg.enableDiagnostics();
        BufferedImage experiment = cg.getGraph();
        ImageMaker.saveImage(experiment, "color-graph-2",
                ImageMaker.DEFAULT_EXTENSION);
    }

    @Ignore
    @Test
    public void testTrigonometricGraph2() throws InvalidEquationException,
            IOException {
        ColorGrapher cg = new ColorGrapher("sin(X)*255", "cos(Y)*255",
                "tan(X+Y)*255");
        cg.enableDiagnostics();
        BufferedImage experiment = cg.getGraph();
        ImageMaker.saveImage(experiment, "color-graph-3",
                ImageMaker.DEFAULT_EXTENSION);
    }

    @Test
    public void testTrigonometricGraph3() throws InvalidEquationException,
            IOException {
        ColorGrapher cg = new ColorGrapher("X^2+Y^2", "sin(Y*X)*128", "X");
        cg.enableDiagnostics();
        BufferedImage experiment = cg.getGraph();
        ImageMaker.saveImage(experiment, "color-graph-4",
                ImageMaker.DEFAULT_EXTENSION);
    }
}
