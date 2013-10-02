package en.odecram.story_liner.skin;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

public class Messages {

	public static void afficherErreur(String message){
		JInternalFrame frame = new JInternalFrame();
		JOptionPane.showMessageDialog(frame, message, "Oops, A Problem !", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void afficherConfirmation(String message){
		JInternalFrame frame = new JInternalFrame();
		JOptionPane.showMessageDialog(frame, message,  "Youpi", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
