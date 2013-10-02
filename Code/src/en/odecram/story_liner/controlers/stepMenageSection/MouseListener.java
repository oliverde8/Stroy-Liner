
package en.odecram.story_liner.controlers.stepMenageSection;

import en.odecram.story_liner.models.Step;
import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.sections.stepMenage.ScrollContent;
import java.awt.event.MouseEvent;

/**
 * Mouse Lisener for the FileSelector. TO enable users to select 
 * a File in the list of files
 * @author De Cramer Oliver
 */
public class MouseListener implements java.awt.event.MouseListener {
	
	/**
	 * The file selector
	 */
	ScrollContent sc;
	
	MainContent mContent;

	public MouseListener(ScrollContent a, MainContent mc) {
		sc = a;
		mContent = mc;
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		e.getSource().getClass().getName();
		
		int nelem = e.getPoint().x/(sc.HORIZANTAL_SPACE*2 + sc.ADD_BOX_SIZE + sc.getStep_preview_w());
		int x = e.getPoint().x%(sc.HORIZANTAL_SPACE*2 + sc.ADD_BOX_SIZE + sc.getStep_preview_w());
		
		if(nelem >= sc.getSteps().size())
			return;
			
		if(x > sc.HORIZANTAL_SPACE && x < sc.HORIZANTAL_SPACE + sc.getStep_preview_w()){
			sc.setSelectedStep(sc.getSteps().get(nelem));
	
		}else if( x > (2*sc.HORIZANTAL_SPACE) + sc.getStep_preview_w() && x < (2*sc.HORIZANTAL_SPACE) + sc.getStep_preview_w() + sc.ADD_BOX_SIZE){
			sc.addNewStep(nelem+1);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3){ 
			mouseClicked(e);
			mContent.getTools(sc.getSelectedStep()).show(sc,e.getX(), e.getY());                  
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
