package org.shigglewitz.chess.controller.service;

import org.shigglewitz.chess.entity.board.Board;
import org.shigglewitz.chess.entity.dao.ChessDao;
import org.shigglewitz.chess.entity.game.Game;
import org.shigglewitz.chess.entity.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("gameService")
public class GameService {
    @Autowired
    private ChessDao chessDao;

    @Transactional
    public Player createPlayer() {
        Player player = new Player();

        this.chessDao.savePlayer(player);

        return player;
    }

    @Transactional
    public Game createGame() {
        return this.createGame(Board.DEFAULT_SIZE);
    }

    @Transactional
    public Game createGame(int size) {
        Game game = new Game(size);

        this.chessDao.saveGame(game);

        return game;
    }
}
