
package en.odecram.story_liner.controlers.mainContent;

import en.odecram.story_liner.controlers.Actions;
import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.TextAdd;
import en.odecram.story_liner.views.dragables.models.Abs_StepElement;
import en.odecram.story_liner.views.sections.StepMenageSection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * To make work the main Menu. 
 * 
 * @author De Cramer Oliver
 */
public class MenuActionListener implements ActionListener{

	private Actions a;

	public MenuActionListener(Actions a) {
		this.a = a;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "nothing")
			System.out.println("Nothing...");
		else if(e.getActionCommand() == "add text"){
			a.addtext();
		}else if(e.getActionCommand() == "edit.delete"){
			a.delete();
		}else if(e.getActionCommand() == "edit.forward"){
			a.pushForward();
		}else if(e.getActionCommand() == "edit.forwardFull"){
			a.pushForwardFull();
		}else if(e.getActionCommand() == "edit.back"){
			a.pushBack();
		}else if(e.getActionCommand() == "edit.back"){
			a.pushBack();
		}else if(e.getActionCommand() == "edit.drawColor"){
			a.selectLineColor();
		}else if(e.getActionCommand() == "edit.backColor"){
			a.selectBgColor();
		}else if(e.getActionCommand() == "edit.backColorTransperent"){
			a.makeBgColorTransperent();
		}else if(e.getActionCommand() == "edit.disconnect"){
			a.disconnectElement();
		}else if(e.getActionCommand() == "edit.connect"){
			a.connectElement();
		}
		else if(e.getActionCommand() == "edit.text"){
			a.editText();
		}
		
		else if(e.getActionCommand() == "mode.select"){
			a.modeSelect();
		}
		else if(e.getActionCommand() == "mode.rectangle"){
			a.modeRectangle();
		}
		else if(e.getActionCommand() == "mode.cercle"){
			a.modeCercle();
		}else if(e.getActionCommand() == "mode.line"){
			a.modeLine();
		}
		
		else if(e.getActionCommand() == "File.save"){
			a.save();
		}else if(e.getActionCommand() == "File.saveAs"){
			a.saveAs();
		}else if(e.getActionCommand() == "File.open"){
			a.open();
		}else if(e.getActionCommand() == "File.nextStep"){
			a.nextStep();
		}else if(e.getActionCommand() == "File.prevStep"){
			a.prevStep();
		}else if(e.getActionCommand() == "File.exportPNG"){
			a.exportPNG();
		}
	}
	
	

}
