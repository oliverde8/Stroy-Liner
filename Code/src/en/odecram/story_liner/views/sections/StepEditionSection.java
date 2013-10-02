
package en.odecram.story_liner.views.sections;

import en.odecram.story_liner.models.Step;
import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.dragables.TextElement;
import en.odecram.story_liner.views.dragables.VerticalSectionSeparator;
import en.odecram.story_liner.views.dragables.models.Abs_StepElement;
import en.odecram.story_liner.views.models.Abs_View;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author De Cramer Oliver
 */
public class StepEditionSection extends Abs_View{
	
	/**
	 * The main Content Panel
	 */
	MainContent mContent;
	
	/**
	 * VErtical Section separator
	 */
	VerticalSectionSeparator vss;
	
	/**
	 * The Current Step
	 */
	Step currentStep;
	
	/**
	 * @param mc The main content panel
	 */
	public StepEditionSection(MainContent mc) {
		//Creating the Vertical section separator
		vss = new VerticalSectionSeparator(mc);
		this.mContent =mc;
	}
	
	/**
	 * Init's the Section. once the mouse listener is ready
	 */
	public void init(){
		mContent.mouse_addViewToMouse(vss);
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
		
		//Lets draw The Image.
		currentStep.view_paint(g, scale);
		
		//Lets draw the left Separator
		//vss.view_paint(g, 10, getHeight());
	}
	
	/**
	 * Add the element to the section
	 * @param el
	 * @return 
	 */
	public boolean addElement(Abs_StepElement el){
		//If element null no we don't won't to add it
		if( el == null)
			return false;
		
		Point p = (Point) el.getProp().getPos().clone();
		p.x -= (this.getLocationOnScreen().x-mContent.getLocationOnScreen().x);
		p.y -= (this.getLocationOnScreen().y-mContent.getLocationOnScreen().y);
		
		System.out.println(" X : "+p.x);
		
		Dimension d = el.getProp().getDim();
		
		//If the element is in the Panel let's get it
		if(this.contains(p) || el instanceof TextElement){
			//Change it's parent
			el.setParent(this);
			//Make it selectable
			el.setIsSelectable(true);
			//Add it to the Array
			currentStep.addElement(el);
			//Add it to the mouse listener.
			mContent.mouse_addViewToMouse(el);
			return true;
		}
		
		return false;
	}
	
	public void setStep(Step s){
		if(currentStep != null)
			currentStep.dprepare();
		currentStep = s;
		s.prepare();
	}
	
	public Step getStep(){
		return currentStep;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	
}
