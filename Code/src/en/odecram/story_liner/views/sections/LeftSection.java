
package en.odecram.story_liner.views.sections;

import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.models.Abs_View;
import en.odecram.story_liner.views.sections.left.PreviewSection;
import en.odecram.story_liner.widgets.fileSelector.FileSelector;
import javax.swing.JPanel;

/**
 * The left section of our application. COntains the FileSelecter and the 
 * Preview Section.
 * @author De Cramer Oliver
 */
public class LeftSection extends JPanel{
	
	/**
	 * The main content panel
	 */
	private MainContent mContent;
	
	/**
	 * The file Selector Panel
	 */
	private FileSelector files;
	
	/**
	 * The preview section
	 */
	private PreviewSection prev;
	
	/**
	 * @param mContent The main Content panel
	 */
	public LeftSection(MainContent mContent) {
		this.mContent = mContent;
		
		//We won't use a layout manager
		setLayout(null);
		
		//Creating the File Selecter
		files = new FileSelector(this);
		//Applying the MouseListener
		files.addMouseListener(mContent.getMouse_Listener());
		//We added it to our left section panel
		add(files);

		//Creating the preview section
		prev = new PreviewSection(mContent);
		add(prev);
		
		//Let arrange everything
		reArrenge();
		//files.addMouseListener(mContent.getMouse_Listener());
	}

	public void reArrenge() {
		//The file selecter is on the top left
		files.setBounds(0, 0, getWidth()-2, getHeight()-getWidth()-2);
		
		//And the preview section just below
		prev.setBounds(2, getHeight()-getWidth(), getWidth()-4, getWidth() -4);
	}
	
	/**
	 * Changes the previewed image
	 * @param path The path to the image
	 */
	public void setPreviewImage(String path){
		//Let know the preview panel the image it needs to show
		prev.setPreviewImage(path);
		//Lets's update
		mContent.repaint();
	}
	
	public void mRepaint(){
		mContent.reArrenge();
	}
}
