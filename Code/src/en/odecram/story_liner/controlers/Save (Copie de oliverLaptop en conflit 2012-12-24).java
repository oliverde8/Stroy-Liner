
package en.odecram.story_liner.controlers;

import en.odecram.story_liner.models.Loading;
import en.odecram.story_liner.models.Step;
import en.odecram.story_liner.models.stepElement.Properties;
import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.dragables.CercleElement;
import en.odecram.story_liner.views.dragables.ImageElement;
import en.odecram.story_liner.views.dragables.RectangleElement;
import en.odecram.story_liner.views.dragables.TextElement;
import en.odecram.story_liner.views.dragables.models.Abs_StepElement;
import en.odecram.story_liner.views.sections.StepEditionSection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Will create an XML file to save all the data
 * 
 */
public class Save implements java.io.Serializable{
	
	
	ArrayList<String> save_steps;
	
	Hashtable<Integer, Properties> save_props;
	
	Hashtable<Integer, String> save_elems;
	Hashtable<Integer, java.io.Serializable> save_elems_data;

	public Save() {
		save_steps = new ArrayList<String>();
		save_props = new Hashtable<Integer, Properties>();
		save_elems = new Hashtable<Integer, String>();
		save_elems_data = new Hashtable<Integer, Serializable>();
	}
	
	public Save(ArrayList<Step> steps){
		this(steps, null);
	}
	
	public Save( ArrayList<Step> steps, Loading l){
		
		if(l!=null)l.addStep(steps.size()*2 + 2);
		
		if(l!=null) l.nextStep("Generating Save | Starting");
		save_steps = new ArrayList<String>();
		save_props = new Hashtable<Integer, Properties>();
		save_elems = new Hashtable<Integer, String>();
		save_elems_data = new Hashtable<Integer, java.io.Serializable>();
		
		if(l!=null) l.nextStep("Generating Save | Starting");
		
		int nbstep = 1;
		for (Step s : steps){
			String ss = "";
			
			if(l!=null) l.nextStep("Generating Save | Generating Elements for Step "+nbstep);
			ArrayList<Abs_StepElement> elems = s.getElements();			
			l.addStep(elems.size()*2);
			for (Abs_StepElement e : elems){
				ss += e.getId()+";";
				
				if(l!=null) l.nextStep("Generating Save | Generating Step "+nbstep+" | Element "+e.getId());
				
				if(!save_elems.contains(e.getId())){
					String se = e.getClass().getSimpleName()+";";
					
					Properties p = e.getProp();
					se += p.getId()+";"+e.getOld_id()+";";
					if(!save_props.contains(p.getId())){
						save_props.put(p.getId(), p);
					}
					if(l!=null) l.nextStep("Generating Save | Generating Step "+nbstep+" | Data of Element "+e.getId());
					save_elems.put(e.getId(), se);
					save_elems_data.put(e.getId(), e.getData());
				}else{
					if(l!=null) l.nextStep("Generating Save | Generating Step "+nbstep+" | Data of Element "+e.getId());
				}
				
			}
			save_steps.add(ss);
			if(l!=null) l.nextStep("Generating Save | Generating Elements for Step "+nbstep+" DONE");
			nbstep++;
		}
	}
	
	public ArrayList<Step> generateArray(MainContent mc, StepEditionSection ses, Loading l){
		
		if(l!=null)l.addStep(save_steps.size() + 2);
		
		if(l!=null) l.nextStep("Creating Steps | Starting ...");
		ArrayList<Step> steps = new ArrayList<Step>();
		Hashtable<Integer, Abs_StepElement> elems = new Hashtable<Integer, Abs_StepElement>();
		
		if(l!=null) l.nextStep("Creating Steps | ...");
		
		int nbstep = 1;
		for (String s : save_steps){
			
			if(l!=null) l.nextStep("Creating Steps | Starting to create Step : "+nbstep);
			
			String[] ss = s.split(";");
			
			Step curren = new Step(mc);
			steps.add(curren);
			
			for(int i = 0; i < ss.length; i++ ){
				try{
					int j = Integer.parseInt(ss[i]);
					if(elems.containsKey(j)){
						curren.addElement(elems.get(j));
					}else{
						String[] ms = save_elems.get(j).split(";");
						int ji = Integer.parseInt(ms[1]);
						int jold = Integer.parseInt(ms[2]);
						
						Abs_StepElement aelem;
						if( ms[0].compareTo("ImageElement") == 0){
							aelem = new ImageElement(mc, save_props.get(ji), ses);
						}else if( ms[0].compareTo("CercleElement") == 0){
							aelem = new CercleElement(mc, save_props.get(ji), ses);
						}else if( ms[0].compareTo("lineElement") == 0){
							aelem = new CercleElement(mc, save_props.get(ji), ses);
						}else if( ms[0].compareTo("RectangleElement") == 0){
							aelem = new RectangleElement(mc, save_props.get(ji), ses);
						}else{
							aelem = new TextElement(mc, save_props.get(ji), ses, s);
							TextElement te = (TextElement)aelem;
							te.firstGo = false;
						}
						aelem.setId(j);
						aelem.setOld_id(jold);
						aelem.setData(save_elems_data.get(j));
						elems.put(j, aelem);
						aelem.setIsSelectable(true);
						curren.addElement(aelem);
					}
				}catch(Exception e){
					
				}
			}
			nbstep++;
		}
		
		//Connecting Steps
		for(int i = 0; i<steps.size()-1; i++){
			steps.get(i).setNext_step(steps.get(i+1));
		}
		
		return steps;
	}
	
}
