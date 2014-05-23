package org.shigglewitz.chess.entity.dao.impl;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.shigglewitz.chess.entity.dao.ChessDao;
import org.shigglewitz.chess.entity.game.Game;
import org.shigglewitz.chess.entity.player.Player;
import org.springframework.stereotype.Repository;

@Repository("chessDao")
public class ChessDaoImpl implements ChessDao {
    protected EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public Player getPlayer(UUID id) {
        return this.entityManager.find(Player.class, id);
    }

    @Override
    public Game getGame(UUID id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveGame(Game game) {
        // TODO Auto-generated method stub

    }

    @Override
    public void savePlayer(Player player) {
        this.entityManager.persist(player);
        this.entityManager.flush();
    }

}
