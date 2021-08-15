package de.dragon.ocrcut.optionpane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ArrayBlockingQueue;

public class OptionButton extends JButton implements GeneralConfiguration {

    public OptionButton(ArrayBlockingQueue<Integer> queue, int inQueue) {
        super();

        this.setFocusPainted(false);
        this.setFont(new Font(FONT_FAMILY, Font.PLAIN, (int) FONT_SIZE));

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queue.add(inQueue);
            }
        });
    }

}
