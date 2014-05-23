package org.shigglewitz.chess.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.shigglewitz.chess.entity.dao.ChessDao;
import org.shigglewitz.chess.entity.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration({ "classpath:applicationContext-hibernate.xml",
        "classpath:applicationContext-configBeans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ChessDaoImplTest {
    @Autowired
    private ChessDao chessDao;

    @Test
    public void testSavePlayer() {
        Player player = new Player();

        this.chessDao.savePlayer(player);
    }
}
