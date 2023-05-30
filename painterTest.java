
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

import org.w3c.dom.css.RGBColor;

import javax.imageio.ImageIO;
import javax.swing.*;


import java.io.File;
import java.io.IOException;


/**
 * sets all pixels similar to colors clicked to color myWhite, excludes colors that are too dark
 */

 public class painterTest extends DrawingGUI{

    // maxmimum color difference for checking if pixels matched, the higher the further away the colors can be
    private static final int maxColorDiff = 10;
    // max color difference for checking how dark a color is, the higher the further away from black it can be
    private static final int blackdiff = 80;

    private BufferedImage image;
    private Painter painter;

    // list of background colors which will be processed
    private ArrayList<Color> backgroundColors;

    int myWhite = new Color(247, 236, 202).getRGB();


    public painterTest(String name, BufferedImage image){
        super(name, image.getWidth(), image.getHeight());
        this.image = image;
        
    
        repaint();
    }

    @Override
    public void handleMousePress(int x, int y) {
        backgroundColors = new ArrayList<Color>();

        // radius for the click
        int r = 1;
        // loops through neigbors of pixel clicked on and adds to a list
        for (int row = Math.max(x - r, 0); row < Math.min(x + r + 1, image.getWidth()); row++) {
            for (int col = Math.max(y - r, 0); col < Math.min(y + r + 1, image.getHeight()); col++) {
                Color c = new Color(image.getRGB(row, col));
                if (!backgroundColors.contains(c)){
                    backgroundColors.add(c);
                }
            }
        }

        // loops through all pixels
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {

                // if a pixel matches the colors clicked, note it
                boolean matched = false;
                for (Color p: backgroundColors){
                    if (colorMatch(p, new Color(image.getRGB(j, i)))){
                        matched = true;
                        break;
                    }

                }
                // set pixel to myWhite
                if (matched){
                    if (!colorMatch(Color.BLACK, new Color(image.getRGB(j, i)))){
                        image.setRGB(j, i, myWhite);
                    }
                }
                repaint();
            }
        }
        
        
    }

    /**
     * Tests whether the two colors are "similar enough" (your definition, subject to the maxColorDiff threshold, which you can vary).
     */
    private static boolean colorMatch(Color c1, Color c2) {
        // compares colors
        // if the abs value difference between the r g or b values is greater than the maxColorDiff, it returns false
        if ((Math.abs(c1.getRed() - c2.getRed())) <= maxColorDiff) {
            if ((Math.abs(c1.getGreen() - c2.getGreen())) <= maxColorDiff) {
                if ((Math.abs(c1.getBlue() - c2.getBlue())) <= maxColorDiff) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Tests whether the two colors are "similar enough" (your definition, subject to the maxColorDiff threshold, which you can vary).
     */
    private static boolean blackMatch(Color c1, Color c2) {
        // compares colors
        // if the abs value difference between the r g or b values is greater than the maxColorDiff, it returns false
        if ((Math.abs(c1.getRed() - c2.getRed())) <= blackdiff) {
            if ((Math.abs(c1.getGreen() - c2.getGreen())) <= blackdiff) {
                if ((Math.abs(c1.getBlue() - c2.getBlue())) <= blackdiff) {
                    return true;
                }
            }
        }
        return false;
    }

        /**
     * Make a color brighten.
     *
     * @param color Color to make brighten.
     * @param fraction Darkness fraction.
     * @return Lighter color.
     */
    public static Color brighten(Color color, double fraction) {

        int red = (int) Math.round(Math.min(255, color.getRed() + 255 * fraction));
        int green = (int) Math.round(Math.min(255, color.getGreen() + 255 * fraction));
        int blue = (int) Math.round(Math.min(255, color.getBlue() + 255 * fraction));

        int alpha = color.getAlpha();

        return new Color(red, green, blue, alpha);

    }

    @Override
	public void draw(Graphics g) {
        
		g.drawImage(this.image, 0, 0, null);
        
	}

    public static void main(String[] args) { 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new painterTest("Early Spring", loadImage("early-spring.jpg"));
			}
		});
	}

 }