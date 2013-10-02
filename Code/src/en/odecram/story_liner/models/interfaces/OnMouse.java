
package en.odecram.story_liner.models.interfaces;

/**
 * This allows Elements, Views to interact with the MainContent panels 
 * MouseListener.
 * 
 * @author De Cramer Oliver
 */
 public interface OnMouse extends Cloneable{
	
	
	/**
	 * Tells if the separator is on that particular view
	 * @param x
	 * @param y
	 * @return 
	 */
	public boolean mouse_onMe(int x, int y);
	
	/**
	 * MouseListener confirms that the mouse is on this element
	 */
	public void mouse_onMeConfirm(int x, int y);
	
	/**
	 * When the mouse moves out
	 */
	public void mouse_onMeOut();
	
	/**
	 * When the mouse is pressed on top of this element
	 */
	public void mouse_pressed();
	
	/**
	 * When the mouse is realesed afther being pressed on this element
	 */
	public void mouse_realesed();
	
	public void mouse_doubleClick();
	
	/*
	 * When the element is selected
	 */
	public void mouse_selected();
	
	/**
	 * When the element is no longer selected. 
	 */
	public void mouse_deselected();
	
	/**
	 * Let know the mouseListener that the element is dragable
	 * @return 
	 */
	public boolean mouse_isDragable();
	
	/**
	 * Let kniw the mouseListener that the element is selectable
	 * @return 
	 */
	public boolean mouse_isSelectable();
	
	/**
	 * When the mouse is dragged
	 * @param x the X deplacement of the mouse 
	 * @param y the Y deplacement of the mouse
	 * @return  Was the object updated? 
	 */
	public boolean mouse_drag(int dx, int dy);

	/**
	 * Allows the Element to be copied.
	 */
	public OnMouse clone();
	
}
