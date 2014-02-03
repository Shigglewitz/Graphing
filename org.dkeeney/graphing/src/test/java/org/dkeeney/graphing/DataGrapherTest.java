package org.dkeeney.graphing;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.dkeeney.config.Constants;
import org.dkeeney.graphing.DataGrapher.DrawType;
import org.dkeeney.testutils.ImageComparison;
import org.dkeeney.utils.ImageMaker;
import org.junit.Test;

public class DataGrapherTest {
    @Test
    public void testLineGraph() throws IOException {
        Point[] data = { new Point(0, 0), new Point(1, 1), new Point(2, 2),
                new Point(3, 3), new Point(3, 4), new Point(4, 4) };
        DataGrapher dg = new DataGrapher();
        dg.addPoints(data, Color.RED);
        BufferedImage experiment = dg.getGraph();

        try {
            ImageComparison.compareWholeImage("data-graph1",
                    Constants.DEFAULT_IMAGE_EXTENSION, experiment);
        } catch (AssertionError e) {
            ImageMaker.saveImage(experiment, "data-graph");
            throw e;
        }
    }

    @Test
    public void testFillGraph() throws IOException {
        Point[] data = { new Point(0, 0), new Point(1, 1), new Point(2, 2),
                new Point(3, 3), new Point(3, 4), new Point(4, 4) };
        DataGrapher dg = new DataGrapher();
        dg.addPoints(data, Color.RED);
        dg.setDrawType(DrawType.FILL);
        BufferedImage experiment = dg.getGraph();

        try {
            ImageComparison.compareWholeImage("data-graph2",
                    Constants.DEFAULT_IMAGE_EXTENSION, experiment);
        } catch (AssertionError e) {
            ImageMaker.saveImage(experiment, "data-graph");
            throw e;
        }
    }
}
