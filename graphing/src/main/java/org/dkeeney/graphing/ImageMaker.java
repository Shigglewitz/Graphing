package org.dkeeney.graphing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.dkeeney.utils.Utils;

public class ImageMaker {
    public static final String DEFAULT_DIRECTORY = "src/test/resources/rendered/";

    public static void saveImage(BufferedImage image) throws IOException {
        saveImage(image, "test-image", "png");
    }

    public static void saveImage(BufferedImage image, String filename,
            String format) throws IOException {
        File outputfile = new File(DEFAULT_DIRECTORY + filename + "." + format);
        ImageIO.write(image, format, outputfile);
        image.flush();
    }

    public static BufferedImage baseImage(int width, int height, Color baseColor) {
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D graphics = image.createGraphics();

        graphics.setPaint(baseColor);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();

        return image;
    }

    public static BufferedImage baseImage(int width, int height) {
        return baseImage(width, height, Color.WHITE);
    }

    public static BufferedImage randomImage(int width, int height)
            throws IOException {
        BufferedImage image = baseImage(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(
                        x,
                        y,
                        Utils.getRgbAsInt(Utils.getRandomColor(),
                                Utils.getRandomColor(), Utils.getRandomColor()));
            }
        }

        return image;
    }
}
