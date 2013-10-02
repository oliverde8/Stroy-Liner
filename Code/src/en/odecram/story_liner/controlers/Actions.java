package en.odecram.story_liner.controlers;

import en.odecram.story_liner.views.dragables.RectangleElement;
import en.odecram.story_liner.controlers.mainContent.mouseListener;
import en.odecram.story_liner.models.Step;
import en.odecram.story_liner.models.stepElement.Properties;
import en.odecram.story_liner.views.*;
import en.odecram.story_liner.views.dragables.CercleElement;
import en.odecram.story_liner.views.dragables.LineElement;
import en.odecram.story_liner.views.dragables.TextElement;
import en.odecram.story_liner.views.dragables.models.Abs_StepElement;
import en.odecram.story_liner.views.sections.StepEditionSection;
import en.odecram.story_liner.views.sections.StepMenageSection;
import en.odecram.story_liner.widgets.LoadingScreen;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingWorker;
import en.odecram.story_liner.skin.Messages;

/**
 *
 * @author De Cramer Oliver
 */
public class Actions {

	public final static int MODE_SELECTION = 0;
	public final static int MODE_AJOUT_ELEMENT = 1;
	
	private MainContent mc;
	private StepMenageSection sms;
	private StepEditionSection ses;
	private mouseListener ml;
	private TextAdd tad;
	public static String lastSave = null;

	//Current Settings ...
	private Color lineColor = Color.black;
	private Color bgColor = new Color(0, 0, 0, 0);
	
	private int mode = 0;
	private Abs_StepElement newAbs;
	
	public Actions(MainContent mc, TextAdd tad, StepMenageSection sms, mouseListener ml, StepEditionSection ses) {
		this.mc = mc;
		this.tad = tad;
		this.sms = sms;
		this.ml = ml;
		this.ses = ses;
	}

	public void addtext() {
		tad.start();
	}

	public void delete() {
		if (ml.getViewSelected() != null && ml.getViewSelected() instanceof Abs_StepElement) {
			JInternalFrame frame = new JInternalFrame("");
			int n = JOptionPane.showConfirmDialog(frame, "Are you sure to want to delete this Element? ",
					"Delete an Element?",
					JOptionPane.YES_NO_OPTION);
			if (n == 0) {
				sms.getSelectedStep().removeElement((Abs_StepElement) ml.getViewSelected());
				ml.remove_PaneMouse((Abs_StepElement) ml.getViewSelected());
				mc.reArrenge();
			}
		} else if (sms.getSelectedStep() != null) {
			JInternalFrame frame = new JInternalFrame("");
			int n = JOptionPane.showConfirmDialog(frame, "Are you sure to want to delete this Step? ",
					"Delete a Step?",
					JOptionPane.YES_NO_OPTION);
			if (n == 0) {
				sms.removeStep(sms.getSelectedStep());
			}
		}
	}

