package en.odecram.story_liner.controlers.mainContent;

import en.odecram.story_liner.controlers.Actions;
import en.odecram.story_liner.models.interfaces.OnMouse;
import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.dragables.models.Abs_StepElement;
import en.odecram.story_liner.views.sections.StepEditionSection;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

/**
 * The mouse listener that allows, elements to be selected, dragged and dropped
 * 
 * @author De Cramer Oliver
 */
public class mouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {

	/**
	 * The main Content view
	 */
	private MainContent mContent;
	
	/**
	 * The view in which the steps are edited. 90% od the mouseListeners work
	 */
	private StepEditionSection seSection;
	
	/**
	 * All the elements that react to the mouse being on them
	 * or the mouse clicking or yet draging them
	 */
	private ArrayList<OnMouse> onePaneMouseAffected;
	
	/**
	 * All the elements that needs to be able to change JPanel's during drag and drop
	 */
	private ArrayList<OnMouse> multiPaneMouseAffected;
	
	/**
	 * The view that currently has the mouse on it
	 */
	private OnMouse viewWithMouse;
	
	/**
	 * The view that has been selected
	 */
	private OnMouse viewSelected;
	
	/**
	 * Is this view current view on which the mouse on is multiDrag?
	 */
	private boolean multiDrag = false;
	
	/**
	 * The last known position of the mouse during drang and drop.
	 * Will be used to find out the amount of displatment of the mouse
	 */
	private Point drag_lastMousePosition;
	private Point create_startPosition;
	
	/**
	 * Has the drag started? 
	 */
	private boolean drag_started;

