package org.dkeeney.utils;

/**
 * to count the number of lines in a project, use file search and use this
 * regex: \n[\s]*
 * 
 * source:
 * http://stackoverflow.com/questions/1043666/counting-line-numbers-in-eclipse
 * 
 * @author Daniel
 * 
 */
import java.util.Random;

public class Utils {
    private static final Random RANDOM = new Random();
    private static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

    public static String removeAllWhiteSpace(String s) {
        if (s == null) {
            return null;
        }
        return s.replaceAll("\\s+", "");
    }

    public static String[] splitWithDelimiter(String s, String delimiter,
            int limit) {
        String[] ret = s.split(String.format(WITH_DELIMITER, delimiter), limit);
        return ret;
    }

    public static String[] splitWithDelimiter(String s, String delimiter) {
        return splitWithDelimiter(s, delimiter, 0);
    }

    public static boolean containsRegex(String input, String regex) {
        return input.matches(".*" + regex + ".*");
    }

    /**
     * 
     * @param input
     * @param begin
     * @param end
     * @return [0] is the beginning, [1] is the end
     */
    public static int[] getMatchedGroup(String input, char begin, char end) {
        if (input == null) {
            return null;
        }
        int firstGroupMark = input.indexOf(begin);
        int firstEndMark = input.indexOf(end);
        if (firstGroupMark == -1 || firstEndMark == -1
                || firstGroupMark > firstEndMark) {
            return null;
        }
        int[] ret = new int[2];
        ret[0] = -1;
        ret[1] = -1;
        int matches = 0;
        char[] chars = input.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == begin) {
                if (ret[0] < 0) {
                    ret[0] = i;
                }
                matches++;
            } else if (chars[i] == end) {
                matches--;
                if (matches == 0) {
                    ret[1] = i;
                    break;
                }
            }
        }

        return ret;
    }

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
}
