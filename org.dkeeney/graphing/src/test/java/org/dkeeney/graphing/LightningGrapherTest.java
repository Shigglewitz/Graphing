package org.dkeeney.graphing;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.dkeeney.equations.exceptions.InvalidEquationException;
import org.dkeeney.utils.ColorUtils;
import org.dkeeney.utils.ImageMaker;
import org.junit.Ignore;
import org.junit.Test;

public class LightningGrapherTest {
    @Ignore
    @Test
    public void testLightningGraph() throws IOException,
            InvalidEquationException {
        LightningGrapher lg = new LightningGrapher();
        lg.setBackgroundColor(new Color(75, 150, 225));
        lg.setDecaySpeed(5);
        ColorGrapher cg = new ColorGrapher("X^2+Y^2", "sin(Y*X)*128", "X");
        BufferedImage image = cg.getGraph(Grapher.DEFAULT_WIDTH,
                Grapher.DEFAULT_HEIGHT);
        cg.draw(image);
        lg.draw(image);

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
