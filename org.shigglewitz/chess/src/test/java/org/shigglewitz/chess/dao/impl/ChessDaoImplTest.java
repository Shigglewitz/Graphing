package org.shigglewitz.chess.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.shigglewitz.chess.entity.dao.ChessDao;
import org.shigglewitz.chess.entity.game.Game;
import org.shigglewitz.chess.entity.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration({ "classpath:applicationContext-hibernate.xml",
        "classpath:applicationContext-daoBeans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ChessDaoImplTest {
    @Autowired
    private ChessDao chessDao;

    @Test
    public void testSavePlayer() {
        Player player = new Player();

        this.chessDao.savePlayer(player);

        assertNotNull(player.getId());
    }

    @Test
    public void testGetPlayer() {
        Player player = new Player();
        this.chessDao.savePlayer(player);
        UUID id = player.getId();
        player = null;

        player = this.chessDao.getPlayer(id);

        assertNotNull(player);
    }

    @Test
    public void testGetUnknownPlayer() {
        Player player = this.chessDao.getPlayer(UUID.randomUUID());

        assertNull(player);
    }

    @Test
    public void testSaveGame() {
        Game game = new Game();

        this.chessDao.saveGame(game);

        assertNotNull(game.getId());
    }

    @Test
    public void testGetGame() {
        Game game = new Game();
        this.chessDao.saveGame(game);
        UUID id = game.getId();
        game = null;

        game = this.chessDao.getGame(id);

        assertNotNull(game);
    }

    @Test
    public void testGetUnknownGame() {
        Game game = this.chessDao.getGame(UUID.randomUUID());

        assertNull(game);
    }
}
