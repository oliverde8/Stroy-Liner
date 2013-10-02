package en.odecram.story_liner.controlers;

import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.sections.StepEditionSection;
import java.awt.Frame;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

/**
 * Listens to actions done to the Window of the JFrame. 
 * Will alow the interface to react to window size change
 *
 * @author De Cramer Oliver
 */
public class JframeListener implements ComponentListener {

	/**
	 * The JPanel holding the main COntent to refresh it if necessary
	 */
	private MainContent mContent;

	/**
	 * Sometimes swing is strange, we need timers...
	 */
	private Timer t;
	private TimerTask tt;
	private JFrame jf;
	
	/**
	 * 
	 * @param mContent The JPanel holding the main COntent to refresh it if necessary
	 * @param f The jframe for which the listener works
	 */
	public JframeListener(MainContent mContent, JFrame f) {
		this.mContent = mContent;
		jf = f;
	}
	
	public void componentResized(ComponentEvent e) {
		
		//If there is already a timer going on we cancel it
		if(t != null)
			t.cancel();

		//We create a Task. THe task is to rearange the main Content
		tt = new TimerTask() {

			@Override
			public void run() {
				mContent.reArrenge();
			}
		};
		//We need to wait a few miliseconds for the variables to be updated by Swing
		//Yes it is silly but well I didn't do swing did I? 
		t = new Timer();
		t.schedule(tt, 50);
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}
}
