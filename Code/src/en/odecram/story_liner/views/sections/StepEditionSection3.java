
package en.odecram.story_liner.views.sections;

import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.dragables.VerticalSectionSeparator;
import en.odecram.story_liner.views.models.Abs_View;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author De Cramer Oliver
 */
public class StepEditionSection3 extends JPanel{

	/**
	 * The main Content Panel
	 */
	MainContent mContent;
	
	
	/**
	 * 
	 */
	StepEditionSection ses;

	StepEditionSection2 ses2;
	
	StepEditionSection4 ses4;
	
	public StepEditionSection3(MainContent mContent, StepEditionSection ses) {
		this.mContent = mContent;
		this.ses = ses;
		setLayout(null);
		
		ses2 = new StepEditionSection2(mContent, ses);
		ses4 = new StepEditionSection4(mContent);
		add(ses2);
		add(ses4);
	}
	
	public void reArrenge() {
		ses2.setBounds(VerticalSectionSeparator.section_separatorWidth, 0, getWidth()-VerticalSectionSeparator.section_separatorWidth, getHeight());
		ses2.reArrenge();
		
		ses4.setBounds(0, 0, VerticalSectionSeparator.section_separatorWidth, getHeight());
	}
	
}
