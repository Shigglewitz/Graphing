package org.shigglewitz.chess.controller;

import java.util.UUID;

import org.shigglewitz.chess.controller.exception.BadRequestException;
import org.shigglewitz.chess.entity.Game;
import org.shigglewitz.chess.entity.Player;
import org.shigglewitz.chess.entity.dao.ChessDao;
import org.springframework.beans.factory.annotation.Autowired;

public class Controller {
	public static final String UUID_REGEX = "[a-f0-9]{8}(-[a-f0-9]{4}){3}-[a-f0-9]{12}";

	@Autowired
	private ChessDao chessDao;

	@Autowired
	private GameService gameService;

	public void startGame(String playerId, String colorChoice) {

	}

	public void movePiece(String gameId, String playerId, String startSquare,
			String destSquare) throws BadRequestException {
		if (!validUuid(gameId)) {
			throw new BadRequestException(invalidGameIdMessage(gameId));
		}
		if (!validUuid(playerId)) {
			throw new BadRequestException(invalidPlayerIdMessage(playerId));
		}

		Game game = chessDao.getGame(UUID.fromString(gameId));
		if (game == null) {
			throw new BadRequestException(invalidGameIdMessage(gameId));
		}

		Player player = chessDao.getPlayer(UUID.fromString(playerId));
		if (player == null) {
			throw new BadRequestException(invalidPlayerIdMessage(playerId));
		}

		switch (game.getNextMove()) {
		case LIGHT:
			validatePlayersTurn(game.getLightPlayer(), player);
			break;
		case DARK:
			validatePlayersTurn(game.getDarkPlayer(), player);
			break;
		default:
			break;
		}

		if (!game.getBoard().validateSquareDescr(startSquare)) {
			throw new BadRequestException("Invalid starting square: "
					+ startSquare);
		}

		if (!game.getBoard().validateSquareDescr(destSquare)) {
			throw new BadRequestException("Invalid destination square: "
					+ destSquare);
		}

		gameService.movePiece(game, startSquare, destSquare);
	}

	private String invalidGameIdMessage(String gameId) {
		return "Invalid game id: " + gameId;
	}

	private String invalidPlayerIdMessage(String playerId) {
		return "Invalid player id: " + playerId;
	}

	private void validatePlayersTurn(Player nextMove, Player player)
			throws BadRequestException {
		if (!nextMove.getId().equals(player.getId())) {
			throw new BadRequestException("Not player " + player.getId()
					+ "'s move");
		}
	}

	public static boolean validUuid(String uuid) {
		return uuid.matches(UUID_REGEX);
	}
}
