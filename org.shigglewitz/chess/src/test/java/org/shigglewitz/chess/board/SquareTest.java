package org.shigglewitz.chess.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Random;

import org.junit.Test;
import org.shigglewitz.chess.entity.board.Square;

public class SquareTest {
    @Test
    public void testSquareAlphabetSetup() {
        assertEquals(26, Square.ALPHABET.length());

        for (int i = 0; i < Square.ALPHABET.length(); i++) {
            assertEquals((char) ('a' + i), Square.ALPHABET.charAt(i));
        }
    }

    @Test
    public void testGetRankAndFileFromDescr() {
        Random random = new Random();
        int numTestCases = 100;

        for (int i = 0; i < numTestCases; i++) {
            int file = random.nextInt(Square.ALPHABET.length()) + 1;
            int rank = random.nextInt(Square.ALPHABET.length()) + 1;

            this.assertEquality(new int[] { file, rank }, Square
                    .getRankAndFileFromDescr(new Square(file, rank, Math.max(
                            file, rank)).getDescr()));
        }
    }

    @Test
    public void testGetDescr() {
        Random random = new Random();
        int numTestCases = 100;

        for (int i = 0; i < numTestCases; i++) {
            int file = random.nextInt(Square.ALPHABET.length()) + 1;
            int rank = random.nextInt(Square.ALPHABET.length()) + 1;
            String expected = Character.toString(Square.ALPHABET
                    .charAt(file - 1)) + Integer.toString(rank);

            assertEquals("Descr mismatch for file " + file + " and rank "
                    + rank, expected,
                    new Square(file, rank, Math.max(file, rank)).getDescr());
        }
    }

    private void assertEquality(int[] expected, int[] test) {
        assertNotNull(expected);
        assertNotNull(test);
        assertEquals(expected.length, test.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals("Index " + i + " mismatch", expected[i], test[i]);
        }
    }
}
