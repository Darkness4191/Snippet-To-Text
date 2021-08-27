package de.dragon.ocrcut.selector;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;

public class SelectorFrames {

    private ArrayBlockingQueue<Rectangle> q = new ArrayBlockingQueue<>(10);
    private JFrame[] frames;

    public SelectorFrames() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = environment.getScreenDevices();
        frames = new JFrame[devices.length];

        for(int i = 0; i < devices.length; i++) {
            frames[i] = new JFrame();
            frames[i].setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frames[i].setExtendedState(JFrame.MAXIMIZED_BOTH);
            frames[i].setUndecorated(true);
            frames[i].getRootPane().setOpaque(false);
            frames[i].setBackground(new Color(0, 0, 0, 5));
            frames[i].setVisible(true);

            Selector selector = new Selector(frames[i], q, devices[i].getDisplayMode().getRefreshRate());
            frames[i].add(selector);
            frames[i].addMouseListener(selector);
            frames[i].addKeyListener(new EscKeyListener(frames[i]));
            frames[i].validate();

            frames[i].setLocation(devices[i].getDefaultConfiguration().getBounds().x, devices[i].getDefaultConfiguration().getBounds().y + frames[i].getY());
            frames[i].setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }

    public Rectangle getSelected() throws InterruptedException {
        return q.take();
    }

    public void dispose() {
        for(JFrame frame: frames) {
            frame.dispose();
        }
    }

}
