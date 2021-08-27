package de.dragon.ocrcut.selector;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EscKeyListener implements KeyListener {

    private JFrame frame;

    public EscKeyListener(JFrame frame) {
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
