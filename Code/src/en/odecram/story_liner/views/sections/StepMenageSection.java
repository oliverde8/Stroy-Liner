
package en.odecram.story_liner.views.sections;

import en.odecram.story_liner.models.Step;
import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.dragables.VerticalSectionSeparator;
import en.odecram.story_liner.views.sections.stepMenage.ScrollContent;
import en.odecram.story_liner.views.sections.stepMenage.VerticalSeparator;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * The section in which we will edit steps of the Story.
 * @Todo ....
 * @author De Cramer Oliver
 */
public class StepMenageSection  extends JPanel{
	
	/**
	 * THe main content
	 */
	MainContent mc;
	
	/**
	 * The vertical separator panel
	 */
	VerticalSeparator vs;
	
	/**
	 * THe scroll panel
	 */
	JScrollPane	sp;
	ScrollContent sc;
	
	ArrayList<Step> steps;
	
	StepEditionSection ses;
			
	/**
	 * 
	 * @param mc THe main content panel
	 */
	public StepMenageSection(MainContent mc, StepEditionSection ses) {
		this.mc = mc;
		this.ses = ses;
		//We won't use a Layout manager
		setLayout(null);
		
		vs = new VerticalSeparator(mc);
		add(vs);
		
		steps = new ArrayList<Step>();
		
		sc = new ScrollContent(mc, steps, ses, this);
		sp = new JScrollPane(sc);
		add(sp);
	}
	
	public void reArrenge() {
		//Lets show the separator on the left
		vs.setBounds(0, 0, VerticalSectionSeparator.section_separatorWidth, MainContent.STEPMENAGESECTION_HEIGHT);
		//And the scroll pan just next to it
		sc.updateSize(steps.size());
		sp.setBounds(VerticalSectionSeparator.section_separatorWidth, 0, getWidth()-VerticalSectionSeparator.section_separatorWidth, getHeight());
	}


	public void addSteptoEnd(Step s){
		steps.add(s);
		
		if(steps.size() > 1){
			Step so = steps.get(steps.size()-2);
			so.setNext_step(s);
		}
	}
	
	public void addNewStep(int p){
		Step s;
		if(p > 0){
			s = new Step(steps.get(p-1));
			steps.get(p-1).setNext_step(s);
		}else{
			s = new Step(mc);
		}
		
		if(p < steps.size()){
			s.setNext_step(steps.get(p));
		}
		
		steps.add(p, s);
		setSelectedStep(s);
	}
	
	public void removeStep(Step s){
		
		if(steps.size() == 1){
			JInternalFrame frame = new JInternalFrame("");
			JOptionPane.showMessageDialog(frame, "This is the last Step. You can't delete all the steps.", TOOL_TIP_TEXT_KEY, WIDTH);
		}else{
			int i = steps.indexOf(s);
			System.out.println("Test "+i);
			if(i>0){
				if(steps.size() == i+1){
					setSelectedStep(steps.get(i-1));
				}else{
					steps.get(i-1).setNext_step(steps.get(i+1));
					setSelectedStep(steps.get(i+1));
				}
			}
			steps.remove(i);
			mc.reArrenge();
		}
	}
	
	public Step getSelectedStep() {
		return sc.getSelectedStep();
	}
	
	public void nexsStep(){
		sc.nextStep();
	}
	
	public void prevStep(){
		sc.prevStep();
	}
	
	public void setSelectedStep(Step s){
		sc.setSelectedStep(s);
	}

	public ArrayList<Step> getSteps() {
		return steps;
	}
	
	public void setSteps(ArrayList<Step> s){
		steps = s;
		sc.setSteps(s);
	}
}
