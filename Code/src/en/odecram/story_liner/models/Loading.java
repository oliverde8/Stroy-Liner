package en.odecram.story_liner.models;

/**
 *
 * @author De Cramer Oliver
 */
public class Loading {
	
	protected int nombreEtapes;
	protected int etapeActuelle;
	
	public void nextStep(String s){
		etapeActuelle++;
		
	}
	
	public void nextStep(){
		nextStep("");
	}
	
	public void addStep(int enPlus){
		nombreEtapes += enPlus;
	}
	
	public void finish(){
		etapeActuelle = nombreEtapes;
	}
	
}
