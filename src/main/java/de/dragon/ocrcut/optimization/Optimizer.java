package de.dragon.ocrcut.optimization;

import ch.qos.logback.classic.pattern.CallerDataConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Optimizer {

    private HashMap<Integer, Integer> all_colors = new HashMap<>();

    //ARCHIVED
    public void optimize(BufferedImage image) throws IOException {
        for(int i = 0; i < image.getWidth(); i++) {
            for(int j = 0; j < image.getHeight(); j++) {
                int rgb = image.getRGB(i, j);

                all_colors.put(rgb, all_colors.getOrDefault(rgb, 0) + 1);
            }
        }

        int[] values = new int[2];

        all_colors.forEach((k, v) -> {
            values[0] = Math.max(v, values[0]);
            values[1] = Math.max(v, values[0]) == v ? k : values[1];
        });

        for(int i = 0; i < image.getWidth(); i++) {
            for(int j = 0; j < image.getHeight(); j++) {
                if(image.getRGB(i, j) == values[1]) {
                    image.setRGB(i, j, Color.WHITE.getRGB());
                } else {
                    image.setRGB(i, j, Color.BLACK.getRGB());
                }
            }
        }
    }

}
