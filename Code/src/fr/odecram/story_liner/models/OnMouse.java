
package fr.odecram.story_liner.models;

/**
 *
 * @author De Cramer Oliver
 */
 public interface OnMouse {
	
	
	/**
	 * Tells if the separator is on that particular view
	 * @param x
	 * @param y
	 * @return 
	 */
	public boolean mouse_onMe(int x, int y);
	
	public void mouse_onMeConfirm();
	
	public void mouse_onMeOut();
	
	public boolean mouse_isDragable();
	
	public boolean mouse_drag(int x, int y);
	
}
