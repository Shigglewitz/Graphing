package org.dkeeney.utils;

public class Utils {
    public static String removeAllWhiteSpace(String s) {
        return s.replaceAll("\\s+", "");
    }

    private static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

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
}
