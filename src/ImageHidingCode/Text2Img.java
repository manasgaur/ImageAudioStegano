package ImageHidingCode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.*;
import javax.imageio.ImageIO;

public class Text2Img {

	public static void main(String args[])
	{
        int width = 1000;
        int height = 1000;

        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, 48);
        g2d.setFont(font);
        //FontMetrics fm = g2d.getFontMetrics();

        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setFont(font);
        //fm = g2d.getFontMetrics();
        //FontMetrics fm=g2d.getFontMetrics();
        g2d.dispose();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setColor(Color.BLACK);
        File file = new File("//Users//manasgaur//Documents//javaworkspace//Crypto//src//ImageHidingCode//text.txt");

        BufferedReader br=null;
        int nextLinePosition=100;
        int fontSize = 48;
        try {
            br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {
               g2d.drawString(line, 0, nextLinePosition);
               nextLinePosition = nextLinePosition + fontSize;
            }
        br.close();
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Testing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(Testing.class.getName()).log(Level.SEVERE, null, ex);
        }


        g2d.dispose();
        try {
            ImageIO.write(img, "jpg", new File("Text.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
}
