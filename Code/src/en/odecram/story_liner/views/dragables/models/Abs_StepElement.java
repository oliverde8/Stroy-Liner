package en.odecram.story_liner.views.dragables.models;

import en.odecram.story_liner.models.Step;
import en.odecram.story_liner.models.interfaces.OnMouse;
import en.odecram.story_liner.models.stepElement.Properties;
import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.models.Abs_View;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Representes a Element of the a Step. Elements from different steps
 * maybe connected throught the stepElement/properties
 * 
 * @author De Cramer Oliver
 */
public abstract class Abs_StepElement implements OnMouse {
	
	/**
	 * Drag modes
	 */
	public static final int DRAG_MOVE			= 1;	//Moves the object 
	public static final int DRAG_TOP_LEFT		= 2;	//Resize the object from top LEFT
	public static final int DRAG_TOP_RIGHT		= 3;	//Resize the object from top RIGHT
	public static final int DRAG_BOTTOM_RIGHT	= 4;	//Resize the object from bottom RIGHT
	public static final int DRAG_BOTTOM_LEFT	= 5;	//Resize the object from botom LEFT
	public static final int DRAG_LEFT			= 6;	//Resize the object from LEFT
	public static final int DRAG_RIGHT			= 7;	//Resize the object from RIGHT
	public static final int DRAG_TOP			= 8;	//Resize the object from TOP
	public static final int DRAG_BOTTOM			= 9;	//Resize the object from BOTTOM
	
	/**
	 * The minimum size of the Element box
	 */
	public static final int MIN_SIZE	= 20;
	
	/**
	 * 
	 */
	public static final int MOUSE_ACTION_SIZE = 10;
			
	/**
	 * The main Contentn Panel
	 */
	protected MainContent mContent;
	
	/**
	 * THe JPane in whih is shown the Step Element
	 */
	protected Abs_View parent;
	
	/**
	 * This step Elements ID,
	 */
	protected int id;
	
	protected int old_id = -1;
	
	/**
	 * The properties of the StepElement. Multiple Step elements may have same 
	 * properties. 
	 */
	protected Properties prop;
	protected Properties oprop;
	
	/**
	 * Is this element selected with the mouse? 
	 */
	private boolean mouseSelected = false;
	
	/**
	 * Can this element be selected. 
	 * All elements are first shown in the Preview Panel where they are not Selectable
	 */
	private boolean isSelectable = false;
	
	/**
	 * The minimum amount of the image that needs to be visible.
	 */
	private int minVisible = 50;
	
	
	/**
	 * The last scale with who the Element was drawed.
	 */
	protected float el_scale = -1;
	
	/**
	 * The drag mode on which is actually the Element.
	 */
	private int drag_mode = 0;
	
	private int last_drag_mode = -1;
	
	/**
	 * Copyig a StepElement. Will create a clone of all values ! 
	 * @param origin 
	 */
	public Abs_StepElement(Abs_StepElement origin) {
		this();
		mContent = origin.mContent;
		parent = origin.parent;
		prop = (Properties) origin.prop.clone();
		mouseSelected = origin.mouseSelected;
		minVisible = origin.minVisible;
		isSelectable = origin.isSelectable;
		this.id = Step.getUniqueNum();
	}

	/**
	 * 
	 * @param mContent The main content Panel
	 * @param prop The properties of this Step Element
	 * @param parent The view in which the Step element is shown
	 */
	public Abs_StepElement(MainContent mContent, Properties prop, Abs_View parent) {
		this();
		this.mContent = mContent;
		this.prop = prop;
		this.parent = parent;
		this.id = Step.getUniqueNum();
	}
	
	public Abs_StepElement(){
		this.id = en.odecram.story_liner.models.Step.getUniqueNum();
	}
	
