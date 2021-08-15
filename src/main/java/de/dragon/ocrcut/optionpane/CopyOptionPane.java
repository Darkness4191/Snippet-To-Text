package de.dragon.ocrcut.optionpane;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;

public class CopyOptionPane extends JFrame implements Options, GeneralConfiguration {

    private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

    public CopyOptionPane() {
        super();
    }

    public int showOptions(String panetext) throws InterruptedException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(400, 300);
        this.setTitle("Text Snippet");
        this.setVisible(true);

        JTextPane pane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(pane);
        pane.setFont(new Font(FONT_FAMILY, Font.PLAIN, (int) FONT_SIZE - 1));
        pane.setEditable(false);
        pane.setText(panetext);
        pane.setBackground(this.getBackground());

        OptionButton confirm = new OptionButton(queue, YES);
        OptionButton tryagain = new OptionButton(queue, AGAIN);
        confirm.setText("Yes");
        tryagain.setText("Try again");

        JPanel panelButtons = new JPanel();
        GridLayout layout = new GridLayout();
        layout.setColumns(2);
        layout.setRows(1);
        layout.setVgap(4);
        panelButtons.setLayout(layout);
        panelButtons.add(confirm);
        panelButtons.add(tryagain);

        JLabel label = new JLabel("Do you want to save the displayed text to your clipboard?");
        label.setFont(new Font(FONT_FAMILY, Font.PLAIN, (int) FONT_SIZE));
        JPanel panellabel = new JPanel();
        panellabel.setLayout(new GridLayout());
        panellabel.setBorder(BorderFactory.createLineBorder(this.getBackground(), 3));
        panellabel.add(label);

        this.add(panellabel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(panelButtons, BorderLayout.SOUTH);

        SwingUtilities.invokeLater(this::validate);

        return queue.take();
    }

}
