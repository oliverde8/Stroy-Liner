
package fr.odecram.story_liner.contolers.mainContent;

import fr.odecram.story_liner.models.OnMouse;
import fr.odecram.story_liner.views.MainContent;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

/**
 *
 * @author De Cramer Oliver
 */
public class mouseListener implements MouseListener, MouseMotionListener, MouseWheelListener{
	
	/**
	 * The main Content view
	 */
	private MainContent mContent;
	
	/**
	 * All the elements that react to the mouse being on them
	 */
	private ArrayList<OnMouse> onMouses;
	
	/**
	 * The view that currently has the mouse on it
	 */
	private OnMouse viewWithMouse;
	
	private Point drag_lastMousePosition;
	private boolean drag_started;
	
	public mouseListener(MainContent mContent) {
		this.mContent = mContent;
		onMouses = new ArrayList<OnMouse>();
		drag_started = false;
	}

	public mouseListener() {
		
	}


	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		if(viewWithMouse != null  && viewWithMouse.mouse_isDragable()){
			drag_started = true;
			drag_lastMousePosition = new Point(e.getPoint());
		}
	}

	public void mouseReleased(MouseEvent e) {
		//well there is no checks to do.
		drag_started = false;
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mouseDragged(MouseEvent e) {
		if(drag_started){
			/*If the view couldn't be dragged due to some unknown reasons, we will not update the mouse positions for a nicer feeling using the draging.
			Actually the mouse will continue to move but the dragged objec wont. If you dont test it then even if the mouse isn't at the old position
			(on the view) the view will move wen you revery direction. Shortly the view would move with the mouse not on top of it.
			This way it will wait until the mouse in back on it to move. */
			if(viewWithMouse.mouse_drag(e.getPoint().x - drag_lastMousePosition.x, e.getPoint().y - drag_lastMousePosition.y)){
				drag_lastMousePosition.x = e.getPoint().x;
				drag_lastMousePosition.y = e.getPoint().y;
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
		
		//If we are dragging something ignore
		/*if(drag_started)
			return;*/
		
		//We will need this to see if we found a View with the mouse on it
		boolean found = false;
		
		//For all the view we need to check
		for (OnMouse m : onMouses){
			//Is it on this view
			if(m.mouse_onMe(e.getPoint().x, e.getPoint().y)){
				//Well yes so we found one
				found = true;
				
				//It the view is not the one found before
				if(viewWithMouse != m){
					//We let it now the mouse is on him.
					m.mouse_onMeConfirm();
					
					//We let know to the old view that had the mouse that it is no longer with him
					if(viewWithMouse != null)
						viewWithMouse.mouse_onMeOut();
					
					//We update the view with our new view
					viewWithMouse = m;
				}
				break;
			}
		}
		//Checking if we have found a view
		if(!found && viewWithMouse != null){
			//We havent. lets done say to the last view that had the mouse that it no longer has it. 
			viewWithMouse.mouse_onMeOut();
			//And we put it to null
			viewWithMouse = null;
		}
		
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		
	}

	public void add_OnMouse(OnMouse m){
		onMouses.add(m);
	}

}
