package org.shigglewitz.game.state;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class GameStateManager {
    private final List<GameState> gameStates;
    private int currentState = 0;

    public static final int MENU_STATE = 0;
    public static final int LEVEL_1_STATE = 1;

    public GameStateManager() {
        gameStates = new ArrayList<>();

        gameStates.add(new MenuState(this));
        gameStates.add(new Level1State(this));

        setState(MENU_STATE);
    }

    public void setState(int state) {
        currentState = state;
        gameStates.get(currentState).init();
    }

    public void update() {
        if (gameStates.get(currentState).isInitialized()) {
            gameStates.get(currentState).update();
        }
    }

    public void draw(Graphics2D g) {
        if (gameStates.get(currentState).isInitialized()) {
            gameStates.get(currentState).draw(g);
        }

    }

    public void keyPressed(int k) {
        if (gameStates.get(currentState).isInitialized()) {
            gameStates.get(currentState).keyPressed(k);
        }
    }

    public void keyReleased(int k) {
        if (gameStates.get(currentState).isInitialized()) {
            gameStates.get(currentState).keyReleased(k);
        }
    }
}
