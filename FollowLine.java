import java.util.HashMap;
import java.util.Map;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class FollowLine implements Behavior {
	
	private EV3ColorSensor color;
	private Couleur cTable;
	
	public FollowLine(EV3ColorSensor cs) {
		this.color = cs;
	}
	
	@Override
	public boolean takeControl() {
		
		return true;
	}

	@Override
	public void action() {
		
		LCD.clear();
		LCD.drawString("Je Marche qu'est ce tu va faire?", 0, 1);
		LCD.refresh();
		Motor.B.forward();
		Motor.C.forward();
		
		//lire et afficher la couleur
		LCD.clear();
		LCD.drawString("La couleur détécté est: ", 0, 2);
		SampleProvider average = new MeanFilter(color.getRGBMode(), 1);
		color.setFloodlight(Color.WHITE);
		float[] colorSampled = new float[average.sampleSize()];
		average.fetchSample(colorSampled, 0);
		int[] intValues = new int[3];
		intValues[0] = (int)(colorSampled[0]*1000);
		intValues[1] = (int)(colorSampled[1]*1000);
		intValues[2] = (int)(colorSampled[2]*1000);
		
		//Detecter la couleur (distance scalaire)
		String detectedColor = scalarColorDetector(intValues);
		
		LCD.drawString(detectedColor, 0, 3);
		LCD.refresh();
		Delay.msDelay(1000);
	}

	@Override
	public void suppress() {
		Motor.B.stop();
		Motor.C.stop();
	}
	
	public String scalarColorDetector(int[] intValues){
		
		
		Map<String, Double> distance;
		distance = new HashMap();
		
		distance.put("BLEU", Math.sqrt (Math.pow(intValues[0] - cTable.colorTable.get("BLEU")[0], 2.0) +
				Math.pow(intValues[1] - cTable.colorTable.get("BLEU")[1], 2.0) +
				Math.pow(intValues[2] - cTable.colorTable.get("BLEU")[2], 2.0)));
		distance.put("ROUGE", Math.sqrt (Math.pow(intValues[0] - cTable.colorTable.get("ROUGE")[0], 2.0) +
				Math.pow(intValues[1] - cTable.colorTable.get("ROUGE")[1], 2.0) +
				Math.pow(intValues[2] - cTable.colorTable.get("ROUGE")[2], 2.0)));
		distance.put("MARRON", Math.sqrt (Math.pow(intValues[0] - cTable.colorTable.get("MARRON")[0], 2.0) +
				Math.pow(intValues[1] - cTable.colorTable.get("MARRON")[1], 2.0) +
				Math.pow(intValues[2] - cTable.colorTable.get("MARRON")[2], 2.0)));
		distance.put("NOIR", Math.sqrt (Math.pow(intValues[0] - cTable.colorTable.get("NOIR")[0], 2.0) +
				Math.pow(intValues[1] - cTable.colorTable.get("NOIR")[1], 2.0) +
				Math.pow(intValues[2] - cTable.colorTable.get("NOIR")[2], 2.0)));
		distance.put("BLANC", Math.sqrt (Math.pow(intValues[0] - cTable.colorTable.get("BLANC")[0], 2.0) +
				Math.pow(intValues[1] - cTable.colorTable.get("BLANC")[1], 2.0) +
				Math.pow(intValues[2] - cTable.colorTable.get("BLANC")[2], 2.0)));
		distance.put("VERT", Math.sqrt (Math.pow(intValues[0] - cTable.colorTable.get("VERT")[0], 2.0) +
				Math.pow(intValues[1] - cTable.colorTable.get("VERT")[1], 2.0) +
				Math.pow(intValues[2] - cTable.colorTable.get("VERT")[2], 2.0)));
		
		
		return "";
	}

}
