package org.shigglewitz.chess.controller;

import org.shigglewitz.chess.controller.exception.BadRequestException;
import org.shigglewitz.chess.entity.Board;
import org.shigglewitz.chess.entity.Game;
import org.shigglewitz.chess.entity.Player;
import org.shigglewitz.chess.entity.Square;
import org.shigglewitz.chess.entity.Game.Color;
import org.shigglewitz.chess.entity.dao.ChessDao;
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
    
    @Transactional
	public void movePiece(Game game, String startSquareDescr,
			String destSquareDescr) throws BadRequestException {
    	Board board = game.getBoard();
    	Square startSquare = board.getSquare(startSquareDescr);
    	
    	if (startSquare.getPiece() == null) {
    		throw new BadRequestException("No piece at square "+startSquareDescr);
    	}
    	
    	Square destSquare = board.getSquare(destSquareDescr);
    	
    	if (destSquare.getPiece() != null) {
    		destSquare.getPiece().setCaptured(true);
    	}
    	
    	destSquare.setPiece(startSquare.getPiece());
    	startSquare.setPiece(null);
    	
    	switch(game.getNextMove()) {
    	case LIGHT:
    		game.setNextMove(Color.DARK);
    		break;
    	case DARK:
    		game.setNextMove(Color.LIGHT);
    		break;
    	}
    	
    	chessDao.updateGame(game);
    }
}
