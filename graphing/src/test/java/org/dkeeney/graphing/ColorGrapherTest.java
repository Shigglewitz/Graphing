package org.dkeeney.graphing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.dkeeney.graphing.equations.exceptions.InvalidEquationException;
import org.dkeeney.utils.ImageComparison;
import org.junit.Test;

public class ColorGrapherTest {
    @Test
    public void testGetGraph() throws InvalidEquationException, IOException {
        ColorGrapher cg = new ColorGrapher("X", "Y", "X+Y");
        cg.enableDiagnostics();
        BufferedImage experiment = cg.getGraph();
        File file = new File("src/test/resources/images/" + "color-graph-1"
                + ".png");
        BufferedImage control = ImageIO.read(file);
        ImageComparison.compareRandomPixels(control, experiment);
    }
}
