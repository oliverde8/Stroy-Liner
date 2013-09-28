package fr.odecram.story_liner.contolers;

import fr.odecram.story_liner.views.MainContent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Ecute cetain des action faite sur la fenetre telle que le redimensionnement
 *
 * @author De Cramer Oliver
 */
public class JframeListener implements ComponentListener {

	/**
	 * Le compositor
	 */
	private MainContent mContent;

	/**
	 * Uu timer
	 */
	private Timer t;
	private TimerTask tt;

	public JframeListener(MainContent mContent) {
		this.mContent = mContent;
	}

	public void componentResized(ComponentEvent e) {

		if(t != null)
			t.cancel();

		//We create a Task. THe task is to rearange the main Content
		tt = new TimerTask() {

			@Override
			public void run() {
				mContent.reArrenge();
			}
		};
		//We need to wait a few miliseconds for the cariables to be updated by Swing
		//Yes it is silly but well I didn't do swing did I? 
		t = new Timer();
		t.schedule(tt, 10);
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}
}
