package org.shigglewitz.game.main;

import javax.swing.JFrame;

import org.shigglewitz.game.config.Config;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame(Config.WINDOW_TITLE);
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }
}
