package org.shigglewitz.game.state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import org.shigglewitz.game.main.Config;
import org.shigglewitz.game.tilemap.Background;

public class MenuState extends GameState {

    private Background bg;
    private final String[] options = { "Start", "Help", "Quit" };
    private int current = 0;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;

        try {
            bg = new Background(Config.MENU_BACKGROUND, 1);
            bg.setVector(-0.1, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void init() {

    }

    @Override
    protected void update() {
        bg.update();
    }

    @Override
    protected void draw(Graphics2D g) {
        bg.draw(g);

        g.setColor(Color.RED);
        g.drawString("MENU TITLE", 80, 70);

        for (int i = 0; i < options.length; i++) {
            if (i == current) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.BLUE);
            }
            g.drawString(options[i], 80, 100 + i * 15);
        }
    }

    private void select() {
        if (current == 0) {
            System.out.println("START!");
        } else if (current == 1) {
            System.out.println("HELP!");
        } else if (current == 2) {
            System.exit(0);
        }
    }

    @Override
    protected void keyPressed(int k) {
        switch (k) {
        case KeyEvent.VK_ENTER:
            select();
            break;
        case KeyEvent.VK_DOWN:
            current++;
            if (current >= options.length) {
                current = 0;
            }
            break;
        case KeyEvent.VK_UP:
            current--;
            if (current < 0) {
                current = options.length - 1;
            }
            break;
        }
    }

    @Override
    protected void keyReleased(int k) {
    }

}
