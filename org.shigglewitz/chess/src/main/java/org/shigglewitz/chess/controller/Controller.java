package org.shigglewitz.chess.controller;

import org.shigglewitz.chess.entity.dao.ChessDao;
import org.springframework.beans.factory.annotation.Autowired;

public class Controller {
    @Autowired
    private ChessDao chessDao;

    public void movePiece(String gameId, String playerId, String pieceId,
            String destSquare) {

    }
}
