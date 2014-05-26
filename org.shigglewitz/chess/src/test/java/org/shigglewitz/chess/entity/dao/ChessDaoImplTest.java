package org.shigglewitz.chess.entity.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shigglewitz.chess.controller.GameService;
import org.shigglewitz.chess.entity.Board;
import org.shigglewitz.chess.entity.Game;
import org.shigglewitz.chess.entity.Player;
import org.shigglewitz.chess.entity.dao.ChessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Ignore
@ContextConfiguration({ "classpath:applicationContext-hibernate.xml",
		"classpath:applicationContext-daoBeans.xml",
		"classpath:applicationContext-serviceBeans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ChessDaoImplTest {
	@Autowired
	private ChessDao chessDao;

	@Autowired
	private GameService gameService;

	@Test
	@Transactional
	public void testSavePlayer() {
		Player player = new Player();

		this.chessDao.savePlayer(player);

		assertNotNull(player.getId());
	}

	@Test
	public void testGetPlayer() {
		Player player = this.gameService.createPlayer();
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
	@Transactional
	public void testSaveGame() {
		Game game = new Game(5);

		this.chessDao.saveGame(game);

		assertNotNull(game.getId());
	}

	@Test
	public void testGetGame() {
		Game game = this.gameService.createGame();
		UUID id = game.getId();
		game = null;

		game = this.chessDao.getGame(id);

		assertNotNull(game);
		assertEquals(Board.DEFAULT_SIZE, game.getBoard().getSize());
	}

	@Test
	public void testGetUnknownGame() {
		Game game = this.chessDao.getGame(UUID.randomUUID());

		assertNull(game);
	}

	@Test
	@Transactional
	public void testCreateGame() {
		Player player1 = new Player();
		Game game = new Game(5);

		game.setLightPlayer(player1);

		this.chessDao.savePlayer(player1);
		this.chessDao.saveGame(game);
	}
}