	public mouseListener(MainContent mContent, StepEditionSection se) {
		this.mContent = mContent;
		this.seSection = se;
		onePaneMouseAffected = new ArrayList<OnMouse>();
		multiPaneMouseAffected = new ArrayList<OnMouse>();
		drag_started = false;
	}
	
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2 && viewWithMouse != null)
			viewWithMouse.mouse_doubleClick();
			
	}

	public void mousePressed(MouseEvent e) {
		
		//If the mouse is on something. 
		if(mContent.action.getMode() != Actions.MODE_SELECTION){
			seSection.getStep().addElement(mContent.action.getNewAbs());
			
			
			
			int x = (int) ((e.getLocationOnScreen().x-seSection.getLocationOnScreen().x)/seSection.getScale());
			int y = (int) ((e.getLocationOnScreen().y-seSection.getLocationOnScreen().y)/seSection.getScale());
			
			Point n = new Point(x, y);
			drag_lastMousePosition = new Point(e.getPoint());
			create_startPosition = new Point(e.getLocationOnScreen());
			mContent.action.getNewAbs().getProp().setPos(n);
		
		}else if (viewWithMouse != null){
			//Let know the view the mouse pressed.
			viewWithMouse.mouse_pressed();
			//If it is a selectable view and not already selected then let's select it
			if (viewWithMouse.mouse_isSelectable() && viewSelected != viewWithMouse) {
				//If there was already a selected view we let it know about the change
				if(viewSelected!=null)viewSelected.mouse_deselected();
				//Let us select it
				viewSelected = viewWithMouse;
				//Let let it know that it was selected
				viewSelected.mouse_selected();
			}
			//If the view is dragable we will start drag process.
			if (viewWithMouse.mouse_isDragable()) {
				drag_started = true;
				//Let us save current mouse position
				drag_lastMousePosition = new Point(e.getPoint());
				//If it is a multidrag we need to copy the element
				if (multiDrag) {
					//Creating the copy
					OnMouse copy = viewWithMouse.clone();
					//Let know the orginal the mouse is not on him and that we don't press it anymore
					viewWithMouse.mouse_onMeOut();
					viewWithMouse.mouse_realesed();
					//We will be draging the new element
					viewWithMouse = copy;
					//We ask the main Panel to show this Element aboce all other
					mContent.startMultiDrag((Abs_StepElement) viewWithMouse);
				}
			}
			//If we click on nothing then deselect
		}else if (viewSelected != null) {
			//We let it know that we deselected him.
			viewSelected.mouse_deselected();
			viewSelected = null;
		}
	}

	public void mouseReleased(MouseEvent e) {
		//If the mouse was on somethin before
		if (viewWithMouse != null) {
			//We let it know that we realesed
			viewWithMouse.mouse_realesed();
			
			//If the mouse is not still on it
			if(!viewWithMouse.mouse_onMe(e.getX(), e.getY())){
				//We let it know that the mouse has come out
				viewWithMouse.mouse_onMeOut();
				viewWithMouse = null;
			}
		}
		//Any way drag stops
		drag_started = false;
		//If there was multi drag
		if (multiDrag) {
			//We send the dragged element in the desired Section.
			seSection.addElement((Abs_StepElement) viewWithMouse);
			//We end the multi draging process going on the Main Content
			mContent.endMuliDrag();
			multiDrag = false;
		}
                
		if(e.getButton() == MouseEvent.BUTTON3){                     
			mContent.getTools(viewWithMouse).show(mContent,e.getX(),e.getY());                  
		}
		
		if(mContent.action.getMode() != Actions.MODE_SELECTION){
			add_OnePaneMouse(mContent.action.getNewAbs());
			mContent.action.getNewAbs().setIsSelectable(true);
			mContent.action.modeSelect();
			mContent.reArrenge();
		}

	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		if (drag_started) {
			/*If the view couldn't be dragged due to some unknown reasons, we will not update the mouse positions for a nicer feeling using the draging.
			 Actually the mouse will continue to move but the dragged objec wont. If you dont test it then even if the mouse isn't at the old position
			 (on the view) the view will move wen you revery direction. Shortly the view would move with the mouse not on top of it.
			 This way it will wait until the mouse in back on it to move. */
			
			float scale = 1;
			if(viewWithMouse instanceof Abs_StepElement)
				scale = ((Abs_StepElement)viewWithMouse).getParent().getScale();
			
			if (viewWithMouse.mouse_drag((int)((e.getPoint().x - drag_lastMousePosition.x)/scale)
					, (int)((e.getPoint().y - drag_lastMousePosition.y)/scale))) {
				drag_lastMousePosition.x = e.getPoint().x;
				drag_lastMousePosition.y = e.getPoint().y;
			}
		}else if(mContent.action.getMode() != Actions.MODE_SELECTION && create_startPosition != null){
			
			
			int w = (int) ((e.getPoint().x - drag_lastMousePosition.x)/seSection.getScale());
			int h = (int) ((e.getPoint().y - drag_lastMousePosition.y)/seSection.getScale());
			
			int x = (int) ((create_startPosition.x-seSection.getLocationOnScreen().x)/seSection.getScale());
			int y = (int) ((create_startPosition.y-seSection.getLocationOnScreen().y)/seSection.getScale());
			
			if(w < 0 && !mContent.action.getNewAbs().sizeNegatif()){
				x += w;
				w *= -1;
			}
			
			if(h < 0 && !mContent.action.getNewAbs().sizeNegatif()){
				y += h;
				h *= -1;
			}
			
			mContent.action.getNewAbs().getProp().setDim(new Dimension(w,h));
			mContent.action.getNewAbs().getProp().setPos(new Point(x, y));
			
			seSection.validate();
			seSection.repaint();
		}
	}

	public void mouseMoved(MouseEvent e) {
					
		//We will need this to see if we found a View with the mouse on it
		boolean found = false;
		
		if(mContent.action.getMode() != Actions.MODE_SELECTION){
			return;
		}
		
		//For all the view we need to check
		for (int i = onePaneMouseAffected.size()-1; i>=0; i--){
			OnMouse m = onePaneMouseAffected.get(i);
			//Is it on this view
			if (m.mouse_onMe(e.getPoint().x, e.getPoint().y)) {
				//Well yes so we found one
				found = true;
				//It the view is not the one found before
				if (viewWithMouse != m) {
					//We let know to the old view that had the mouse that it is no longer with him
					if (viewWithMouse != null) {
						viewWithMouse.mouse_onMeOut();
					}
					//We let it now the mouse is on him.
					m.mouse_onMeConfirm(e.getPoint().x, e.getPoint().y);
					
					//We update the view with our new view
					viewWithMouse = m;

					//We check if it is a multi drag
					multiDrag = multiPaneMouseAffected.contains(m);
				}else{
					m.mouse_onMeConfirm(e.getPoint().x, e.getPoint().y);
				}
				break;
			}
		}
		//Checking if we have found a view
		if (!found && viewWithMouse != null) {
			//We havent. lets done say to the last view that had the mouse that it no longer has it. 
			viewWithMouse.mouse_onMeOut();
			//And we put it to null
			viewWithMouse = null;
		}

	}

	public void mouseWheelMoved(MouseWheelEvent e) {
	}
	
	/**
	 * Add a View that is affected by mouse clikc, drags ....
	 * @param m 
	 */
	public void add_OnePaneMouse(OnMouse m) {
		if (m != null) {
			onePaneMouseAffected.add(m);
		}
	}
	
	/**
	 * Will allow a view to be dragged from one panel to another.
	 * This views will be automatically added to the onPaneMouse
	 * @param m 
	 */
	public void add_multiPaneMouse(OnMouse m) {
		if (m != null) {
			multiPaneMouseAffected.add(m);
			onePaneMouseAffected.add(m);
		}
	}
	
	/**
	 * Will remove the view from the onePaneMouse and from the multiple
	 * panel drag process.
	 */
	public void remove_PaneMouse(OnMouse m) {
		if (m != null) {
			multiPaneMouseAffected.remove(m);
			onePaneMouseAffected.remove(m);
			if(viewSelected == m){
				forceLoseSelect();
			}
			if(viewWithMouse == m){
				m.mouse_onMeOut();
				viewWithMouse = null;
			}
		}
	}
	
	/**
	 * Will force the lost of select
	 */
	public void forceLoseSelect(){
		if(viewSelected != null){
			viewSelected.mouse_deselected();
			viewSelected = null;
		}
	}

	public OnMouse getViewSelected() {
		return viewSelected;
	}
}
