package org.dkeeney.graphing;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.dkeeney.graphing.equations.exceptions.InvalidEquationException;
import org.dkeeney.utils.ImageComparison;
import org.junit.Test;

public class ColorGrapherTest {
    private static final boolean DIAGNOSTIC = true;

    @Test
    public void testGetGraph() throws InvalidEquationException, IOException {
        this.testColorGraph("X", "Y", "X+Y", "color-graph-1", false, "");
    }

    @Test
    public void testTrigonometricGraph() throws InvalidEquationException,
            IOException {
        this.testColorGraph("X^2+Y^2", "sin(Y*X)*128", "X",
                "trigonometric-graph-1", false, "");
    }

    @Test
    public void testGraph() throws InvalidEquationException, IOException {
        this.testColorGraph("X^2+Y", "0", "0", null, true, "graph-4");
    }

    public void testColorGraph(String redEq, String greenEq, String blueEq,
            String compareFile, boolean save, String fileName)
            throws InvalidEquationException, IOException {
        ColorGrapher cg = new ColorGrapher(redEq, greenEq, blueEq);
        if (DIAGNOSTIC) {
            cg.enableDiagnostics();
        }
        BufferedImage experiment = cg.getGraph();
        if (compareFile != null) {
            ImageComparison.compareRandomPixels(compareFile, experiment);
        }
        if (save) {
            ImageMaker.saveImage(experiment, fileName,
                    ImageMaker.DEFAULT_EXTENSION);
        }
    }
}
