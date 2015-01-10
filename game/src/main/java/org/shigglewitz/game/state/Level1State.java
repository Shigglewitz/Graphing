package org.shigglewitz.game.state;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.shigglewitz.game.audio.AudioPlayer;
import org.shigglewitz.game.audio.BackgroundPlayer;
import org.shigglewitz.game.config.Config;
import org.shigglewitz.game.config.Controls;
import org.shigglewitz.game.entity.Enemy;
import org.shigglewitz.game.entity.Explosion;
import org.shigglewitz.game.entity.HUD;
import org.shigglewitz.game.entity.Player;
import org.shigglewitz.game.entity.enemies.Slugger;
import org.shigglewitz.game.tilemap.Background;
import org.shigglewitz.game.tilemap.TileMap;

public class Level1State extends GameState {

    private TileMap tileMap;
    private Background bg;

    private Player player;
    private List<Enemy> enemies;
    private List<Explosion> explosions;
    private HUD hud;

    private AudioPlayer bgMusic;

    public Level1State(GameStateManager gsm) {
        this.gsm = gsm;
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

        populateEnemies();

        explosions = new ArrayList<>();

        hud = new HUD(player);

        bgMusic = new BackgroundPlayer(Config.LEVEL_1_1_MUSIC);
        bgMusic.play();

        initialized = true;
    }

    private void populateEnemies() {
        enemies = new ArrayList<>();

        Point[] locations = new Point[] { new Point(200, 100),
                new Point(860, 200), new Point(1525, 200),
                new Point(1680, 200), new Point(1800, 200), };

        Slugger s;
        for (Point p : locations) {
            s = new Slugger(tileMap);
            s.setPosition(p.getX(), p.getY());
            enemies.add(s);
        }

    }

    @Override
    protected void update() {
        // update player
        player.update();
        tileMap.setPosition(Config.WIDTH / 2 - player.getX(), Config.HEIGHT / 2
                - player.getY());

        // set background
        bg.setPosition(tileMap.getX(), tileMap.getY());

        // attack enemies
        player.checkAttack(enemies);

        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.isDead()) {
                enemies.remove(i);
                i--;
                explosions.add(new Explosion(tileMap, e.getX(), e.getY()));
            }
        }

        for (int i = 0; i < explosions.size(); i++) {
            Explosion e = explosions.get(i);
            e.update();
            if (e.shouldRemove()) {
                explosions.remove(i);
                i--;
            }
        }
    }

    @Override
    protected void draw(Graphics2D g) {
        bg.draw(g);

        tileMap.draw(g);

        player.draw(g);

        for (Enemy e : enemies) {
            e.draw(g);
        }

        for (Explosion e : explosions) {
            e.draw(g);
        }

        hud.draw(g);
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
