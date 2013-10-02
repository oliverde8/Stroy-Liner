
package en.odecram.story_liner.views.sections;

import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.dragables.VerticalSectionSeparator;
import en.odecram.story_liner.views.models.Abs_View;
import java.awt.Graphics;

/**
 *
 * @author De Cramer Oliver
 */
public class StepEditionSection2 extends Abs_View{

	/**
	 * The main Content Panel
	 */
	MainContent mContent;
	
	/**
	 * 
	 */
	StepEditionSection ses;

	public StepEditionSection2(MainContent mContent, StepEditionSection ses) {
		this.mContent = mContent;
		this.ses = ses;
		this.add(ses);
		this.setLayout(null);
	}
	
	
	public void reArrenge() {

		
		int width = getWidth();
		int height = (int)(((float)width/800)*600);

		if(height > getHeight()){
			height = getHeight();
			width =  (int)(((float)height/600)*800);
		}
		
		int x = ((getWidth()) - width)/2;
		int y = ((getHeight()) - height)/2;
		ses.setScale(((float)height/600));
		ses.setBounds(x, y, width, height);
	}
}
