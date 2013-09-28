
package fr.odecram.story_liner.views.leftSection;

import fr.odecram.story_liner.views.MainContent;
import fr.odecram.story_liner.models.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author De Cramer Oliver
 */
public class VerticalSectionSeparator implements View, OnMouse{
	
	static final public int section_separatorWidth = 5;
	
	int posX;
	
	private MainContent mContent;

	public VerticalSectionSeparator(MainContent mContent) {
		this.mContent = mContent;
		
		posX = MainContent.DEF_SECTION_LEFT_WIDTH + 10;
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
		GradientPaint gp = new GradientPaint(posX, 0,  new Color(0xEEEEEE), posX + section_separatorWidth , 0, new Color(0xBBBBBB));
		g2d.setPaint(gp);
		g2d.fillRect(posX, 0, section_separatorWidth, height);
		
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
		return (x <posX+section_separatorWidth && x > posX );
	}

	@Override
	public void mouse_onMeConfirm() {
		mContent.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
	}
	
	public void mouse_onMeOut() {
		mContent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public boolean mouse_isDragable() {
		return true;
	}

	@Override
	public boolean mouse_drag(int x, int y) {
		//Calculating the new X position
		int nposX = posX + x;
		
		//If the position don't make the left section width not to small then do it
		if(nposX > MainContent.DEF_SECTION_LEFT_WIDTH){
			//Update the position
			posX = nposX;
			//Redraw everything
			mContent.repaint();
			//Let know the MouseListener that youy have move the view
			return true;
		}
		//Else let the MouseListener no that you didn't move the view
		return false;
	}

	public int getPosX() {
		return posX;
	}
}
