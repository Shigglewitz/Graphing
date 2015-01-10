package org.shigglewitz.game.state;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import org.shigglewitz.game.entity.Player;
import org.shigglewitz.game.main.Config;
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
        if (k == KeyEvent.VK_LEFT) {
            player.setLeft(true);
        }
        if (k == KeyEvent.VK_RIGHT) {
            player.setRight(true);
        }
        if (k == KeyEvent.VK_UP) {
            player.setUp(true);
        }
        if (k == KeyEvent.VK_DOWN) {
            player.setDown(true);
        }
        if (k == KeyEvent.VK_W) {
            player.setJumping(true);
        }
        if (k == KeyEvent.VK_E) {
            player.setGliding(true);
        }
        if (k == KeyEvent.VK_R) {
            player.setScratching();
        }
        if (k == KeyEvent.VK_F) {
            player.setFiring();
        }
    }

    @Override
    protected void keyReleased(int k) {
        if (k == KeyEvent.VK_LEFT) {
            player.setLeft(false);
        }
        if (k == KeyEvent.VK_RIGHT) {
            player.setRight(false);
        }
        if (k == KeyEvent.VK_UP) {
            player.setUp(false);
        }
        if (k == KeyEvent.VK_DOWN) {
            player.setDown(false);
        }
        if (k == KeyEvent.VK_W) {
            player.setJumping(false);
        }
        if (k == KeyEvent.VK_E) {
            player.setGliding(false);
        }
    }

}
