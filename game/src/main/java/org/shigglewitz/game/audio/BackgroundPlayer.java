package org.shigglewitz.game.audio;

import javax.sound.sampled.Clip;

public class BackgroundPlayer extends AudioPlayer {

    public BackgroundPlayer(String s) {
        super(s);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
