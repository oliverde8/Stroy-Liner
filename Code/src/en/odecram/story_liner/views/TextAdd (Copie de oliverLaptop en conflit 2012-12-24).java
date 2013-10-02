
package en.odecram.story_liner.views;

import en.odecram.story_liner.controlers.mainContent.KeyboardListener;
import en.odecram.story_liner.models.stepElement.Properties;
import en.odecram.story_liner.views.dragables.TextElement;
import en.odecram.story_liner.views.sections.StepEditionSection;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 * This panel allows text to be added. 
 * To do so it will show a text Area when demanded
 * 
 * @author De Cramer Oliver
 */
public class TextAdd extends JPanel{

	/**
	 * The main content panel
	 */
	private MainContent mc;
	
	/**
	 * The Step Edition section on top of which it will show itself
	 */
	private StepEditionSection ses;
	
	/**
	 * Is it active. By default no it will be activated when asked by user
	 */
	private boolean active = false;
	
	/**
	 * The text area
	 */
	private JTextArea ta;
	
	private TextElement te = null;
	private String origin = "";
	/**
	 * 
	 * @param mc THe main content panel
	 * @param s The step edition section
	 */
	public TextAdd(MainContent mc, StepEditionSection s) {
		this.mc = mc;
		ses = s;
		
		//We wont use a Layout manager
		setLayout(null);
		
		//Creating the text area. 
		ta = new JTextArea();
		//Adding a key listener to the text area
		ta.addKeyListener(new KeyboardListener(mc));
		add(ta);
		
		//Creating a nice border around the text area
		Border b = BorderFactory.createLineBorder(Color.black);
		ta.setBorder(b);
	}
			
	
	public void reArrenge() {
		ta.setBounds(0, 0, getWidth(), getHeight());
	}
	
	public void start(){
		//Reset the tewt
		ta.setText("");
		//Activate
		active = true;
		//Update all views
		mc.reArrenge();
	}
	
	public void start(TextElement te){
		this.te = te;
		//Reset the text
		origin = (String)te.getData();
		ta.setText((String)te.getData());
		//Activate = true;
		active = true;
		//Update views
		mc.reArrenge();
	}
	
	
	public void cancel(){
		//Deactivete
		active = false;
		
		if(te != null){
			te.setData(origin);
		}
		
		te = null;
		//Update all views
		mc.reArrenge();
	}
	
	/**
	 * When the text is confirmed
	 */
	public void confirm(){
		
		if(te == null){
			//Creating a new element with a certain size. (size need to be corrected)
			Properties p = new Properties(0, 0, 0, 0, mc.action.getLineColor());
			p.setBgColor(mc.action.getBgColor());
			//Creating a tewt element
			TextElement te = new TextElement(mc, p, ses,  ta.getText());
			//Adding the tewt element
			ses.addElement(te);
		}else{
			te.setData(ta.getText());
		}
		//Deactivating
		active = false;
		//
		te = null;
		//Redraw
		mc.reArrenge();
		
	}
	
	public void keyAction(KeyEvent e){
		if(te != null){
			te.setData(ta.getText());
			mc.reArrenge();
		}
	}
	
	public boolean isActive(){
		return active;
	}
}