	public void view_paint(Graphics g, float scale) {
		
		//Workout the dimentsion and position of the element takin in account the scale factor.
		Dimension el_dim = new Dimension((int) (prop.getDim().width * scale), (int) (prop.getDim().height * scale));
		Point el_pos = new Point((int) (prop.getPos().x * scale) + 10, (int) (prop.getPos().y * scale) + 10);

		//We will use Graphics 2D
		Graphics2D g2d = (Graphics2D) g;

		//If the element is selected
		if (mouseSelected) {
			g2d.drawRect(el_pos.x-2, el_pos.y-2, el_dim.width+4, el_dim.height+4);
		}
		
		//We change default color to prevent bugs
		g2d.setPaint(Color.GRAY);
		
		//Drawing the Step Element now.
		paint(g, el_pos, el_dim, scale);
		
		//If the element is selected lets show corners
		if (mouseSelected) {
			g2d.setPaint(Color.BLUE);
			int d2 = MOUSE_ACTION_SIZE/2;
			
			//TOp Left Corner
			g2d.fillRect(el_pos.x-d2, el_pos.y-d2, MOUSE_ACTION_SIZE, MOUSE_ACTION_SIZE);
			
			//Bottom Left Corner
			g2d.fillRect(el_pos.x-d2, el_pos.y+el_dim.height-d2, MOUSE_ACTION_SIZE, MOUSE_ACTION_SIZE);
			
			//Bottom Right Corner
			g2d.fillRect(el_pos.x-d2+el_dim.width, el_pos.y+el_dim.height-d2, MOUSE_ACTION_SIZE, MOUSE_ACTION_SIZE);
			
			//Top Right Corner, 
			g2d.fillRect(el_pos.x-d2+el_dim.width, el_pos.y-d2, MOUSE_ACTION_SIZE, MOUSE_ACTION_SIZE);
		}
	}

	/**
	 * Drawing the Element. Will be called by view_paint.
	 * @param g The graphic in which we need to draw it
	 * @param pos The position at which we will draw it
	 * @param dim The size of the element
	 * @param scale The scale factor. Dimension and position are already updated to be upto scale
	 */
	public abstract void paint(Graphics g, Point pos, Dimension dim, float scale);

	@Override
	public boolean mouse_onMe(int x, int y) {
		//parents location
		Point pp = new Point(parent.getLocationOnScreen().x - mContent.getLocationOnScreen().x, parent.getLocationOnScreen().y - mContent.getLocationOnScreen().y);
		//Creating a point
		Point mp = new Point(x, y);
		
		//Workout the dimentsion and position of the element takin in account the scale factor.
		Dimension el_dim = new Dimension((int) (prop.getDim().width *  parent.getScale()), (int) (prop.getDim().height *  parent.getScale()));
		Point el_pos = new Point((int) (prop.getPos().x * parent.getScale()) + 10, (int) (prop.getPos().y *  parent.getScale()) + 10);
		
		correctDimPosition(el_pos, el_dim);
		
		Point el_pos2 = new Point(el_pos.x + pp.x ,el_pos.y + pp.y);
		
		int d2 = MOUSE_ACTION_SIZE/2;
		Rectangle r;
		if(isSelectable)
			r = new Rectangle(el_pos2.x-d2, el_pos2.y-d2, el_dim.width+MOUSE_ACTION_SIZE, el_dim.height+MOUSE_ACTION_SIZE);
		else
			r = new Rectangle(el_pos2.x, el_pos2.y, el_dim.width, el_dim.height);
		
		return r.contains(mp);
	}

