
package en.odecram.story_liner.models.stepElement;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 * This represents Some of the proprietes a StepElement may have. This will allow
 * elements to inherit from their parents steps. 
 * 
 * @author De Cramer Oliver
 */
public class Properties implements Cloneable, java.io.Serializable{

	protected static int unique_num = 0;
	public static int getUniqueNum(){ return unique_num++; }
	
	/**
	 * The position of this Element
	 */
	protected Point pos;
	
	/**
	 * The dimension of this element
	 */
	protected Dimension dim;
	
	/**
	 * Is this element connected with the element in the father Step
	 */
	protected boolean isConnected = true;

	
	protected Color lineColor;
	protected Color bgColor = null;
	
	private int id;
	
	/**
	 * Creates a Proprties with same values. but different references
	 * @param origin 
	 */
	public Properties(Properties origin){
		pos = (Point) origin.pos.clone();
		dim = (Dimension) origin.dim.clone();
		
		isConnected = origin.isConnected;
		id = getUniqueNum();
		lineColor = new Color(origin.lineColor.getRed(), origin.lineColor.getGreen(), origin.lineColor.getBlue(), origin.lineColor.getAlpha());
		
		if(origin.bgColor != null)
			bgColor = new Color(origin.bgColor.getRed(), origin.bgColor.getGreen(), origin.bgColor.getBlue(), origin.bgColor.getAlpha());
	}
	
	/**
	 * 
	 * @param pos The position of the element
	 * @param dim The dimension of the element
	 */
	public Properties(Point pos, Dimension dim, Color c1) {
		id = getUniqueNum();
		this.pos = pos;
		this.dim = dim;
		this.lineColor = c1;
	}

	/**
	 * 
	 * @param x The X position of the element
	 * @param y The Y position of the element
	 * @param w The Width of the element
	 * @param h The height of the element
	 */
	public Properties(int x, int y, int w, int h, Color c1) {
		this(new Point(x,y), new Dimension(w,h), c1);
	}
	
	/**
	 * 
	 * @return The position of the element
	 */
	public Point getPos() {
		return pos;
	}

	/**
	 * Changes the position of the element. 
	 * @param pos The new position
	 */
	public void setPos(Point pos) {
		this.pos = pos;
	}
	
	/**
	 * 
	 * @return The dimensions of the element
	 */
	public Dimension getDim() {
		return dim;
	}
	
	/**
	 * Changes the dimension of the element
	 * @param dim  The new dimension
	 */
	public void setDim(Dimension dim) {
		this.dim = dim;
	}
	
	/**
	 * Elements may be connected with elements in other steps. is it the case? 
	 * @return Is the lement connected
	 */
	public boolean isConnected() {
		return isConnected;
	}
	
	/**
	 * @TODO Not sure if needed. Probably wrong need to think about it
	 * @param isConnected 
	 */
	public void setIsConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	public int getX(){
		return pos.x;
	}
	
	public int getY(){
		return pos.y;
	}
	
	public int getW(){
		return dim.width;
	}
	
	public int getH(){
		return dim.height;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}
	
	public Object clone(){
		return new Properties(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
