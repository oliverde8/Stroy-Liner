
package en.odecram.story_liner.views.sections.stepMenage;

import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.dragables.VerticalSectionSeparator;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * Drawing a Vertical Section Separator between left ection and step menage
 * @author De Cramer Oliver
 */
public class VerticalSeparator extends JPanel{

	MainContent mc;
	
	VerticalSectionSeparator vss;
	
	public VerticalSeparator(MainContent mc) {
		vss = new VerticalSectionSeparator(mc);
		mc.mouse_addViewToMouse(vss);
		this.mc =mc;
	}
	
	/**
	 * Will paint the Content into the graphics
	 * 
	 * @param g The graphics in which the painting will be done
	 */
	public void paint(Graphics g){		
		//We clear anything we might have already drawn
		g.clearRect(0, 0, getWidth(), getHeight());
		
		//We don(t need any 3D so we will make everything simplier by casting graphics to 2DGraphics
		Graphics2D g2d = (Graphics2D)g;
		
		//Antiliazing to have nice drag & drops 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Lets draw the left Separator
		vss.view_paint(g, 10, getHeight());
	}
}
