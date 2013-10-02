package en.odecram.story_liner.views.dragables;

import en.odecram.story_liner.models.interfaces.OnMouse;
import en.odecram.story_liner.models.stepElement.Properties;
import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.dragables.models.Abs_StepElement;
import en.odecram.story_liner.views.models.Abs_View;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * This is a Step Element that shows an Image
 *
 * @author De Cramer Oliver
 */
public class RectangleElement extends Abs_StepElement {


	public RectangleElement(Abs_StepElement origin){
		super(origin);
	}
	/**
	 *
	 * @param mContent The main Content view
	 * @param prop THe properties of the Element
	 * @param parent The parent view of the element
	 * @param img The path to the image to be shown
	 */
	public RectangleElement(MainContent mContent, Properties prop, Abs_View parent, String img) {
		//Calling parent constructor
		super(mContent, prop, parent);
	}
	
	public RectangleElement(MainContent mContent, Properties prop, Abs_View parent){
		//Calling parent constructor
		super(mContent, prop, parent);
	}

	@Override
	public void paint(Graphics g, Point pos, Dimension dim, float scale) {
		//To draw an image we need a Grapics 2D
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(prop.getBgColor());
		g2d.fillRect(pos.x, pos.y, dim.width, dim.height);
		
		g2d.setColor(prop.getLineColor());
		g2d.drawRect(pos.x, pos.y, dim.width, dim.height);
	}

	@Override
	public OnMouse clone() {
		return new RectangleElement(this);
	}

	@Override
	public java.io.Serializable getData() {
		return "";
	} 


	@Override
	public void setData(java.io.Serializable s) {
		
	}
}
