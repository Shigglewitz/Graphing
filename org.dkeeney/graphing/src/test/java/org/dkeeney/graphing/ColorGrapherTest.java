package org.dkeeney.graphing;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.dkeeney.config.Constants;
import org.dkeeney.equations.exceptions.InvalidEquationException;
import org.dkeeney.testutils.ImageComparison;
import org.dkeeney.utils.ImageMaker;
import org.junit.Ignore;
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
    public void testGraphZoom() throws InvalidEquationException, IOException {
        this.testColorGraph("X^2+Y^2", "sin(Y*X)*128", "X", 2, 0, 200,
                DIAGNOSTIC, null, true, "graph-5");
    }

    @Ignore
    @Test
    public void testGraph() throws InvalidEquationException, IOException {
        this.testColorGraph("X^2+Y", "0", "0", null, true, "graph-4");
    }

    public void testColorGraph(String redEq, String greenEq, String blueEq,
            String compareFile, boolean save, String fileName)
            throws InvalidEquationException, IOException {
        this.testColorGraph(redEq, greenEq, blueEq, 1, compareFile, save,
                fileName);
    }

    public void testColorGraph(String redEq, String greenEq, String blueEq,
            int zoomFactor, String compareFile, boolean save, String fileName)
            throws InvalidEquationException, IOException {
        this.testColorGraph(redEq, greenEq, blueEq, zoomFactor, DIAGNOSTIC,
                compareFile, save, fileName);
    }

    public void testColorGraph(String redEq, String greenEq, String blueEq,
            int zoomFactor, boolean diagnostic, String compareFile,
            boolean save, String fileName) throws InvalidEquationException,
            IOException {
        this.testColorGraph(redEq, greenEq, blueEq, zoomFactor, 0, 0,
                diagnostic, compareFile, save, fileName);
    }

    public void testColorGraph(String redEq, String greenEq, String blueEq,
            int zoomFactor, int xShift, int yShift, boolean diagnostic,
            String compareFile, boolean save, String fileName)
            throws InvalidEquationException, IOException {
        ColorGrapher cg = new ColorGrapher(redEq, greenEq, blueEq);
        if (diagnostic) {
            cg.enableDiagnostics();
        }
        cg.setZoomFactor(zoomFactor);
        cg.setPixelShift(xShift, yShift);
        BufferedImage experiment = cg.getGraph();
        if (compareFile != null) {
            ImageComparison.compareRandomPixels(compareFile, experiment);
        }
        if (save) {
            ImageMaker.saveImage(experiment, fileName,
                    Constants.DEFAULT_IMAGE_EXTENSION);
        }
    }
}
