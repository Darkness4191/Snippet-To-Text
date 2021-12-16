package de.dragon.ocrcut.progress;

import javax.swing.*;
import java.awt.*;

public class ProgressBar {

    private JFrame frame;

    public ProgressBar() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 42);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setTitle("Getting Text...");
        frame.setLocationRelativeTo(null);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        JLabel label = new JLabel("Loading Text...");
        label.setBorder(BorderFactory.createLineBorder(frame.getBackground(), 4));

        frame.add(label, BorderLayout.NORTH);
        frame.add(progressBar, BorderLayout.CENTER);
        frame.validate();
    }

    public JFrame getFrame() {
        return frame;
    }

}