	@Override
	public void mouse_onMeConfirm(int x, int y) {

		//parents location
		Point pp = new Point(parent.getLocationOnScreen().x - mContent.getLocationOnScreen().x, parent.getLocationOnScreen().y - mContent.getLocationOnScreen().y);
		//Creating a point
		Point mp = new Point(x, y);
		
		//Workout the dimentsion and position of the element takin in account the scale factor.
		Dimension el_dim = new Dimension((int) (prop.getDim().width *  parent.getScale()), (int) (prop.getDim().height *  parent.getScale()));
		Point el_pos = new Point((int) (prop.getPos().x * parent.getScale()) + 10, (int) (prop.getPos().y *  parent.getScale()) + 10);
		
		correctDimPosition(el_pos, el_dim);
		
		if(el_pos == null)return;
		el_pos = new Point(el_pos.x + pp.x ,el_pos.y + pp.y);
		Rectangle r;
		
		int d2 = MOUSE_ACTION_SIZE/2;
		
		if(isSelectable)
			r = new Rectangle(el_pos.x+d2, el_pos.y+d2, el_dim.width-MOUSE_ACTION_SIZE, el_dim.height-MOUSE_ACTION_SIZE);
		else
			r = new Rectangle(el_pos.x, el_pos.y, el_dim.width, el_dim.height);
		if(r.contains(mp)){
			drag_mode = DRAG_MOVE;
			mContent.setCursor(new Cursor(Cursor.HAND_CURSOR));
			return;
		}else if(!isSelectable)return;
		
		//The Top Left move Zone
		r = new Rectangle(el_pos.x-d2, el_pos.y-d2, MOUSE_ACTION_SIZE, MOUSE_ACTION_SIZE);
		if(r.contains(mp)){
			mContent.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
			drag_mode = DRAG_TOP_LEFT;
			return;
		}
		//THe Top Right move zone
		r = new Rectangle(el_pos.x-d2+el_dim.width, el_pos.y-d2, MOUSE_ACTION_SIZE, MOUSE_ACTION_SIZE);
		if(r.contains(mp)){
			drag_mode = DRAG_TOP_RIGHT;
			mContent.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
			return;
		}
		//The Bottom Right Move zone
		r = new Rectangle(el_pos.x-d2+el_dim.width, el_pos.y+el_dim.height-d2, MOUSE_ACTION_SIZE, MOUSE_ACTION_SIZE);
		if(r.contains(mp)){
			drag_mode = DRAG_BOTTOM_RIGHT;
			mContent.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
			return;
		}
		//The Bottom Left Move zone
		r = new Rectangle(el_pos.x-d2, el_pos.y+el_dim.height-d2, MOUSE_ACTION_SIZE, MOUSE_ACTION_SIZE);
		if(r.contains(mp)){
			mContent.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
			drag_mode = DRAG_BOTTOM_LEFT;
			return;
		}
		
		//The Right Move zone
		r = new Rectangle(el_pos.x+el_dim.width-d2, el_pos.y+d2, MOUSE_ACTION_SIZE, el_dim.height-(2*d2));
		if(r.contains(mp)){
			mContent.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
			drag_mode = DRAG_RIGHT;
			return;
		}
		
		//The left Move zone
		r = new Rectangle(el_pos.x-d2, el_pos.y+d2, MOUSE_ACTION_SIZE, el_dim.height-(2*d2));
		if(r.contains(mp)){
			mContent.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
			drag_mode = DRAG_LEFT;
			return;
		}
		
		//The Bottom Move Zone
		r = new Rectangle(el_pos.x+d2, el_pos.y+el_dim.height-d2, el_dim.width-(2*d2), MOUSE_ACTION_SIZE);
		if(r.contains(mp)){
			mContent.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
			drag_mode = DRAG_BOTTOM;
			return;
		}
		
		//The TOP Move Zone
		r = new Rectangle(el_pos.x+d2, el_pos.y-d2, el_dim.width-(2*d2), MOUSE_ACTION_SIZE);
		if(r.contains(mp)){
			mContent.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
			drag_mode = DRAG_TOP;
			return;
		}
	}
	
	private void correctDimPosition(Point p, Dimension d){
		if(d.width < 0){
			p.x += d.width;
			d.width *= -1;
		}
		
		if(d.height <0){
			p.y += d.height;
			d.height *= -1;
		}
	}

