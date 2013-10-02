package en.odecram.story_liner.widgets.fileSelector;

import en.odecram.story_liner.views.sections.LeftSection;
import en.odecram.story_liner.widgets.fileSelector.controleurs.MouseListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author De Cramer Oliver
 */
public class FileSelector extends JPanel {
	/**
	 * The file on which we are on right now
	 */
	File actualFile;
	
	/**
	 * THe scrool panel in which we will show the list of files/folders
	 */
	JScrollPane scPane;
	
	/**
	 * The Jpanel that will contain the list of files. 
	 */
	JPanel mpanel;
	
	/**
	 * The left section in which is shown the FIleSelector.
	 * @TODO : make it general.
	 */
	LeftSection ls;
	
	ImageIcon file;
	ImageIcon folder;

	public FileSelector(LeftSection l) {
		ls = l;
		
		file = new ImageIcon("img/explorer_file.png");
		folder = new ImageIcon("img/explorer_folder.png");
		//We use a BowLayour
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//We put default path 
		actualFile = new File(".");
		
		//Creating the panel
		mpanel = new JPanel();
		//It also has a BowLayout
		mpanel.setLayout(new BoxLayout(mpanel, BoxLayout.Y_AXIS));
		
		//The scrollPanel
		scPane = new JScrollPane(mpanel);
		//Adding the scroll panel
		this.add(scPane);
		//Let show it.
		show();
		
	}

	public void show() {
		//Getting the file path
		String directory = actualFile.getAbsolutePath();
		
		//List of all files
		String[] files = actualFile.list();
		
		//Removing the existing files from the panel
		mpanel.removeAll();
		
		//Button to go up
		JLabel l = new JLabel(".. UP");
		//a folder icon
		l.setIcon(folder);
		//A mouse listener
		l.addMouseListener(new MouseListener(this,true));
		//Adding the label to the panel
		mpanel.add(l);
		
		//If there is no files
		if (files != null) {
			//All files
			for (int i = 0; i < files.length; i++) {
				//Creating file object to test directory
				File f = new File(directory, files[i]);
				//Creating the label
				l = new JLabel(files[i]);
				ImageIcon ic;
				//If directory then folder icon
				if(f.isDirectory())
					ic = folder;
				else
				//Else document icon
					ic = file;
				//Setting the icon
				l.setIcon(ic);
				//Adding mouse listener
				l.addMouseListener(new MouseListener(this));
				mpanel.add(l);
			}
		}
		//and repaint mpanel
		mpanel.repaint();
	}
	
	/**
	 * Chnage the current path to ...
	 * @param path The new path to show in the explorer
	 */
	public void changeDir(String path){
		if(path != null){
			File fd = new File(path);
			//On verifie le fichier avant de rentre dedans
			if(fd != null && fd.isDirectory()){
				actualFile = fd;
				show();
				this.validate();
				this.repaint();
			}
		}
	}
	
	/**
	 * Moves to the parent directory of the current one
	 */
	public void parentDir(){
		changeDir(actualFile.getParent());
	}
	
	/**
	 * Moves to one of the directories of this direcotry
	 * @param f The name of the sub directory
	 */
	public void subDir(String f){
		File fd = new File(actualFile.getAbsolutePath()+"/"+f);
		if(fd.isDirectory()){
			actualFile = fd;
		}else{
			//If not a directory then maybe image lets see
			ls.setPreviewImage(fd.getAbsolutePath());
		}
		show();
		this.validate();
		this.repaint();
	}
}
