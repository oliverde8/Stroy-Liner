
package en.odecram.story_liner.views.sections;

import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.dragables.VerticalSectionSeparator;
import en.odecram.story_liner.views.models.Abs_View;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author De Cramer Oliver
 */
public class StepEditionSection4 extends JPanel{

	/**
	 * The main Content Panel
	 */
	MainContent mContent;
	
	VerticalSectionSeparator vss;
	
	public StepEditionSection4(MainContent mContent) {
		
		vss = new VerticalSectionSeparator(mContent);
		
	}
	
	public void paint(Graphics g){		
		//We clear anything we might have already drawn
		g.clearRect(0, 0, getWidth(), getHeight());
		
		//Lets draw the left Separator
		vss.view_paint(g, 10, getHeight());
	}
	
}
