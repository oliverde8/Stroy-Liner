
package en.odecram.story_liner.views.sections.left;

import en.odecram.story_liner.models.stepElement.Properties;
import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.dragables.ImageElement;
import en.odecram.story_liner.views.dragables.models.Abs_StepElement;
import en.odecram.story_liner.views.models.Abs_View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * The section in which we preview the image selected in the Fila Manager
 * 
 * @author De Cramer Oliver
 */
public class PreviewSection extends Abs_View{

	/**
	 * The main content panel
	 */
	private MainContent mContent;
	
	/**
	 * The Step element to be showned
	 */
	Abs_StepElement elem;
	
	/**
	 * The propertie of the step elements in the panel
	 */
	Properties elem_prop;
	
	/**
	 * 
	 * @param mContent The main content panel
	 */
	public PreviewSection(MainContent mContent) {
		this.mContent = mContent;
		//Creating a propertie we will use 
		elem_prop = new Properties(5, 5, getWidth(), getHeight(), Color.red);
	}
	
	public void paint(Graphics g){
		g.clearRect(0, 0, getWidth(), getHeight());
		//We don(t need any 3D so we will make everything simplier by casting graphics to 2DGraphics
		Graphics2D g2d = (Graphics2D)g;
		
		//Lets set new Size;
		elem_prop.setDim(new Dimension(getWidth()-50, getHeight()-50));
		
		//Lets draw The Image.
		if(elem!=null)
			elem.view_paint(g, 1);
	}
	
	/**
	 * Changes the previewed image
	 *  
	 * @param path The path to the image to preview
	 */
	public void setPreviewImage(String path){
		//We remove all Element from the mouseListener
		mContent.mouse_removeViewToMouse(elem);
		//Creating a new Image Element
		elem = new ImageElement(mContent, elem_prop, this, path);
		//We add the new element as multi panel dragable.
		mContent.mouse_addMultiDragToMouse(elem);
	}
}
