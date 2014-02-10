package org.dkeeney.graphing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.dkeeney.utils.ColorUtils;
import org.dkeeney.utils.ImageMaker;

public class LightningGrapher implements Grapher {

    private Color backgroundColor = Color.BLACK;
    private int decaySpeed = 1;

    @Override
    public void draw(BufferedImage image) {
        Graphics2D graphics = image.createGraphics();
        int startPoint = image.getHeight() / 2;

        graphics.setColor(Color.WHITE);
        graphics.drawLine(0, startPoint, image.getWidth(), startPoint);
        int[] brightestRgb = ColorUtils.getRgbFromInt(Color.WHITE.getRGB());
        for (int i = 0; i < 255 / this.decaySpeed; i++) {
            if (startPoint + i >= image.getHeight()) {
                break;
            }
            for (int j = 0; j < brightestRgb.length; j++) {
                brightestRgb[j] = brightestRgb[j] - this.decaySpeed;
            }
            for (int x = 0; x < image.getWidth(); x++) {
                image.setRGB(
                        x,
                        startPoint + i,
                        brightenPixel(image.getRGB(x, startPoint + i),
                                brightestRgb));
                image.setRGB(
                        x,
                        startPoint - i,
                        brightenPixel(image.getRGB(x, startPoint - i),
                                brightestRgb));
            }
        }

        graphics.dispose();
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

}
