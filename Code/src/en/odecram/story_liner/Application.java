
package en.odecram.story_liner;

import en.odecram.story_liner.controlers.JframeListener;
import en.odecram.story_liner.widgets.LoadingScreen;
import en.odecram.story_liner.views.MainContent;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author De Cramer Oliver
 */
public class Application {
	
	
	public static void main(String[] r){
		
		//A Loading Screen to make it nicer
		LoadingScreen ls = new LoadingScreen(20, 2000);
		ls.nextStep("Starting Application ...");
		
		// Pour avoir le thème de l'OS
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		
		ls.nextStep("Starting Application ...");
		final JFrame frame = new JFrame("Story Liner");
		frame.setEnabled(false);
		
		ls.nextStep("Starting Application ...");
		//Declaring the main content
		MainContent mcontent = new MainContent(frame, ls);
		mcontent.setSize(800, 600);
		
		ls.nextStep("Starting Application ...");
		//Adding it in the main window (Frame)
		frame.add(mcontent);
		
		ls.nextStep("Starting Application ...");
		//Just a Listener to re-arrange the layout of the page if the wondows size is changed. 
		frame.addComponentListener(new JframeListener(mcontent, frame));
		
		ls.nextStep("Starting Application ...");
		//Setting the size of The Frame
		frame.setSize(800, 600);
		
		ls.nextStep("Starting Application ...");
		//This should open the window in the middle of the screen and not in a random place. 
		frame.setLocationRelativeTo(null);
		
		ls.nextStep("Starting Application ...");
		//Never to sure about this one
		frame.setVisible(true);
		
		ls.nextStep("Starting Application ...");
		//We do a clean Close
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ls.nextStep("Starting Application ...");
		mcontent.reArrenge();
		
		ls.finish();
		frame.setEnabled(true);
	}
}
