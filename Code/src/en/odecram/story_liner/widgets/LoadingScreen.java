package en.odecram.story_liner.widgets;

import en.odecram.story_liner.models.Loading;
import java.awt.BorderLayout;

import javax.swing.JProgressBar;
import javax.swing.JWindow;


import en.odecram.story_liner.skin.SplashImage;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LoadingScreen extends Loading{
	
	
	
	// Fenetre principale
	private JWindow frmPrincipale;
	private JProgressBar pgbChargement;
	private JLabel jtMessage;
	
	private long startTime;
	
	private int minTime;

	public LoadingScreen(int nbSteps) {
		this(nbSteps, 0);
	}
	
	
	
	public LoadingScreen(int nbSteps, int minTime){
		this.minTime = minTime;
		this.nombreEtapes = nbSteps;
		etapeActuelle = 0;
		
		frmPrincipale = new JWindow();
		frmPrincipale.setSize(400, 260);
		
		JPanel pnlChargement = new JPanel(new BorderLayout());
		
		pgbChargement = new JProgressBar();
		pgbChargement.setValue(0);
		
		jtMessage = new JLabel();
		jtMessage.setHorizontalAlignment(SwingConstants.CENTER);
		
		SplashImage image = new SplashImage();
		
		frmPrincipale.add(image);
		
		pnlChargement.add(jtMessage,BorderLayout.NORTH);
		pnlChargement.add(pgbChargement,BorderLayout.SOUTH);
		
		frmPrincipale.add(pnlChargement,BorderLayout.SOUTH);
		frmPrincipale.setLocationRelativeTo(null);
		frmPrincipale.setVisible(true);
		
		startTime = System.currentTimeMillis();
	}
	
	public void nextStep(String s){
		etapeActuelle++;
		jtMessage.setText(s);
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}*/
		if( etapeActuelle >= nombreEtapes)
			frmPrincipale.dispose();
		else {
			float pourcentage = ((float)etapeActuelle/nombreEtapes)*100;
			pgbChargement.setValue( (int) pourcentage );
			
		}
	}
	
	public void nextStep(){
		nextStep("");
	}
	
	public void addStep(int enPlus){
		nombreEtapes += enPlus;
		float pourcentage = ((float)etapeActuelle/nombreEtapes)*100;
		pgbChargement.setValue( (int) pourcentage );
	}

	@Override
	public void finish() {
		System.out.println("Finish");
		frmPrincipale.setAlwaysOnTop(false);
		if( (System.currentTimeMillis() - startTime) < minTime){
			frmPrincipale.setVisible(true);
			frmPrincipale.setAlwaysOnTop(true);
			try {
				Thread.sleep(minTime - (System.currentTimeMillis() - startTime));
			} catch (InterruptedException e) {}
		}
		pgbChargement.setValue(100);
		frmPrincipale.dispose();
		super.finish();
	}
	
	public void setAlwaysOnTop(boolean b){
		frmPrincipale.setAlwaysOnTop(b);
	}
	
}
