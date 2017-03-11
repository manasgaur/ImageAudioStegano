package ImageHidingCode;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Text2binary {

	static BufferedImage image;
	static String getBits(byte b)
	{
	    String result = "";
	    for(int i = 0; i < 8; i++)
	        result += (b & (1 << i)) == 0 ? "0" : "1";
	    return result;
	}
	public Text2binary(BufferedImage image)
	{
		this.image=image;
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
     
		BufferedImage img=ImageIO.read(new File("//Users//manasgaur//Documents//javaworkspace//Crypto//src//ImageHidingCode//host_image.jpg"));
		Text2binary t2b=new Text2binary(img);
		//Text2Binary(img);
		BufferedReader br=new BufferedReader(new FileReader("//Users//manasgaur//Documents//javaworkspace//Crypto//src//ImageHidingCode//text.txt"));
	    String line="";
	    String content="";
	    
		while((line=br.readLine())!=null)
		{
			byte []bst=line.getBytes();
			for(byte b: bst)
			{
				content+=getBits(b);
			}
		}
		int bits=5; // will be given the GUI interface
		int maskBits = (int)(Math.pow(2, bits)) - 1 << (8 - bits);
		  int mask = (maskBits << 24) | (maskBits << 16) | (maskBits << 8) | maskBits;
          String []maskedtext=content.split("");
          String []masktext=new String[content.length()];
		  for (int i = 0; i < content.length(); i++)
		  {
		  masktext[i] = String.valueOf((Integer.parseInt(maskedtext[i]) & mask));
		  }
		  //int data=Integer.parseInt(content);
		// encode the image with the text
		  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));
		  int encodeByteMask = (int)(Math.pow(2, bits)) - 1 << (8 - bits);
		  int encodeMask = (encodeByteMask << 24) | (encodeByteMask << 16) | (encodeByteMask << 8) | encodeByteMask;

		  int decodeByteMask = ~(encodeByteMask >>> (8 - bits)) & 0xFF;
		  int hostMask = (decodeByteMask << 24) | (decodeByteMask << 16) | (decodeByteMask << 8) | decodeByteMask;
	
		  for(int i=0; i<imageRGB.length; i++)
		  {
			  int encodedata= (Integer.parseInt(maskedtext[i])&encodeMask)>>>(8-bits);
		      imageRGB[i] = (imageRGB[i] & hostMask) | (encodedata & ~hostMask);
		  }
		  image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
		  ImageIcon icon=new ImageIcon(image);
	        JFrame frame=new JFrame();
	        frame.setLayout(new FlowLayout());
	        frame.setSize(200,300);
	        JLabel lbl=new JLabel();
	        lbl.setIcon(icon);
	        frame.add(lbl);
	        frame.setVisible(true);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
