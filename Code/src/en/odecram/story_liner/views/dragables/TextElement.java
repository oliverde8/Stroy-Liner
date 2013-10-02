
package en.odecram.story_liner.views.dragables;

import en.odecram.story_liner.models.interfaces.OnMouse;
import en.odecram.story_liner.models.stepElement.Properties;
import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.dragables.models.Abs_StepElement;
import en.odecram.story_liner.views.models.Abs_View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 * This step ELement draws a Text. 
 * @author De Cramer Oliver
 */
public class TextElement extends Abs_StepElement{

	/**
	 * The text to be drawed
	 */
	String text;
	
	/**
	 * Has the Text element drawed once 
	 */
	public boolean firstGo=true;
	
	public TextElement(Abs_StepElement origin) {
		super(origin);
		text = ((TextElement)origin).text;
		firstGo = ((TextElement)origin).firstGo;
	}
	/**
	 * 
	 * @param mContent The main content Panel
	 * @param prop THe Element properties
	 * @param parent The parent view of the Element
	 * @param text  The Text to be shown
	 */
	public TextElement(MainContent mContent, Properties prop, Abs_View parent, String text) {
		super(mContent, prop, parent);
		this.text = text;
	}
	
	@Override
	public void paint(Graphics g, Point pos, Dimension dim, float scale) {
		//Let recover default font.
		Font f = g.getFont();
		//We will find out the thext dimensions
		FontMetrics metrics;
		
		//On esseye de calculer la taille du text
		metrics = g.getFontMetrics(f);
		Rectangle2D r2d = metrics.getStringBounds(text, g);
			
		if(firstGo && scale == parent.getScale()){
			//Suming all the Width
			int nw, nh;
			if(r2d.getWidth() > MIN_SIZE)nw = (int)r2d.getWidth(); else nw = MIN_SIZE;
			if(r2d.getHeight() > MIN_SIZE)nh=(int)r2d.getHeight(); else nh = MIN_SIZE;
			
			prop.setDim(new Dimension(nw, nh));
			dim = prop.getDim();
			firstGo = false;
			el_scale = -1;
			parent.repaint();
			return;
		}
		
		//We shall find out the true size of the text with default text
		double sh, sw, s;
		
		//We calculate the Height and with Ratios.
		sh = (prop.getH() / r2d.getHeight())*scale; 
		sw = (prop.getW() / r2d.getWidth())*scale; 
		
		//We select the smallest one to be sure it fits
		if(sh < sw)s = sh;
		else s = sw;
		
		//We apply a new text size taking in account the ratios above.
		g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), (int)(15*s)));
		metrics = g.getFontMetrics(g.getFont());
		
		//Finding out the text height
		int h = metrics.getHeight();
		
		Color cdef = g.getColor();
		
		g.setColor(prop.getBgColor());
		g.fillRect(pos.x, pos.y, (int)(prop.getW()*scale), (int)(prop.getH()*scale));
		
		g.setColor(prop.getLineColor());

		
		//Drawing the text and centering on the Y position
		g.drawString(text, pos.x, pos.y+(h/2));
		
		g.setColor(cdef);
		
		
		//Reapplying default Font.
		g.setFont(f);
	}

	@Override
	public OnMouse clone() {
		return new TextElement(this);
	}

	@Override
	public void mouse_deselected() {
		if(mContent.tadd.isActive())
			mContent.tadd.confirm();
		super.mouse_deselected();
	}

	
	
	@Override
	public void mouse_doubleClick() {
		mContent.tadd.start(this);
	}

	@Override
	public java.io.Serializable getData() {
		return text;
	}

	@Override
	public void setData(java.io.Serializable s) {
		text = (String) s;
	}

}
