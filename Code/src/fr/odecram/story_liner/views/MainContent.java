
package fr.odecram.story_liner.views;

import fr.odecram.simpleButton.SimpleButton;
import fr.odecram.story_liner.contolers.mainContent.mouseListener;
import fr.odecram.story_liner.models.OnMouse;
import fr.odecram.story_liner.views.leftSection.VerticalSectionSeparator;
import en.odecram.story_liner.widgets.FileSelector.FileSelector;
import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author De Cramer Oliver
 */
public final class MainContent extends JPanel{
	
	
	public static final int DEF_SECTION_LEFT_WIDTH = 150;
	
	/*************************************************		
			** ALL The settings of the View **		
	**************************************************/
	private int section_left_width;
	
	/*************************************************		
			** The Drawing elements of The view **		
	**************************************************/	
	private FileSelector panel_fileSelector;
	
	private RightSection panel_rightSection;
	
	/*************************************************		
				** The Controllers	 **		
	**************************************************/	
	private mouseListener ml;

	
	public MainContent() {
		/* Assigning Default Settings at creation */
		section_left_width = DEF_SECTION_LEFT_WIDTH;
		
		/* Configuring the JPanel */
		setLayout(null);
		
		ml = new mouseListener(this);
		
		//The File Selector
		//panel_fileSelector = new FileSelector("tt", "Cancel", "Ok");
		//add(panel_fileSelector);
		
		panel_rightSection = new RightSection(this);
		add(panel_rightSection);
		
		reArrenge();
	}
	
	/*
	 * Will re arrange the content in the panel to fit to the new size of the Panel.
	 */
	public void reArrenge(){
		//panel_fileSelector.setBounds(0, 0, section_left_width, getHeight()-section_left_width);
		//canvas_rigth_Content.setBounds(section_left_width, 0, getWidth(), getHeight());
		
		panel_rightSection.setBounds(section_left_width, 0, getWidth()-section_left_width, getHeight());
		
		repaint();
	}
	
	public void mouse_addViewToMouse(OnMouse m){
		ml.add_OnMouse(m);
	}
}
