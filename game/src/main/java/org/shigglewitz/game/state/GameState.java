package org.shigglewitz.game.state;

import java.awt.Graphics2D;

public abstract class GameState {
    protected GameStateManager gsm;
    protected boolean initialized = false;

    protected abstract void init();

    protected abstract void update();

    protected abstract void draw(Graphics2D g);

    protected abstract void keyPressed(int k);

    protected abstract void keyReleased(int k);

    public boolean isInitialized() {
        return initialized;
    }
}
