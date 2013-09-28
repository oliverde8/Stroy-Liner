
package fr.odecram.story_liner;

import fr.lri.swingstates.canvas.Canvas;
import fr.odecram.story_liner.contolers.JframeListener;
import fr.odecram.story_liner.views.MainContent;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author De Cramer Oliver
 */
public class Application {

	public static void main(String[] r){

		JFrame frame = new JFrame("Demonstration - HMI Project - Oliver De Cramer");
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
		}
		
		//Declaring the main content
		MainContent mcontent = new MainContent();
		frame.add(mcontent);
		
		//Just a Listener to re-arrange the layout of the page if the wondows size is changed. 
		frame.addComponentListener(new JframeListener(mcontent));		
		
		//Setting the size of The Frame
		frame.setSize(800, 600);
		
		//This should open the window in the middle of the screen and not in a random place. 
		frame.setLocationRelativeTo(null);
		
		//Never to sure about this one
		frame.setVisible(true);
		
		//We do a clean Close
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
}
