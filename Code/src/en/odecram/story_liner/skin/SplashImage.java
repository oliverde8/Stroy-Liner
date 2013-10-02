package en.odecram.story_liner.skin;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SplashImage extends JPanel {

	private Image image;
	
	public SplashImage() {
		super();
		try {
			image = ImageIO.read(new File("img/splash.png"));
		} catch (IOException e) {}
	}
	
	public void paint(Graphics g){
		
		Graphics2D dessin = (Graphics2D)g;
		if(image != null){
			dessin.drawImage(image, 0, 0, this);
			dessin.setColor(Color.GRAY);
		}
		else{
			GradientPaint degrade = new GradientPaint(0, 0, Color.DARK_GRAY, 0, this.getHeight(), Color.BLACK);
			dessin.setPaint(degrade);
			dessin.fillRect(0, 0, this.getWidth(), this.getHeight());
			dessin.setColor(Color.GRAY);
			dessin.drawString("Oliver DE CRAMER - Amghar Nassim ", 20, this.getHeight()-10);
			dessin.setFont(new Font("sans-serif",Font.BOLD,60));
			degrade = new GradientPaint(0, 0, Color.WHITE, 0, this.getHeight(), Color.BLUE);
			dessin.setPaint(degrade);
			dessin.drawString("Story Liner", 40, 130);
		}
		dessin.drawRoundRect(0, 0, this.getWidth(), this.getHeight(), 3, 3);
	}
	
}
