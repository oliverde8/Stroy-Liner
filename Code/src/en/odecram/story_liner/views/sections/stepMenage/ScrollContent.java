
package en.odecram.story_liner.views.sections.stepMenage;

import en.odecram.story_liner.models.Step;
import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.dragables.models.Abs_StepElement;
import en.odecram.story_liner.views.sections.StepEditionSection;
import en.odecram.story_liner.views.sections.StepMenageSection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * The content in the scroll Menagers scroll panel.
 * @author De Cramer Oliver
 */
public class ScrollContent extends JPanel{

	
	public static final int HORIZANTAL_SPACE = 15;
	public static final int VERTICAL_SPACE = 5;
	public static final int ADD_BOX_SIZE = 40;
	
	
	/**
	 * The main Content panel
	 */
	private MainContent mContent;
	
	ArrayList<Step> steps;
	
	private Step selected;

	private int step_preview_h;
	
	private int step_preview_w;
	
	private float scale;
	
	private StepEditionSection ses;
	
	private StepMenageSection sms;
	
	private Image add_img;
	
	public ScrollContent(MainContent mContent, ArrayList<Step> steps, StepEditionSection s, StepMenageSection sms) {
		this.mContent = mContent;
		
		this.steps  = steps;
		
		this.sms = sms;
		//We set a size just to test it.
		
		ses = s;
		
		ImageIcon ic = new ImageIcon("img/addStep.png");
		add_img = ic.getImage();
		
		addMouseListener(new en.odecram.story_liner.controlers.stepMenageSection.MouseListener(this, mContent));
	}

	
	public void updateSize(int i){
		
		//setSize(new Dimension((i * (step_preview_w + HORIZANTAL_SPACE*2 + ADD_BOX_SIZE))+ADD_BOX_SIZE, mContent.STEPMENAGESECTION_HEIGHT-20));
		setPreferredSize(new Dimension((i * (step_preview_w + HORIZANTAL_SPACE*2 + ADD_BOX_SIZE))+ADD_BOX_SIZE, mContent.STEPMENAGESECTION_HEIGHT-20));
		revalidate();
	}
	
	/**
	 * 
	 * @param g The 2dGraphics in which we need to draw
	 * @param height The height of rhe Vertical Separation Bar
	 */
	public void paint(Graphics g){
		
		step_preview_h = getHeight() - VERTICAL_SPACE*2;
		scale =  ((float)step_preview_h/600);
		step_preview_w = (int)( (float)800 * scale);
		
		int i = 0;
		g.clearRect(0, 0, getWidth(), getHeight());
		if(!steps.isEmpty()){
			
			Graphics2D g2d = (Graphics2D)g;
			
			int yb = (getHeight() - ADD_BOX_SIZE)/2;
			
			for (Step s : steps){				
				
				int x = HORIZANTAL_SPACE + ( (step_preview_w + ADD_BOX_SIZE + 2*HORIZANTAL_SPACE) * i);
				int y = 4;
				
				if(selected == s){
					drawSelected(g2d, x, y, step_preview_w, step_preview_h);
				}
				
				//Drawing preview
				Graphics g2 = g.create(x, y, step_preview_w, step_preview_h);
				g.drawRect(x-1, y-1, step_preview_w+2, step_preview_h+2);
				g.setColor(Color.WHITE);
				g.fillRect(x, y, step_preview_w, step_preview_h);
				g.setColor(Color.BLACK);
				s.view_paint(g2, scale);
				
				//drawing Add Box
				//@todo Make it an image
				/*g.setColor(Color.BLUE);
				g.fillRect(x + step_preview_w + HORIZANTAL_SPACE, yb, ADD_BOX_SIZE, ADD_BOX_SIZE);*/
				
				g2d.drawImage(add_img, x + step_preview_w + HORIZANTAL_SPACE, yb, ADD_BOX_SIZE, ADD_BOX_SIZE, getParent());
				
				g.setColor(Color.BLACK);
				
				i++;
			}
		}
	}

	
	private void drawSelected(Graphics2D g2d, int x, int y, int w, int h){
		//The width of the hallow
		int stroke =  5;

		//The colors of the Hallow. Yellow slightly transparent and full transperency
		Color c1 = new Color(Color.YELLOW.getRed(), Color.YELLOW.getGreen(), Color.YELLOW.getBlue(), 200);
		Color c2 = new Color(255, 255, 255, 0);

		//Creating a Gradient paint from top left corner for the rectengale
		GradientPaint gp = new GradientPaint(x - stroke, y + stroke, c1,
												x + w/2 ,y + h/2, c2);
		g2d.setPaint(gp);
		g2d.fillRect(x - stroke, y - stroke, w + (2 * stroke), h + (2 * stroke));

		//BOttom Left Corner
		gp = new GradientPaint(x - stroke, y + stroke + h, c1,
												x + w/2 ,y + h/2, c2);
		g2d.setPaint(gp);
		g2d.fillRect(x - stroke, y - stroke, w + (2 * stroke), h + (2 * stroke));

		//Bottom Right Corner
		gp = new GradientPaint(x + stroke + w, y + stroke, c1,
												x + w/2 ,y + h/2, c2);
		g2d.setPaint(gp);
		g2d.fillRect(x - stroke, y - stroke, w + (2 * stroke), h + (2 * stroke));

		//Top right Corner
		gp = new GradientPaint(x + stroke + w, y + stroke + h, c1,
												x + w/2 ,y + h/2, c2);
		g2d.setPaint(gp);
		g2d.fillRect(x - stroke, y - stroke, w + (2 * stroke), h + (2 * stroke));
	}
	
	public Step getSelectedStep() {
		return selected;
	}
	
	public void setSelectedStep(Step s){
		selected = s;
		ses.setStep(s);
		mContent.reArrenge();
	}

	public ArrayList<Step> getSteps() {
		return steps;
	}

	public int getStep_preview_h() {
		return step_preview_h;
	}

	public int getStep_preview_w() {
		return step_preview_w;
	}
	
	public void addNewStep(int p){
		sms.addNewStep(p);
	}
	
	public void setSteps(ArrayList<Step> s){
		steps = s;
		selected = steps.get(0);
		ses.setStep(selected);
		mContent.reArrenge();
	}
	
	public void nextStep(){
		int i = steps.indexOf(selected);
		if(i+1 >= steps.size())
			i = 0;
		else
			i++;
		setSelectedStep(steps.get(i));
	}
	
	public void prevStep(){
		int i = steps.indexOf(selected);
		if(i-1 < 0){
			i = steps.size()-1;
		}
		else
			i--;
		setSelectedStep(steps.get(i));
	}
	
}
