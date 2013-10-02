
package en.odecram.story_liner.views.models;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Interface for all elements that draws in Graphics.
 * 
 * @author De Cramer Oliver
 */
public interface Inter_PaintableItem {
	
	public void view_paint(Graphics g, int width, int height);
	
}
