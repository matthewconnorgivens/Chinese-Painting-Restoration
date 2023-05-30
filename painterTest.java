
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
 * click a point in the background of a painting and the program loops through its neighbors and looks through
 * all the pixels in the painting and changes them to the color "myWhite"
 */

 public class painterTest extends DrawingGUI{

    private static final int maxColorDiff = 10;

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


    /**
     * takes pixel clicked on and looks are nieghbors and turns all pixels similar to those colors to myWhite
     */
    @Override
    public void handleMousePress(int x, int y) {
        backgroundColors = new ArrayList<Color>();

        int r = 1;
        for (int row = Math.max(x - r, 0); row < Math.min(x + r + 1, image.getWidth()); row++) {
            for (int col = Math.max(y - r, 0); col < Math.min(y + r + 1, image.getHeight()); col++) {
                Color c = new Color(image.getRGB(row, col));
                if (!backgroundColors.contains(c)){
                    backgroundColors.add(c);
                }
            }
        }

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {

                boolean matched = false;
                for (Color p: backgroundColors){
                    if (colorMatch(p, new Color(image.getRGB(j, i)))){
                        matched = true;
                        break;
                    }

                }

                if (matched){
                    image.setRGB(j, i, myWhite);
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