package org.shigglewitz.chess.dao.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.shigglewitz.chess.entity.dao.ChessDao;
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

        Player dbPlayer = this.chessDao.getPlayer(player.getId());
        assertNotNull(dbPlayer);
    }
}
