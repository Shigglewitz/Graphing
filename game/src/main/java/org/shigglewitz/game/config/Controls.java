package org.shigglewitz.game.config;

import java.awt.event.KeyEvent;

public interface Controls {
    public static final int LEFT = KeyEvent.VK_A;
    public static final int RIGHT = KeyEvent.VK_D;
    public static final int JUMP = KeyEvent.VK_SPACE;
    public static final int GLIDE = KeyEvent.VK_W;
    public static final int SCRATCH = KeyEvent.VK_R;
    public static final int FIREBALL = KeyEvent.VK_F;

    // unimplemented
    public static final int UP = -1;
    public static final int DOWN = -2;
}
