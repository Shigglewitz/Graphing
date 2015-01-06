package org.shigglewitz.game.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.shigglewitz.game.state.GameStateManager;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private static final long serialVersionUID = -2792827444847952045L;

    private Thread thread;
    private boolean running;

    private BufferedImage image;
    private Graphics2D g;

    private GameStateManager gsm;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(Config.WIDTH * Config.SCALE,
                Config.HEIGHT * Config.SCALE));
        setFocusable(true);
        this.requestFocus();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    private void init() {
        image = new BufferedImage(Config.WIDTH, Config.HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        g = image.createGraphics();
        running = true;
        gsm = new GameStateManager();
    }

    @Override
    public void run() {
        init();

        long start;
        long finish;
        long elapsed;
        long wait;

        while (running) {
            start = System.nanoTime();

            this.update();
            draw(g);
            drawToScreen();

            finish = System.nanoTime();
            elapsed = finish - start;
            wait = Config.TARGET_TIME - (elapsed / 1_000_000);

            if (wait > 0) {
                try {
                    Thread.sleep(wait);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void update() {
        gsm.update();
    }

    private void draw(Graphics2D g) {
        gsm.draw(g);
    }

    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, Config.WIDTH * Config.SCALE, Config.HEIGHT
                * Config.SCALE, null);
        g2.dispose();
    }

    @Override
    public void keyPressed(KeyEvent k) {
        gsm.keyPressed(k.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent k) {
        gsm.keyReleased(k.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent k) {
    }
}
