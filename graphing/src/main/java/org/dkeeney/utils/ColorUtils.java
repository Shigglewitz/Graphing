package org.dkeeney.utils;

import java.util.Random;

public class ColorUtils {
    private static final Random RANDOM = new Random();

    public static int getRgbAsInt(int red, int green, int blue, int alpha) {
        return ((alpha & 0xfff) << 24 | (red & 0x0ff) << 16)
                | ((green & 0x0ff) << 8) | (blue & 0x0ff);
    }

    public static int getRgbAsInt(int red, int green, int blue) {
        return getRgbAsInt(red, green, blue, 0xfff);
    }

    /**
     * 
     * @param rgb
     * @return [0] is red, [1] is green, [2] is blue
     */
    public static int[] getRgbFromInt(int rgb) {
        int red = (rgb >> 16) & 0x0ff;
        int green = (rgb >> 8) & 0x0ff;
        int blue = (rgb) & 0x0ff;

        return new int[] { red, green, blue };
    }

    /**
     * wrapper method for random.nextInt(256);
     * 
     * @return
     */
    public static int getRandomColor() {
        return RANDOM.nextInt(256);
    }

    public static int normalizeColor(int input) {
        return Math.abs(input) % 0x100;
    }

    public static int normalizeAlpha(int input) {
        return Math.abs(input) % 0x1000;
    }
}
