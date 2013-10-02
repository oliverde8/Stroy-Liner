
package en.odecram.story_liner.views;

import en.odecram.story_liner.views.dragables.models.Abs_StepElement;
import en.odecram.story_liner.views.models.Abs_View;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * The drag panel shown above all to alow objects to be dragged 
 * across different panels
 * @author De Cramer Oliver
 */
public class DragPanel extends Abs_View{
	
	MainContent mc;
	
	Abs_StepElement v;
	
	public DragPanel(MainContent mc) {
		this.mc =mc;
	}

	/**
	 * Will paint the Content into the graphics
	 * 
	 * @param g The graphics in which the painting will be done
	 */
	public void paint(Graphics g){		
		//We clear anything we might have already drawn
		//g.clearRect(0, 0, getWidth(), getHeight());
		
		//We don(t need any 3D so we will make everything simplier by casting graphics to 2DGraphics
		Graphics2D g2d = (Graphics2D)g;
		
		//Antiliazing to have nice drag & drops 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
		
		if(v != null)
			v.view_paint(g, 1);
	}
	
	public void setV(Abs_StepElement v) {		
		this.v = v;
		if(v != null)
			v.setParent(this);
	}
}
