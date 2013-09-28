package fr.odecram.simpleButton;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Click;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import fr.lri.swingstates.sm.transitions.TimeOut;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 *
 */
public class SimpleButton extends CStateMachine {

	private CRectangle rect;
	private CText label;
	private BasicStroke defStroke;
	private BasicStroke hovStroke;
	private Color defColor;
	private Color clicColor;
	private Canvas canvas;
	
	Point mouse_start;
	
	SimpleButtonListener listener = null;
	int mmouse;
	
	private State def = new State("Default") {

		@Override
		public void enter() {
			super.enter();
			rect.setStroke(defStroke);
			rect.setFillPaint(defColor);
		}
		
		Transition mouse_hoover = new EnterOnShape("Hoover") {
		};
	};
	
	private State hoover = new State("Hoover") {
		
		public void enter(){
			rect.setStroke(hovStroke);
			rect.setFillPaint(defColor);
		}
		
		Transition mouse_out = new LeaveOnShape("Default") {
		};
		
		Transition mouse_click = new Press(MouseEvent.BUTTON1, "Clicked_IN"){
		};
	};
	
	private State clicked_in = new State("Clicked_IN"){
		
		@Override
		public void enter() {
			super.enter();
			rect.setStroke(hovStroke);
			rect.setFillPaint(clicColor);
			armTimer(2000,true);
		}
		Transition timer = new TimeOut("Clicked_IN"){
			@Override
			public void action() {
				if(listener != null)listener.onSemiClick();
			}
		};
		
		Transition mouse_realese = new Release(MouseEvent.BUTTON1, "Simple_Click"){};
		
		Transition mouse_out = new LeaveOnShape("Clicked_OUT"){	};
	};
	
	private State clicked_out = new State("Clicked_OUT"){

		@Override
		public void enter() {
			super.enter();
			rect.setStroke(defStroke);
				rect.setFillPaint(defColor);
		}
		
		Transition mouse_hoover = new EnterOnShape("Clicked_IN") {};
		Transition real = new Release(MouseEvent.BUTTON1, "Default");
	};
	
	private State simple_click = new State("Simple_Click"){
		public void enter(){
			rect.setStroke(hovStroke);
			rect.setFillPaint(defColor);
			armTimer(500, false);
		}
		
		Transition tout = new TimeOut("Hoover"){
			@Override
			public void action() {
				if(listener != null)listener.onClick();
			}
		};
		Transition mouse_out = new LeaveOnShape("Default");
		
		Transition click = new Press(MouseEvent.BUTTON1, "Double_Click"){};
	};
	
	private State double_click = new State("Double_Click"){
		public void enter(){
			rect.setStroke(hovStroke);
			rect.setFillPaint(clicColor);
			armTimer(2000, true);
		}
		
		Transition tout = new TimeOut("Clicked_IN"){
			@Override
			public void action() {
				if(listener != null)listener.on3on4Click();
			}
		};
		Transition mouse_out = new LeaveOnShape("Clicked_OUT");
		Transition mouse_realese = new Release(MouseEvent.BUTTON1, "Hoover"){
			@Override
			public void action() {
				if(listener != null)listener.onDoubleClick();
			}
		};		
	};

	
	public SimpleButton(Canvas canvas, String text) {
		
		defStroke = new BasicStroke(1);
		hovStroke = new BasicStroke(3);
		defColor = Color.GRAY;
		clicColor = Color.YELLOW;
		
		this.canvas = canvas;	
		label = canvas.newText(3, 3, text, new Font("verdana", Font.PLAIN, 12));
		label.addTag("button").addTag("label");
		label.addTo(canvas);
		
		rect = canvas.newRectangle(0, 0, label.getWidth()+6, label.getHeight()+6);
		rect.addTag("button").addTag("rect");
		rect.addTo(canvas);
		
		rect.setStroke(defStroke);
		
		label.above(rect);
	}

	
	public void setListener(SimpleButtonListener l){
		listener = l;
	}

	public void action() {
		System.out.println("ACTION!");
	}

	public CShape getShape() {
		return label;
	}
}


		/*Transition mouse_moved = new Drag("Clicked"){

			@Override
			public void action() {
				double dx = getPoint().getX() - mouse_start.getX();
				double dy = getPoint().getY() - mouse_start.getY();
				mouse_start = (Point) getPoint();
				canvas.getTag("button").translateBy(dx, dy);
			}	
		};*/