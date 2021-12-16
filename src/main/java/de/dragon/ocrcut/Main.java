package de.dragon.ocrcut;

import de.dragon.ocrcut.pane.CopyOptionPane;
import de.dragon.ocrcut.progress.ProgressBar;
import de.dragon.ocrcut.selector.SelectorFrames;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

public class Main {

    private static String text;

    public static void main(String[] args) throws InterruptedException, AWTException, URISyntaxException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, TesseractException, IOException, InvocationTargetException {
        while(true) {

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

            SelectorFrames frames = new SelectorFrames();
            Rectangle rec = frames.getSelected();

            frames.dispose();

            ProgressBar progressBar = new ProgressBar();

            SwingUtilities.invokeAndWait(() -> {
                try {
                    BufferedImage image = new Robot().createScreenCapture(rec);
                    //new Optimizer().optimize(image);
                    text = tesseract.doOCR(image);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            });

            progressBar.getFrame().dispose();

            int res = new CopyOptionPane().showOptions(text);

            if (res == CopyOptionPane.YES) {
                StringSelection stringSelection = new StringSelection(text);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                System.exit(0);
            } else if (res != CopyOptionPane.AGAIN) {
                System.exit(0);
            }
        }
    }
}
