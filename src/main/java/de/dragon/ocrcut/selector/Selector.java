package de.dragon.ocrcut.selector;

import com.sun.jna.Pointer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Selector extends JComponent implements MouseListener {

    private Point start;
    private Point current;

    private JFrame frame;

    private boolean run = true;

    private ArrayBlockingQueue<Rectangle> blockingQueue = new ArrayBlockingQueue<>(5);

    public Selector(JFrame frame) {
        this.frame = frame;

        this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        this.setSize(frame.getSize());
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0, 170), 2));

        new Thread(this::run).start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if (start != null && current != null) {
            graphics.clearRect(0, 0, this.getWidth(), this.getHeight());
            graphics.setColor(Color.WHITE);

            int x = (int) Math.min(start.getX(), current.getX());
            int y = (int) Math.min(start.getY(), current.getY());

            int width = (int) Math.abs(start.getX() - current.getX());
            int height = (int) Math.abs(start.getY() - current.getY());

            graphics.drawRect(x, y, width, height);
            graphics.setColor(new Color(0, 0, 0, 40));
            graphics.fillRect(x, y, width, height);
        } else {
            graphics.clearRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    public void run() {
        Thread.currentThread().setName("Mouse-Agent");

        try {
            while (run) {
                TimeUnit.MICROSECONDS.sleep(20);
                if (start != null) {
                    current = this.getMousePosition();
                    this.repaint();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        start = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        run = false;

        Point startfin = start;
        Point currentfin = current;
        SwingUtilities.convertPointToScreen(startfin, this);
        SwingUtilities.convertPointToScreen(currentfin, this);

        start = null;
        current = null;
        this.repaint();

        this.setVisible(false);
        frame.revalidate();

        blockingQueue.add(new Rectangle((int) Math.min(startfin.getX(), currentfin.getX()), (int) Math.min(startfin.getY(), currentfin.getY()),
                (int) Math.abs(startfin.getX() - currentfin.getX()), (int) Math.abs(startfin.getY() - currentfin.getY())));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public Rectangle getSelected() throws InterruptedException {
        return blockingQueue.take();
    }
}
