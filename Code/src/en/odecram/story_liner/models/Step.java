
package en.odecram.story_liner.models;

import en.odecram.story_liner.models.stepElement.Properties;
import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.dragables.models.Abs_StepElement;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Nassim
 */
public class Step {
	
	/**
	 * Allowing Step Elements to have unique id to differntiate them.
	 */
	protected static int unique_num = 0;
	public static int getUniqueNum(){ return unique_num++; }
	
	protected MainContent mContent;
	
	/**
	 * The next Step
	 */
	protected Step next_step = null;
	
	/**
	 * The new Elements that was introduces in this step.
	 */
	protected ArrayList<Abs_StepElement> elements;

	public Step(MainContent mContent) {
		this.mContent = mContent;
		elements = new ArrayList<Abs_StepElement>();
	}
	
	public Step(Step s){
		this.mContent = s.mContent;
		this.elements = (ArrayList<Abs_StepElement>) s.elements.clone();
	}
	
	public void view_paint(Graphics g, float scale){
		
		if(!elements.isEmpty()){
			for (Abs_StepElement e : elements){
				e.view_paint(g, scale);
			}	
		}
	}
	
	public void addElement(Abs_StepElement elem){
		elements.add(elem);
		if(next_step != null){
			next_step.addElement(elem);
		}
	}
	
	public void removeElement(Abs_StepElement elem){
		elements.remove(elem);
		mContent.mouse_removeViewToMouse(elem);
		
		if(next_step != null)
			next_step.removeElement(elem);
	}
	
	public void prepare(){
		
		if(!elements.isEmpty())
			for (Abs_StepElement e : elements){
				mContent.mouse_addViewToMouse(e);
			}
	}
	
	public void dprepare(){
		if(!elements.isEmpty())
			for (Abs_StepElement e : elements){
				mContent.mouse_removeViewToMouse(e);
			}
	}

	public Step getNext_step() {
		return next_step;
	}

	public void setNext_step(Step next_step) {
		this.next_step = next_step;
	}
	
	public ArrayList<Abs_StepElement> getElements(){
		return elements;
	}
	
	public boolean startbreakChain(Abs_StepElement e){
		if(elements.contains(e)){
			int ori = mContent.stepMenage_section.getSteps().indexOf(this);
			if(ori > 0 && mContent.stepMenage_section.getSteps().get(ori-1).getElements().contains(e)){
				Abs_StepElement e2 = (Abs_StepElement) e.clone();
				e2.setOld_id(e.getId());

				int i = elements.indexOf(e);
				elements.remove(e);
				mContent.mouse_removeViewToMouse(e);
				
				e2.mouse_deselected();
				elements.add(i, e2);
				mContent.mouse_addViewToMouse(e2);
				e2.getProp().setIsConnected(false);

				if(next_step != null)
						next_step.replaceElement(e, e2);
				return true;
			}
		}
		return false;
	}
	
	public void replaceElement(Abs_StepElement e, Abs_StepElement e2){
		if(elements.contains(e)){
			int i = elements.indexOf(e);
			elements.remove(e);
			mContent.mouse_removeViewToMouse(e);
			
			elements.add(i, e2);
			mContent.mouse_addViewToMouse(e2);
			
			if(next_step != null)
				next_step.replaceElement(e, e2);
		}
	}
	
	public boolean repairChainStart(Abs_StepElement e){
		if(elements.contains(e) && e.getOld_id() != -1){
			int oid = e.getId();
			int ori = mContent.stepMenage_section.getSteps().indexOf(this);
			if(ori > 0){
				Step prev = mContent.stepMenage_section.getSteps().get(ori-1);
				Abs_StepElement e2 = prev.findFromId(e.getOld_id());
				
				if(e2 != null){
					if(next_step != null){
						next_step.repairChain(oid, e2.getId());
					}
					replaceElement(e, e2);
					return true;
				}
			}
		}
		return false;
	}
	
	public void repairChain(int origin, int n){
		for (Abs_StepElement e : elements){
			if(e.getOld_id() == origin)
				e.setOld_id(n);
		}
		if(next_step != null)
			next_step.repairChain(origin,n);
	}
	
	public Abs_StepElement findFromId(int id){
		for (Abs_StepElement e : elements){
			if(e.getId() == id)
				return e;
		}
		return null;
	}
	
	public void pushForward(Abs_StepElement e){
		int i = elements.indexOf(e);
		if(i < elements.size()-1){
			int to = i+1;
			elements.remove(i);
			elements.add(to, e);
			if(next_step != null)
				next_step.pushForward(e);
		}
	}
	
	public void pushForwardFull(Abs_StepElement e){
		int i = elements.indexOf(e);
		if(i < elements.size()-1){
			int to = elements.size()-1;
			elements.remove(i);
			elements.add(to, e);
			if(next_step != null)
				next_step.pushForwardFull(e);
		}
	}
	
	public void pushBack(Abs_StepElement e){
		int i = elements.indexOf(e);
		if(i > 0){
			int to = i-1;
			elements.remove(i);
			elements.add(to, e);
			if(next_step != null)
				next_step.pushBack(e);
		}
	}
	
}
