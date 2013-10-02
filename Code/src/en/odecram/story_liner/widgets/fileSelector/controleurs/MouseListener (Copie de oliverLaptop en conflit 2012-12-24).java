
package en.odecram.story_liner.widgets.fileSelector.controleurs;

import en.odecram.story_liner.widgets.fileSelector.FileSelector;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 * Mouse Lisener for the FileSelector. TO enable users to select 
 * a File in the list of files
 * @author De Cramer Oliver
 */
public class MouseListener implements java.awt.event.MouseListener {
	
	/**
	 * The file selector
	 */
	FileSelector fs;
	
	/**
	 * Is it the UP button?
	 */
	boolean up = false;
	
	
	public MouseListener(FileSelector fs) {
		this.fs = fs;
	}
	
	public MouseListener(FileSelector fs, boolean u) {
		this.fs = fs;
		up = u;
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		//If up go to parent dir
		if(up){
			fs.parentDir();
		//If not move to subdirectory
		}else{
			JLabel l = (JLabel)e.getSource();
			fs.subDir(l.getText());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
