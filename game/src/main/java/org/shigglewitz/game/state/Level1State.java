package org.shigglewitz.game.state;

import java.awt.Graphics2D;

import org.shigglewitz.game.config.Config;
import org.shigglewitz.game.config.Controls;
import org.shigglewitz.game.entity.Player;
import org.shigglewitz.game.tilemap.Background;
import org.shigglewitz.game.tilemap.TileMap;

public class Level1State extends GameState {

    private TileMap tileMap;
    private Background bg;

    private Player player;

    public Level1State(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    @Override
    protected void init() {
        tileMap = new TileMap(30);
        tileMap.loadTiles(Config.LEVEL_1_TILESET);
        tileMap.loadMap(Config.LEVEL_1_1_MAP);
        tileMap.setPosition(0, 0);

        bg = new Background(Config.GRASS_BACKGROUND, 0.1);

        player = new Player(tileMap);
        player.setPosition(100, 100);
    }

    @Override
    protected void update() {
        player.update();

        tileMap.setPosition(Config.WIDTH / 2 - player.getX(), Config.HEIGHT / 2
                - player.getY());
    }

    @Override
    protected void draw(Graphics2D g) {
        bg.draw(g);

        tileMap.draw(g);

        player.draw(g);
    }

    @Override
    protected void keyPressed(int k) {
        if (k == Controls.LEFT) {
            player.setLeft(true);
        }
        if (k == Controls.RIGHT) {
            player.setRight(true);
        }
        if (k == Controls.UP) {
            player.setUp(true);
        }
        if (k == Controls.DOWN) {
            player.setDown(true);
        }
        if (k == Controls.JUMP) {
            player.setJumping(true);
        }
        if (k == Controls.GLIDE) {
            player.setGliding(true);
        }
        if (k == Controls.SCRATCH) {
            player.setScratching();
        }
        if (k == Controls.FIREBALL) {
            player.setFiring();
        }
    }

    @Override
    protected void keyReleased(int k) {
        if (k == Controls.LEFT) {
            player.setLeft(false);
        }
        if (k == Controls.RIGHT) {
            player.setRight(false);
        }
        if (k == Controls.UP) {
            player.setUp(false);
        }
        if (k == Controls.DOWN) {
            player.setDown(false);
        }
        if (k == Controls.JUMP) {
            player.setJumping(false);
        }
        if (k == Controls.GLIDE) {
            player.setGliding(false);
        }
    }

}