	public void saveAs() {
		JInternalFrame frame = new JInternalFrame("");
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));
		int result = fc.showDialog(frame, "Save");

		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			doSave(file.getAbsolutePath());
		}
	}

	public void save() {
		if (lastSave == null) {
			saveAs();
		} else {
			doSave(lastSave);
		}
	}

	private void doSave(final String path) {


		SwingWorker<Integer, Integer> sw = new SwingWorker<Integer, Integer>() {
			@Override
			protected Integer doInBackground() throws Exception {
				//Creating a Loading Screen
				mc.setEnabled(false);
				LoadingScreen ls = new LoadingScreen(5, 1000);
				ls.setAlwaysOnTop(true);

				try {
					ls.nextStep("Starting Save ...");
					FileOutputStream fichier = new FileOutputStream(path);
					ObjectOutputStream oos = new ObjectOutputStream(fichier);

					ls.nextStep("Generating Save ...");
					Save s = new Save(sms.getSteps(),ls);

					ls.nextStep("Writing File");
					oos.writeObject(s);

					ls.nextStep("Finalising");
					oos.flush();
					oos.close();

					lastSave = path;

					ls.finish();
					mc.rename(lastSave);
					mc.setEnabled(true);
				} catch (Exception ex) {
					ls.finish();
					mc.rename(lastSave);
					mc.setEnabled(true);
					Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
					Messages.afficherErreur("Save failed, The file might not ewist or you might not acces to it");
					try {
						finalize();
					} catch (Throwable ex1) {
						Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex1);
					}
				}
				return null;
			}
		};

		sw.execute();


	}

	public void open() {

		JInternalFrame frame = new JInternalFrame("");
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));
		int result = fc.showDialog(frame, "Open");

		if (result == JFileChooser.APPROVE_OPTION) {
			SwingWorker<Integer, Integer> sw = new SwingWorker<Integer, Integer>() {
				@Override
				protected Integer doInBackground() throws Exception {
					mc.setEnabled(false);
					LoadingScreen ls = new LoadingScreen(7, 1000);
					ls.setAlwaysOnTop(true);
					try {
						ls.nextStep("Starting Open ...");
						File file = fc.getSelectedFile();

						ls.nextStep("Opening File ...");
						FileInputStream fichier = new FileInputStream(file.getAbsolutePath());

						ls.nextStep("Reading File ...");
						ObjectInputStream ois = new ObjectInputStream(fichier);

						ls.nextStep("Opening Object ...");
						Save s = (Save) ois.readObject();

						ls.nextStep("Creating Steps & Elements...");
						sms.setSteps(s.generateArray(mc, ses, ls));
						lastSave = file.getAbsolutePath();

						ls.nextStep("Finalising");
						mc.rename(lastSave);
						ls.finish();
						mc.setEnabled(true);
						
					} catch (Exception ex) {
						ls.finish();
						mc.setEnabled(true);
						Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
						Messages.afficherErreur("The file couldn't be opened. It might be incopatible");
						try {
							finalize();
						} catch (Throwable ex1) {
							Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex1);
						}
					}
					return null;
				}
			};
			sw.execute();
		}
	}
	
	public void exportPNG(){
		JInternalFrame frame = new JInternalFrame("");
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = fc.showDialog(frame, "Export");

		if (result == JFileChooser.APPROVE_OPTION) {
			SwingWorker<Integer, Integer> sw = new SwingWorker<Integer, Integer>() {
				@Override
				protected Integer doInBackground() throws Exception {
					int nbStep = 1;
					
					mc.setEnabled(false);
					LoadingScreen ls = new LoadingScreen(sms.getSteps().size()+3, 1000);
					ls.setAlwaysOnTop(true);
				
					ls.nextStep("Starting Export ...");
					
					for(Step s : sms.getSteps()){
						ls.nextStep("Exporting Step : "+nbStep+" ...");
						BufferedImage bi = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB); 
						Graphics g = bi.createGraphics();
						g.setColor(Color.white);
						g.fillRect(0, 0, 800, 600);

						s.view_paint(g,1);
						g.dispose();
						try{
							ImageIO.write(bi,"png",new File(fc.getSelectedFile().getAbsolutePath()+"/"+nbStep+".png"));
						}catch (Exception e) {}
						nbStep++;
					}
					
					ls.nextStep("Finalising");
					ls.finish();
					mc.setEnabled(true);
					return null;
				}
			};
			sw.execute();
		}
		
	}
	
	public void nextStep(){
		sms.nexsStep();
	}
	
	public void prevStep(){
		sms.prevStep();
	}

	public void pushForward() {
		if (ml.getViewSelected() != null && ml.getViewSelected() instanceof Abs_StepElement) {
			Step step = sms.getSelectedStep();
			step.pushForward((Abs_StepElement) ml.getViewSelected());
			sms.getSelectedStep().dprepare();
			sms.getSelectedStep().prepare();
			mc.reArrenge();
		}
	}

	public void pushForwardFull() {
		if (ml.getViewSelected() != null && ml.getViewSelected() instanceof Abs_StepElement) {
			Step step = sms.getSelectedStep();
			step.pushForwardFull((Abs_StepElement) ml.getViewSelected());
			sms.getSelectedStep().dprepare();
			sms.getSelectedStep().prepare();
			mc.reArrenge();
		}
	}
	
	public void pushBack(){
		if (ml.getViewSelected() != null && ml.getViewSelected() instanceof Abs_StepElement) {
			Step step = sms.getSelectedStep();
			step.pushBack((Abs_StepElement) ml.getViewSelected());
			sms.getSelectedStep().dprepare();
			sms.getSelectedStep().prepare();
			mc.reArrenge();
		}
	}
	
	public void selectLineColor(){
		mc.setEnabled(false);

		lineColor = JColorChooser.showDialog(mc, "Chose Color", lineColor);
		
		if(ml.getViewSelected() != null && ml.getViewSelected() instanceof Abs_StepElement){
			Abs_StepElement e = (Abs_StepElement) ml.getViewSelected();
			e.getProp().setLineColor(lineColor);
		}
		mc.updateMenuColors();
		mc.reArrenge();
		mc.jframe.setAlwaysOnTop(true);
		mc.jframe.setAlwaysOnTop(false);
		mc.setEnabled(true);
	}
	
	public void selectBgColor(){
		mc.setEnabled(false);

		bgColor = JColorChooser.showDialog(mc, "Chose Color", bgColor);
		if(ml.getViewSelected() != null && ml.getViewSelected() instanceof Abs_StepElement){
			Abs_StepElement e = (Abs_StepElement) ml.getViewSelected();
			e.getProp().setBgColor(bgColor);
		}
		mc.updateMenuColors();
		mc.reArrenge();
		mc.setEnabled(true);
		mc.jframe.setAlwaysOnTop(true);
		mc.jframe.setAlwaysOnTop(false);
	}
	
	public void makeBgColorTransperent(){
		bgColor = new Color(0, 0, 0, 0);
		if(ml.getViewSelected() != null && ml.getViewSelected() instanceof Abs_StepElement){
			Abs_StepElement e = (Abs_StepElement) ml.getViewSelected();
			e.getProp().setBgColor(bgColor);
		}
		mc.reArrenge();
	}
	
	public void editText(){
		if(ml.getViewSelected() != null && ml.getViewSelected() instanceof TextElement){
			TextElement te = (TextElement)ml.getViewSelected();
			te.mouse_doubleClick();
		}
	}
	
	public void connectElement(){
		if(ml.getViewSelected() != null && ml.getViewSelected() instanceof Abs_StepElement){
			Step step = sms.getSelectedStep();
			if(!step.repairChainStart((Abs_StepElement)ml.getViewSelected())){
				Messages.afficherErreur("The chain couldn't be repaired. \n You may have deleted the old elements of the chain.");
			}
		}
	}
	
	public void disconnectElement(){
		if(ml.getViewSelected() != null && ml.getViewSelected() instanceof Abs_StepElement){
			Step step = sms.getSelectedStep();
			if(!step.startbreakChain((Abs_StepElement)ml.getViewSelected())){
				Messages.afficherErreur("The chain coudln't be broken. This element might be the first of it's kind or you might be in the first Step");
			}
		}
	}
	
	public void modeSelect(){
		mode = MODE_SELECTION;
		newAbs = null;

		Enumeration<AbstractButton> e;
		e = mc.menu_group_mode.getElements();
		JRadioButtonMenuItem rb = (JRadioButtonMenuItem) e.nextElement();
		while(rb != null){
			if(rb.getActionCommand().compareTo("mode.selection") == 0){
				rb.setSelected(true);
				break;
			}
			rb = (JRadioButtonMenuItem) e.nextElement();
		}
	}
	
	public void modeRectangle(){
		mode = MODE_AJOUT_ELEMENT;
		newAbs = new RectangleElement(mc, new Properties(0, 0, 0, 0, cloneColor(lineColor)), ses);
		newAbs.getProp().setBgColor(cloneColor(bgColor));
	}
	
	public void modeCercle(){
		mode = MODE_AJOUT_ELEMENT;
		newAbs = new CercleElement(mc, new Properties(0, 0, 0, 0, cloneColor(lineColor)), ses);
		newAbs.getProp().setBgColor(cloneColor(bgColor));
	}
	
	public void modeLine(){
		mode = MODE_AJOUT_ELEMENT;
		newAbs = new LineElement(mc, new Properties(0, 0, 0, 0, cloneColor(lineColor)), ses);
		newAbs.getProp().setBgColor(cloneColor(bgColor));
	}
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public Abs_StepElement getNewAbs() {
		return newAbs;
	}

	public void setNewAbs(Abs_StepElement newAbs) {
		this.newAbs = newAbs;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}
	
	public static Color cloneColor(Color origin){
		return new Color(origin.getRed(), origin.getGreen(), origin.getBlue(), origin.getAlpha());
	}
	
	
}
