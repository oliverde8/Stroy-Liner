
package en.odecram.story_liner.controlers.mainContent;

import en.odecram.story_liner.controlers.Actions;
import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.sections.StepEditionSection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author De Cramer Oliver
 */
public class KeyboardListener implements KeyListener{
	
	private MainContent mContent;

	public KeyboardListener(MainContent mContent) {
		this.mContent = mContent;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_ESCAPE : 
				mContent.key_cancel();
				break;
			case KeyEvent.VK_ENTER : 
				mContent.key_enter();
				break;
			case KeyEvent.VK_DELETE : 
				mContent.action.delete();
				break;			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		mContent.key_any(e);
	}

}
