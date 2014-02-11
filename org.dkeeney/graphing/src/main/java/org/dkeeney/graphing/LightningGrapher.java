package org.dkeeney.graphing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.dkeeney.utils.ColorUtils;
import org.dkeeney.utils.ImageMaker;

public class LightningGrapher implements Grapher {

    private Color backgroundColor = Color.BLACK;
    private Color lightningColor = Color.WHITE;
    private int decaySpeed = 1;
    private long seed = 0;

    private final List<Line2D> lines;

    public LightningGrapher() {
        this.lines = new ArrayList<>();
    }

    private void generateLines(int width, int height) {
        this.generateLines(null, width, height);
    }

    private void generateLines(Long seed, int width, int height) {
        Random random = null;
        if (seed != null) {
            random = new Random(seed.longValue());
        } else {
            random = new Random();
        }

        boolean horizontal = random.nextBoolean();

        if (horizontal) {
            this.lines.add(new Line2D.Double(0, random.nextInt(height),
                    width - 1, random.nextInt(height)));
        } else {
            this.lines.add(new Line2D.Double(random.nextInt(width), 0, random
                    .nextInt(width), height - 1));
        }
    }

    @Override
    public void draw(BufferedImage image) {
        Graphics2D graphics = image.createGraphics();

        if (this.seed != 0) {
            this.generateLines(this.seed, image.getWidth(), image.getHeight());
        } else {
            this.generateLines(image.getWidth(), image.getHeight());
        }

        graphics.setColor(this.lightningColor);
        for (Line2D line : this.lines) {
            // draw the line
            System.out.println((int) line.getX1() + " " + (int) line.getY1()
                    + " " + (int) line.getX2() + " " + (int) line.getY2());
            graphics.drawLine((int) line.getX1(), (int) line.getY1(),
                    (int) line.getX2(), (int) line.getY2());

            // add shading
            this.drawShading(image, line);
        }

        graphics.dispose();
    }

    private void drawShading(BufferedImage image, Line2D line) {
        int[] brightestRgb = ColorUtils.getRgbFromInt(this.lightningColor
                .getRGB());
        double slopeCalc = line.getX2() - line.getX1();
        boolean horizontal = false;
        int startPoint = 0;
        int pixelsAdjusted = 0;
        if (slopeCalc == 0) {
            horizontal = false;
        } else {
            slopeCalc = (line.getY2() - line.getY1()) / slopeCalc;
            if (slopeCalc >= -1 && slopeCalc <= 1) {
                horizontal = true;
            } else {
                horizontal = false;
            }
        }
        for (int i = 0; i < 255 / this.decaySpeed; i++) {
            for (int j = 0; j < brightestRgb.length; j++) {
                brightestRgb[j] = brightestRgb[j] - this.decaySpeed;
            }
            for (int x = 0; (horizontal && x < image.getWidth())
                    || (!horizontal && x < image.getHeight()); x++) {
                if (horizontal) {
                    startPoint = (int) (line.getY1() + x * slopeCalc);
                    if (startPoint + i < image.getHeight()) {
                        image.setRGB(
                                x,
                                startPoint + i,
                                brightenPixel(image.getRGB(x, startPoint + i),
                                        brightestRgb));
                    }
                    if (startPoint - i >= 0) {
                        image.setRGB(
                                x,
                                startPoint - i,
                                brightenPixel(image.getRGB(x, startPoint - i),
                                        brightestRgb));
                    }
                } else {
                    startPoint = (int) (line.getX1() + x * (1 / slopeCalc));
                    if (startPoint + i < image.getWidth()) {
                        image.setRGB(
                                startPoint + i,
                                x,
                                brightenPixel(image.getRGB(startPoint + i, x),
                                        brightestRgb));
                    }
                    if (startPoint - i > 0) {
                        image.setRGB(
                                startPoint - i,
                                x,
                                brightenPixel(image.getRGB(startPoint - i, x),
                                        brightestRgb));
                    }
                }
                pixelsAdjusted += 2;
            }
        }
        System.out.println("Adjusted " + pixelsAdjusted + " pixels!");
    }

    private static int adjustPixel(int initialValue, int[] maximumAdjustment,
            boolean brighten) {
        int[] rgb = ColorUtils.getRgbFromInt(initialValue);

        for (int i = 0; i < rgb.length; i++) {
            if (brighten) {
                rgb[i] = Math.max(rgb[i], maximumAdjustment[i]);
            } else {
                rgb[i] = Math.min(rgb[i], maximumAdjustment[i]);
            }
        }

        return ColorUtils.getRgbAsInt(rgb);
    }

    public static int darkenPixel(int initialValue, int[] darkestPossible) {
        return adjustPixel(initialValue, darkestPossible, false);
    }

    public static int brightenPixel(int initialValue, int[] brightestPossible) {
        return adjustPixel(initialValue, brightestPossible, true);
    }

    @Override
    public void adjustWindow(double minX, double maxX, double minY, double maxY) {
        return;
    }

    @Override
    public BufferedImage getGraph(int width, int height) {
        BufferedImage image = ImageMaker.baseImage(width, height,
                this.backgroundColor);
        this.draw(image);
        return image;
    }

    public int getDecaySpeed() {
        return this.decaySpeed;
    }

    public void setDecaySpeed(int decaySpeed) {
        this.decaySpeed = decaySpeed;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getLightningColor() {
        return this.lightningColor;
    }

    public void setLightningColor(Color lightningColor) {
        this.lightningColor = lightningColor;
    }

    public long getSeed() {
        return this.seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

}
