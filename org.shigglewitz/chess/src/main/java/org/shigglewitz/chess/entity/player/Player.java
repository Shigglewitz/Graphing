package org.shigglewitz.chess.entity.player;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PLAYERS")
public class Player {
    private UUID id;

    public Player() {
    }

    @Id
    @GeneratedValue
    public UUID getId() {
        return this.id;
    }
}
