import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class Calibrate implements Behavior{

	private EV3ColorSensor color;
	private boolean allColorsDone;
	private Couleur cTable;
	
	public Calibrate(EV3ColorSensor cs){
		this.color = cs;
		this.allColorsDone = false;
		this.cTable = new Couleur();
	}
	
	@Override
	public boolean takeControl() {
		return true;
	}
	
	@Override
	public void action() {

		SampleProvider average = new MeanFilter(color.getRGBMode(), 1);
		color.setFloodlight(Color.WHITE);
		if(allColorsDone == true) {
			LCD.clear();
			LCD.drawString("all colors calibrated ", 0, 0);
			
			//affichage temporaire
			LCD.drawString(String.valueOf(cTable.colorTable.get("BLEU")[0])+","
					+cTable.colorTable.get("BLEU")[1]+","
					+cTable.colorTable.get("BLEU")[2]+",", 0, 2);
			LCD.drawString(String.valueOf(cTable.colorTable.get("ROUGE")[0])+","
					+cTable.colorTable.get("ROUGE")[1]+","
					+cTable.colorTable.get("ROUGE")[2]+",", 0, 3);
			LCD.drawString(String.valueOf(cTable.colorTable.get("MARRON")[0])+","
					+cTable.colorTable.get("MARRON")[1]+","
					+cTable.colorTable.get("MARRON")[2]+",", 0, 7);
			LCD.drawString(String.valueOf(cTable.colorTable.get("NOIR")[0])+","
					+cTable.colorTable.get("NOIR")[1]+","
					+cTable.colorTable.get("NOIR")[2]+",", 0, 5);
			LCD.drawString(String.valueOf(cTable.colorTable.get("BLANC")[0])+","
					+cTable.colorTable.get("BLANC")[1]+","
					+cTable.colorTable.get("BLANC")[2]+",", 0, 6);
			LCD.drawString(String.valueOf(cTable.colorTable.get("VERT")[0])+","
					+cTable.colorTable.get("VERT")[1]+","
					+cTable.colorTable.get("VERT")[2]+",", 0, 4);
			
			
			LCD.refresh();
		}
		else {
			for(Couleur.couleur c : Couleur.couleur.values()) {
				
				LCD.clear();
				LCD.drawString("Calibrate "+c, 0, 2);
				//Attendre 3 secondes
				for(int i=0; i<5; i++) {
					LCD.drawString(String.valueOf(i), 0, 3);
					Delay.msDelay(1000);
				}
				//Lire la couleur et l'afficher à l'écran
				float[] colorSampled = new float[average.sampleSize()];
				average.fetchSample(colorSampled, 0);
				int[] intValues = new int[3];
				intValues[0] = (int)(colorSampled[0]*1000);
				intValues[1] = (int)(colorSampled[1]*1000);
				intValues[2] = (int)(colorSampled[2]*1000);
				LCD.drawString(intValues[0]+" "+intValues[1]+" "+intValues[2], 5, 3);
				LCD.refresh();
				
				//Sauvegarder la couleur dans la hashtable
				cTable.setColors(c.toString(), intValues);
				
					
//				//Attendre 5 sec
//				for(int i=0; i<8; i++) {
//					LCD.drawString(String.valueOf(i), 0, 3);
//					Delay.msDelay(1000);
//				}
				allColorsDone = true;
			}
		}
		
		
	}

	@Override
	public void suppress() {
		
	}
	
}
