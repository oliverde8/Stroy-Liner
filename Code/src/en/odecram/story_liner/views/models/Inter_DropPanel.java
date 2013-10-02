
package en.odecram.story_liner.views.models;

import en.odecram.story_liner.models.interfaces.OnMouse;
import en.odecram.story_liner.views.dragables.models.Abs_StepElement;
import javax.swing.JPanel;

/**
 * Represents a Panel in which we can drop a Step element.
 * 
 * @author De Cramer Oliver
 */
public interface Inter_DropPanel{
	
	/**
	 * Is this Step element in the Panel. 
	 * @param se THe step element that might be in the Panel
	 * @return 
	 */
	boolean mouse_ElemIn(Abs_StepElement se);
	
	/**
	 * will the panel accept it this step element.
	 * @param se
	 * @return 
	 */
	boolean mouse_canGetElem(Abs_StepElement se);
	
	/**
	 * WHen the Step element is dropped in the panel. Was it accepted?
	 * @param se
	 * @return 
	 */
	boolean mouse_droppedElem(Abs_StepElement se);
}
