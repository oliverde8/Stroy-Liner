
package en.odecram.story_liner.views.dragables;

import en.odecram.story_liner.models.interfaces.OnMouse;
import en.odecram.story_liner.views.models.Inter_PaintableItem;
import en.odecram.story_liner.views.sections.StepEditionSection;
import en.odecram.story_liner.views.MainContent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Allows to separate Sections vertically.
 * Can move to change the sizes. It is placed between left and right section
 * 
 * @author De Cramer Oliver
 */
public class VerticalSectionSeparator implements Inter_PaintableItem, OnMouse{
	
	/**
	 * The width of the Section separator
	 */
	static final public int section_separatorWidth = 5;
	
	/**
	 * THe main content View
	 */
	private MainContent mContent;
	
	private boolean onMeAlready = false;
	
	/**
	 * 
	 * @param mContent The main content
	 */
	public VerticalSectionSeparator(MainContent mContent) {
		this.mContent = mContent;
	}

	
	/**
	 * 
	 * @param g The 2dGraphics in which we need to draw
	 * @param height The height of rhe Vertical Separation Bar
	 */
	public void view_paint(Graphics g, int width, int height){
		
		//We don(t need any 3D so we will make everything simplier by casting graphics to 2DGraphics
		Graphics2D g2d = (Graphics2D)g;
		
		//Lets draw the left
		GradientPaint gp = new GradientPaint(0, 0,  
													new Color(0xEEEEEE),
												section_separatorWidth , 0, 
													new Color(0xBBBBBB));
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, section_separatorWidth, height);
		
	}
	
	
	/**
	 * Is the positin on the separator
	 * 
	 * @param x 
	 * @param y
	 * @return 
	 */
	public boolean mouse_onMe(int x, int y){
		//99% of the time the leftSection is smaller then the right section. Taking that in account for optimisation
		return (x <mContent.getLeft_section_width()+section_separatorWidth && x > mContent.getLeft_section_width() );
	}

	@Override
	public void mouse_onMeConfirm(int x, int y) {
		//Changing to a resize cursor, because we can move the Vertial Bar
		if(onMeAlready)return;
		mContent.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		onMeAlready = true;
	}
	
	public void mouse_onMeOut() {
		//Butting back the default cursor.
		mContent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		onMeAlready = false;
	}

	@Override
	public boolean mouse_isDragable() {
		return true;
	}

	@Override
	public boolean mouse_drag(int x, int y) {
		//Calculating the new X position
		int nposX = mContent.getLeft_section_width() + x;
		
		//If the position don't make the left section width not to small then do it
		if(nposX > MainContent.LEFTSECTION_MINWIDTH && nposX < mContent.getHeight()/2){
			//Update the position
			mContent.setLeft_section_width(nposX);
			//Redraw everything
			mContent.reArrenge();
			//Let know the MouseListener that youy have move the view
			return true;
		}
		//Else let the MouseListener no that you didn't move the view
		return false;
	}

	@Override
	public void mouse_pressed() {
		
	}

	@Override
	public void mouse_realesed() {
		
	}

	@Override
	public OnMouse clone() {
		throw new UnsupportedOperationException("Not supported.");
	}
	
	@Override
	public boolean mouse_isSelectable() {
		return false;
	}
	
	@Override
	public void mouse_selected() {
		
	}

	@Override
	public void mouse_deselected() {

	}

	@Override
	public void mouse_doubleClick() {
		
	}
}
