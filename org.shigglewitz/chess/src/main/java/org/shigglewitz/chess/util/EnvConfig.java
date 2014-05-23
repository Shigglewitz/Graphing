package org.shigglewitz.chess.util;

public class EnvConfig {
    public static String getProperty(String key) {
        System.out.println("asked for " + key);

        String value = System.getenv(key);

        if (value == null) {
            value = System.getProperty(key);
        }

        System.out.println("returning " + value);

        return value;
    }

}