	@Override
	public void mouse_onMeOut() {
		mContent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public boolean mouse_isDragable() {
		return true;
	}
	
	@Override
	public boolean mouse_drag(int x, int y) {
		switch(drag_mode){
			case DRAG_TOP_LEFT : 
				return resizeTopLeftElement(x, y);
			case DRAG_TOP_RIGHT	: 
				return resizeTopRightElement(x, y);
			case DRAG_BOTTOM_RIGHT : 
				return resizeBottomRightElement(x, y);
			case DRAG_BOTTOM_LEFT : 
				return resizeBottomLeftElement(x, y);
			case DRAG_LEFT : 
				return resizeLeftElement(x, y);
			case DRAG_RIGHT	: 
				return resizeRightElement(x, y);
			case DRAG_TOP : 
				return resizeTopElement(x, y);
			case DRAG_BOTTOM :
				return resizeBottom(x, y);
			default : 
				return moveElement(x, y);
		}
	}
	
	/**
	 * @return The parent view of the element
	 */
	public Abs_View getParent() {
		return parent;
	}
	
	/**
	 * 
	 * @return The propreties of the Element
	 */
	public Properties getProp() {
		return prop;
	}

	public boolean isConnected(){
		if(prop.isConnected())
			return true;
		
		Step ss = mContent.stepMenage_section.getSelectedStep();
		int i = mContent.stepMenage_section.getSteps().indexOf(ss);
		
		if(i>0 && mContent.stepMenage_section.getSteps().get(i-1).getElements().contains(this)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @return The id of the element
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOld_id() {
		return old_id;
	}

	public void setOld_id(int old_id) {
		this.old_id = old_id;
	}
	
	
	

	/**
	 * Changes the propreties of the Element
	 * @param prop THe new propreties
	 */
	public void setProp(Properties prop) {
		this.prop = prop;
	}
	
	/**
	 * Affets a new parent to the element
	 * @param p THe new parent view
	 */
	public void setParent(Abs_View p) {
		//We recover the old panels position;
		Point pos1 = new Point(parent.getLocationOnScreen().x - mContent.getLocationOnScreen().x, parent.getLocationOnScreen().y - mContent.getLocationOnScreen().y);
		//The new parents position
		Point pos2 = new Point(p.getLocationOnScreen().x - mContent.getLocationOnScreen().x, p.getLocationOnScreen().y - mContent.getLocationOnScreen().y);
		//We find out the position, to prevent position glitches
		prop.setPos(new Point( (int)((pos1.x + prop.getX() - pos2.x)/p.getScale()), (int)((pos1.y + prop.getY() - pos2.y)/p.getScale()) ));
		//We change the parent
		this.parent = p;
		el_scale = -1;
	}

	/**
	 * 
	 * @param isSelectable 
	 */
	public void setIsSelectable(boolean isSelectable) {
		this.isSelectable = isSelectable;
	}

	@Override
	public void mouse_doubleClick(){
		
	}
	
	@Override
	public void mouse_pressed() {
		oprop = (Properties) prop.clone();
	}

	@Override
	public void mouse_realesed() {
		oprop = null;
	}

	@Override
	public boolean mouse_isSelectable() {
		return isSelectable;
	}

	@Override
	public void mouse_selected() {
		//We are selected ooo
		mouseSelected = true;
		//We update the view
		mContent.reArrenge();
	}

	public void mouse_deselected() {
		mouseSelected = false;
		mContent.reArrenge();
	}

	public abstract OnMouse clone();
	
	private boolean moveElement(int x, int y){
		//We get it's parents location.
		Point p = new Point(parent.getLocationOnScreen().x - mContent.getLocationOnScreen().x, parent.getLocationOnScreen().y - mContent.getLocationOnScreen().y);
		//And it's parents size
		Dimension d = parent.getSize();
		
		//Lets find out the new location of the Elements
		if(oprop == null)oprop = (Properties) prop.clone();
		int nx = oprop.getX() + x;
		int ny = oprop.getY() + y;
		
		//We create a rectangle to represents it's parents size.
		Rectangle r = new Rectangle(new Point(0, 0), d);
		
		/*if ( !r.contains(nx+minVisible, 1)  && !r.contains(nx+el_dim.width-minVisible,1))
			nx = prop.getX();
		
		if( !r.contains(1, ny + minVisible) && !r.contains(1, ny - minVisible - el_dim.width))
			ny = prop.getY();*/

		
		prop.setPos(new Point(nx, ny));
		
		parent.validate();
		parent.repaint();

		return false;
	}

	private boolean resizeTopLeftElement(int x, int y) {
		if(oprop == null)oprop = (Properties) prop.clone();
		
		if((y > 0 && y < x) || (y<=0 && y > x))x = y;
		
		int nw = oprop.getW() - x;
		int nh = oprop.getH() - x;
		
		if( (nw < MIN_SIZE || nh < MIN_SIZE) && !sizeNegatif()){
			return false;
		}
		
		prop.setPos(new Point(oprop.getX() - (nw - oprop.getW()), oprop.getY() - (nh - oprop.getH())));
		prop.setDim(new Dimension(nw, nh));
		parent.validate();
		parent.repaint();
		
		return false;
	}
	
	private boolean resizeTopRightElement(int x, int y){
		if(oprop == null)oprop = (Properties) prop.clone();
		
		if((y > 0 && y < x) || (y<=0 && y > x))x = y;
		
		int nw = oprop.getW() + x;
		int nh = oprop.getH() + x;
		
		if( (nw < MIN_SIZE || nh < MIN_SIZE) && !sizeNegatif()){
			return false;
		}
		
		prop.setPos(new Point(oprop.getX(), oprop.getY() - (nh - oprop.getH())));
		prop.setDim(new Dimension(nw, nh));
		parent.validate();
		parent.repaint();
		
		return false;
	}
	
	private boolean resizeBottomRightElement(int x, int y){
		if(oprop == null)oprop = (Properties) prop.clone();
		
		if((y > 0 && y < x) || (y<=0 && y > x))x = y;
		
		int nw = oprop.getW() + x;
		int nh = oprop.getH() + x;
		
		if( (nw < MIN_SIZE || nh < MIN_SIZE) && !sizeNegatif()){
			return false;
		}
		
		prop.setDim(new Dimension(nw, nh));
		parent.validate();
		parent.repaint();
		
		return false;
	}
	
	private boolean resizeBottomLeftElement(int x, int y){
		if(oprop == null)oprop = (Properties) prop.clone();
		
		if((y > 0 && y < x) || (y<=0 && y > x))x = y;
		
		int nw = oprop.getW() - x;
		int nh = oprop.getH() - x;
		
		if( (nw < MIN_SIZE || nh < MIN_SIZE) && !sizeNegatif()){
			return false;
		}
		
		prop.setPos(new Point(oprop.getX() - (nw - oprop.getW()) , oprop.getY()));
		prop.setDim(new Dimension(nw, nh));
		parent.validate();
		parent.repaint();
		
		return false;
	}

	private boolean resizeLeftElement(int x, int y){
		
		if(oprop == null)oprop = (Properties) prop.clone();
		int nw = oprop.getW() - x;
		
		if(nw < MIN_SIZE && !sizeNegatif()){
			nw = MIN_SIZE;
		}
		prop.setPos(new Point(oprop.getX() - (nw - oprop.getW()) , oprop.getY()));
		prop.setDim(new Dimension(nw, oprop.getH()));
		parent.validate();
		parent.repaint();
		
		return false;
	}
	
	private boolean resizeBottom(int x, int y){
		if(oprop == null)oprop = (Properties) prop.clone();
		
		int nh = oprop.getH() + y;
		
		if(nh < MIN_SIZE && !sizeNegatif()){
			nh = MIN_SIZE;
		}
		
		prop.setDim(new Dimension(oprop.getW(), nh));
		parent.validate();
		parent.repaint();
		
		return false;
	}

	private boolean resizeRightElement(int x, int y){
		if(oprop == null)oprop = (Properties) prop.clone();
		
		int nw = oprop.getW() + x;
		
		if(nw < MIN_SIZE && !sizeNegatif()){
			nw = MIN_SIZE;
		}
		
		prop.setDim(new Dimension(nw, oprop.getH()));
		parent.validate();
		parent.repaint();
		
		return false;
	}	
	
	private boolean resizeTopElement(int x, int y){
		if(oprop == null)oprop = (Properties) prop.clone();
		
			int nh = oprop.getH() - y;
		
		if(nh < MIN_SIZE && !sizeNegatif()){
			nh = MIN_SIZE;
		}
		
		prop.setPos(new Point(oprop.getX(), oprop.getY() - (nh - oprop.getH())));
		prop.setDim(new Dimension(oprop.getW(), nh));
		parent.validate();
		parent.repaint();
		
		return false;
	}
	public boolean sizeNegatif(){
		return false;
	}
	
	public abstract java.io.Serializable getData();
	
	public abstract void setData(java.io.Serializable s);
}
