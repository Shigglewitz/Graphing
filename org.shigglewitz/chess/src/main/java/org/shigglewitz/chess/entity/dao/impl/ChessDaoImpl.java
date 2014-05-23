package org.shigglewitz.chess.entity.dao.impl;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.shigglewitz.chess.entity.dao.ChessDao;
import org.shigglewitz.chess.entity.game.Game;
import org.shigglewitz.chess.entity.player.Player;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        return this.entityManager.find(Game.class, id);
    }

    @Override
    @Transactional
    public void saveGame(Game game) {
        this.entityManager.persist(game);
        this.entityManager.flush();
    }

    @Override
    @Transactional
    public void savePlayer(Player player) {
        this.entityManager.persist(player);
        this.entityManager.flush();
    }

}
