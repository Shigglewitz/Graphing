package org.dkeeney.graphing;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.dkeeney.config.Constants;
import org.dkeeney.testutils.ImageComparison;
import org.junit.Test;

public class DataGrapherTest {
    @Test
    public void test() throws IOException {
        Point[] data = { new Point(0, 0), new Point(1, 1), new Point(2, 2),
                new Point(3, 3), new Point(3, 4), new Point(4, 4) };
        DataGrapher dg = new DataGrapher(data);
        BufferedImage experiment = dg.getGraph();

        ImageComparison.compareWholeImage("data-graph1",
                Constants.DEFAULT_IMAGE_EXTENSION, experiment);
    }
}
