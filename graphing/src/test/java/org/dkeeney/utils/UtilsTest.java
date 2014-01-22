package org.dkeeney.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UtilsTest {
    @Test
    public void testRemoveWhiteSpace() {
        String[] tests = { "           ", String.format(" \t "), "asdf 123",
                "  asdf  ", "The lazy brown fox jumped over the quick dog." };

        for (String s : tests) {
            this.assertHasWhiteSpace(s);
            this.assertHasNoWhiteSpace(Utils.removeAllWhiteSpace(s));
        }

        assertNull(Utils.removeAllWhiteSpace(null));
    }

    @Test
    public void testSplitWithDelimiter() {
        String input = "asdf;asdf";
        String delimiter = ";";
        String[] split = Utils.splitWithDelimiter(input, delimiter);
        assertEquals("Delimiter not found", delimiter, split[1]);
        input = "asdfasdf";
        split = Utils.splitWithDelimiter(input, delimiter);
        assertEquals("Incorrect number of strings found", 1, split.length);
    }

    @Test
    public void testSplitWithDelimiterAndLimit() {
        String input = "asdf;asdf;asdf";
        String delimiter = ";";
        String[] split = Utils.splitWithDelimiter(input, delimiter, 2);
        assertEquals("Incorrect number of strings found", 2, split.length);
        split = Utils.splitWithDelimiter(input, delimiter, 3);
        assertEquals("Incorrect number of strings found", 3, split.length);
        assertEquals("Delimiter not found", delimiter, split[1]);
        split = Utils.splitWithDelimiter(input, delimiter);
        assertEquals("Incorrect number of strings found", 5, split.length);
        assertEquals("Delimiter not found", delimiter, split[1]);
    }

    @Test
    public void testContainsRegex() {
        String regex = "a";
        String[] tests = { "asdf", "sdfasdf", "sdfa", "aaaaaaaa" };
        for (String s : tests) {
            assertTrue(s + " does not contain regex " + regex,
                    Utils.containsRegex(s, regex));
        }
    }

    private static final String WHITE_SPACE_REGEX = ".*\\s.*";

    private void assertHasWhiteSpace(String s) {
        assertTrue("String \"" + s + "\" does not contain whitespace",
                s.matches(WHITE_SPACE_REGEX));
    }

    private void assertHasNoWhiteSpace(String s) {
        assertTrue("String \"" + s + "\" contains whitespace",
                !s.matches(WHITE_SPACE_REGEX));
    }
}