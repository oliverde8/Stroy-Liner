
package fr.odecram.story_liner.views;

import fr.odecram.story_liner.views.leftSection.VerticalSectionSeparator;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author De Cramer Oliver
 */
public class RightSection extends JPanel{

	private VerticalSectionSeparator left_separator;
	
	private MainContent mContent;

	public RightSection(MainContent mContent) {
		this.mContent = mContent;
		
		left_separator = new VerticalSectionSeparator(mContent);
	}
	
	
	
	
	public void paint(Graphics g){
		System.out.println("Test");
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		left_separator.view_paint(g, WIDTH, WIDTH);
	}
	
	
}
