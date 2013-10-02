package en.odecram.story_liner.skin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Logo extends JPanel {

	Image logo;
	Image logoFond;
	
	/**
	 * Constructeur du logo
	 */
	public Logo(){
		try {
			logo = ImageIO.read(new File("img/logo.jpg"));
			logoFond = ImageIO.read(new File("img/logofond.jpg"));
		} catch (IOException e) {}
		
		this.setMinimumSize(new Dimension(0,80));
		this.setPreferredSize(new Dimension(0,80));
	}
	
	/**
	 * Methode de dessin du logo
	 */
	public void paint(Graphics g){
		Graphics2D dessin = (Graphics2D)g;
		if(logoFond != null && logo != null){
			dessin.drawImage(logo , 0, 0, this);
			dessin.drawImage(logoFond, logo.getWidth(this), 0, this.getWidth() -  logo.getWidth(this) , 80 , this);
		} else {
			GradientPaint degrade = new GradientPaint(0, 0, Color.BLACK, 0, this.getHeight(), new Color(32,32,32));
			dessin.setPaint(degrade);
			dessin.fillRect(0, 0, this.getWidth(), this.getHeight());
			degrade = new GradientPaint(0, 0,Color.GRAY, 0, this.getHeight(), Color.LIGHT_GRAY);
			dessin.setFont(new Font("sans-serif",Font.BOLD,30));
			dessin.setPaint(degrade);
			dessin.drawString("Story Liner", 30, this.getHeight()-20);
		}
	}

	
}
