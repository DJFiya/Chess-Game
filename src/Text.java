import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Text 
{
	private static Color c = new Color(0, 0, 0);
	private static BufferedImage[] letters = new BufferedImage[26];
	public static void setImages()
	{
		try
		{
			for(int i = 0; i < 26; i++)
			{
				letters[i] = ImageIO.read(new File("letter" + ((i<10)?"0":"") + i + ".png"));
			}
		}catch(IOException e) {System.out.println("Images could not load");}
	}
	
	public static void setColor(Color c1) 
	{
	    for (BufferedImage i : letters) 
	    {
	        if (i != null) 
	        {  
	            for (int j = 0; j < i.getHeight(); j++) 
	            {
	                for (int k = 0; k < i.getWidth(); k++) 
	                {
	                    int p = i.getRGB(k, j);
	                    if (p == c.getRGB()) i.setRGB(k, j, c1.getRGB());
	                }
	            }
	        }
	    }
	}
	
	public static void drawText(String s, Graphics g, int tileSize) {
	    Graphics2D g2 = (Graphics2D) g;
	    s = s.toUpperCase();
	    int totalWidth = s.length() * tileSize / 2+ (s.length()-1)*tileSize/4;
	    int h = tileSize / 2;
	    int startX = tileSize * 5 - totalWidth / 2; 
	    int currentX = startX;
	    
	    for (int i = 0; i < s.length(); i++) {
	    	g2.drawImage(letters[s.charAt(i) - 65], currentX+i*h, (int)(tileSize * 4.75), h, h, null);
	        currentX+=h/2;
	    }
	}
	
	public static void drawText(String s, Graphics g, int tileSize, int centerX, int centerY) {
	    Graphics2D g2 = (Graphics2D) g;
	    s = s.toUpperCase();
	    int totalWidth = s.length() * tileSize / 2+ (s.length()-1)*tileSize/4;
	    int h = tileSize / 2;
	    int startX = centerX - totalWidth / 2; 
	    int topY = centerY - h;
	    int currentX = startX;
	    
	    for (int i = 0; i < s.length(); i++) {
	    	g2.drawImage(letters[s.charAt(i) - 65], currentX+i*h, topY, h, h, null);
	        currentX+=h/2;
	    }
	}
	
	public static void drawText(String s1, String s2, Graphics g, int tileSize) {
	    Graphics2D g2 = (Graphics2D) g;
	    s1 = s1.toUpperCase();
	    s2 = s2.toUpperCase();
	    int w1 = s1.length() * tileSize / 2 + (s1.length()-1)*tileSize/4;
	    int w2 = s2.length() * tileSize / 2 + (s2.length()-1)*tileSize/4;
	    int h = tileSize / 2;
	    int startX1 = tileSize * 5 - w1 / 2; 
	    int startX2 = tileSize * 5 - w2 / 2; 
	    int currentX = startX1;
	    
	    for (int i = 0; i < s1.length(); i++) {
	        g2.drawImage(letters[s1.charAt(i) - 65], currentX + i * h, (int)(tileSize * 4.25), h, h, null);
	        currentX+=h/2;
	    }
	    currentX = startX2;
	    for (int i = 0; i < s2.length(); i++) 
	    {
	    	g2.drawImage(letters[s2.charAt(i) - 65], currentX + i * h, (int)(tileSize * 5.25), h, h, null);
	    	currentX+=h/2;
	    }
	}
}
