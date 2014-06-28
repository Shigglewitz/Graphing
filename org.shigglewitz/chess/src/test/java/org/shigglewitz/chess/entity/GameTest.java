package org.shigglewitz.chess.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.shigglewitz.chess.entity.Game.Color;
import org.springframework.util.StringUtils;

public class GameTest {
    @Test
    public void testEmptyStartingString() {
        for (int i = 1; i < 20; i++) {
            String test = Game.emptyStartingString(i);

            assertEquals(i, test.length());
            assertFalse(StringUtils.hasText(test));
        }
    }

    @Test
    public void testDefaultSetup() {
        Game game = Game.createDefaultGame();
        assertEquals(Color.LIGHT, game.getNextMove());
    }
}
