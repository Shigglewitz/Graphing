package org.dkeeney.graphing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;
import org.dkeeney.utils.Utils;
import org.junit.AfterClass;
import org.junit.Test;

public class ImageMakerTest {
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = DEFAULT_WIDTH;

    @AfterClass
    public static void cleanUp() {
        File dir = new File(ImageMaker.DEFAULT_DIRECTORY);
        for (File file : dir.listFiles()) {
            file.delete();
        }
    }

    @Test
    public void testBaseImage() {
        BufferedImage image = ImageMaker.baseImage(DEFAULT_WIDTH,
                DEFAULT_HEIGHT);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                assertEquals(Color.WHITE.getRGB(), image.getRGB(x, y));
            }
        }
    }

    @Test
    public void testBaseImageWithColor() {
        int red = Utils.getRandomColor();
        int green = Utils.getRandomColor();
        int blue = Utils.getRandomColor();

        BufferedImage image = ImageMaker.baseImage(DEFAULT_WIDTH,
                DEFAULT_HEIGHT, new Color(red, green, blue));
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                assertEquals(Utils.getRgbAsInt(red, green, blue),
                        image.getRGB(x, y));
            }
        }
    }

    @Test
    public void testSaveImage() throws IOException {
        Random random = new Random();
        Color randomColor = new Color(Utils.getRandomColor(),
                Utils.getRandomColor(), Utils.getRandomColor());
        BufferedImage image = ImageMaker.baseImage(
                random.nextInt(DEFAULT_WIDTH) + 50,
                random.nextInt(DEFAULT_HEIGHT) + 50, randomColor);
        String fileName = RandomStringUtils.randomAlphabetic(15);
        ImageMaker.saveImage(image, fileName, "png");

        File file = new File(ImageMaker.DEFAULT_DIRECTORY + fileName + ".png");
        assertTrue("File was not created", file.exists());
        BufferedImage testImage = ImageIO.read(file);
        assertNotNull(
                "Could not load image to test from file " + file.getPath(),
                testImage);
        assertEquals("Widths do not match!", image.getWidth(),
                testImage.getWidth());
        assertEquals("Heights do not match!", image.getHeight(),
                testImage.getHeight());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                assertEquals("Pixel (" + x + ", " + y + ") did not match!",
                        image.getRGB(x, y), testImage.getRGB(x, y));
            }
        }

    }
}
