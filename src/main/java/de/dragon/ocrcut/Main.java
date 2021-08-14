package de.dragon.ocrcut;

import de.dragon.ocrcut.selector.ExitKeyListener;
import de.dragon.ocrcut.selector.Selector;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws InterruptedException, AWTException, URISyntaxException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, TesseractException, IOException {
        File f = LoadLibs.extractTessResources(LoadLibs.getTesseractLibName());
        System.setProperty("java.library.path", f.getAbsolutePath());

        ITesseract tesseract = new Tesseract();
        tesseract.setOcrEngineMode(1);

        if(System.getenv("TESSDATA_PREFIX") == null) {
            try {
                tesseract.setDatapath(LoadLibs.extractTessResources("tessdata").getAbsolutePath());
            } catch(Exception e) {
                throw new TesseractException("Error: Please make sure the TESSDATA_PREFIX environment variable is set to your \"tessdata\" directory.");
            }
        }


        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.getRootPane().setOpaque(false);
        frame.setBackground(new Color(0, 0, 0, 5));
        frame.setVisible(true);

        Selector selector = new Selector(frame);
        frame.add(selector);
        frame.addMouseListener(selector);
        frame.addKeyListener(new ExitKeyListener(frame));
        frame.validate();

        Rectangle rec = selector.getSelected();
        frame.dispose();

        SwingUtilities.invokeLater(() -> {
            try {
                BufferedImage image = new Robot().createScreenCapture(rec);
                String text = tesseract.doOCR(image);

                int res = JOptionPane.showOptionDialog(frame, "Do you want to copy \"" + text.strip() + "\" to your clipboard?", "Text snippet", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Yes", "Try again"}, "Yes");

                if (res == JOptionPane.YES_OPTION) {
                    StringSelection stringSelection = new StringSelection(text);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                } else if (res == JOptionPane.NO_OPTION) {
                    Runtime.getRuntime().exec("java -jar " + new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath());
                }

                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }

}
