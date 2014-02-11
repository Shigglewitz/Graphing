package org.dkeeney.graphing;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.dkeeney.equations.exceptions.InvalidEquationException;
import org.dkeeney.utils.ColorUtils;
import org.dkeeney.utils.ImageMaker;
import org.junit.Test;

public class LightningGrapherTest {
    @Test
    public void testLightningGraph() throws IOException,
            InvalidEquationException {
        LightningGrapher lg = new LightningGrapher();
        lg.setDecaySpeed(5);
        lg.setSeed(1234);
        lg.setLightningColor(Color.RED);
        BufferedImage image = lg.getGraph(Grapher.DEFAULT_WIDTH,
                Grapher.DEFAULT_HEIGHT);
        // lg.draw(image);

        ImageMaker.saveImage(image, "lightning-graph");
    }

    @Test
    public void testBrightenPixel() {
        int startColor = ColorUtils.getRgbAsInt(100, 100, 100);
        int[] expected = { 100, 100, 101 };

        int result = LightningGrapher.brightenPixel(startColor, new int[] { 99,
                100, 101 });
        assertEquals(ColorUtils.getRgbAsInt(expected), result);
    }

    @Test
    public void testDarkenPixel() {
        int startColor = ColorUtils.getRgbAsInt(100, 100, 100);
        int[] expected = { 99, 100, 100 };

        int result = LightningGrapher.darkenPixel(startColor, new int[] { 99,
                100, 101 });
        assertEquals(ColorUtils.getRgbAsInt(expected), result);
    }
}
