package org.dkeeney.graphing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.dkeeney.graphing.equations.Equation;
import org.dkeeney.graphing.equations.exceptions.InsufficientVariableInformationException;
import org.dkeeney.graphing.equations.exceptions.InvalidEquationException;
import org.dkeeney.utils.ColorUtils;
import org.dkeeney.utils.ColorUtils.NormalizationStrategy;

public class ColorGrapher {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;

    private final Equation red, green, blue, alpha;
    private int[][] values;

    private ColorUtils.NormalizationStrategy strategy = NormalizationStrategy.CURVE;

    public ColorGrapher(String red, String green, String blue)
            throws InvalidEquationException {
        this(red, green, blue, null);
    }

    public ColorGrapher(String red, String green, String blue, String alpha)
            throws InvalidEquationException {
        this.red = new Equation(red);
        this.blue = new Equation(blue);
        this.green = new Equation(green);
        if (alpha == null) {
            this.alpha = null;
        } else {
            this.alpha = new Equation(alpha);
        }
    }

    public void calculateValues(int width, int height)
            throws InsufficientVariableInformationException {
        Map<String, BigDecimal> vars = new HashMap<>();
        this.values = new int[width][height];

        int redValue;
        int greenValue;
        int blueValue;
        int alphaValue = 0xfff;
        for (int x = 0; x < width; x++) {
            vars.put("x", new BigDecimal(x));
            for (int y = 0; y < height; y++) {
                vars.put("y", new BigDecimal(y));
                redValue = ColorUtils.normalizeColor(
                        (int) this.red.solve(vars), this.strategy);
                greenValue = ColorUtils.normalizeColor(
                        (int) this.green.solve(vars), this.strategy);
                blueValue = ColorUtils.normalizeColor(
                        (int) this.blue.solve(vars), this.strategy);
                if (this.alpha != null) {
                    alphaValue = ColorUtils.normalizeAlpha(
                            (int) this.alpha.solve(vars), this.strategy);
                }

                this.values[x][y] = ColorUtils.getRgbAsInt(redValue,
                        greenValue, blueValue, alphaValue);
            }
        }
    }

    public BufferedImage getGraph() {
        return this.getGraph(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public BufferedImage getGraph(int width, int height) {
        BufferedImage image = ImageMaker.baseImage(width, height);
        Graphics2D graphics = image.createGraphics();

        if (this.values == null || this.values.length != image.getWidth()
                || this.values[0].length != image.getHeight()) {
            try {
                this.calculateValues(image.getWidth(), image.getHeight());
            } catch (InsufficientVariableInformationException e) {
                graphics.setColor(Color.BLACK);
                graphics.drawString(
                        "An error occurred while calculating values", 1, 10);
            }
        }

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                image.setRGB(x, y, this.values[x][y]);
            }
        }

        graphics.dispose();

        return image;
    }

    public void setStrategy(ColorUtils.NormalizationStrategy strategy) {
        this.strategy = strategy;
    }
}
