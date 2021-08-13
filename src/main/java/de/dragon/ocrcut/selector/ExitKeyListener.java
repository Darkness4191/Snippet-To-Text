package de.dragon.ocrcut.selector;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ExitKeyListener implements KeyListener {

    private JFrame frame;

    public ExitKeyListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyCode() == 0) {
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
