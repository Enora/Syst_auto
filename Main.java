
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class Main {
	
	public static void main(String[] args) {
		
		//Afficher le message de bonjour au tout debut
		LCD.clear();
		LCD.drawString("Entrer = Calibrer", 0, 0);
		LCD.drawString("Other = Continuer", 0, 1);
		LCD.refresh();
//        Button.waitForAnyPress();
		
		//Declaration de variables
		EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S3);
		Motor.C.setSpeed(90);
        Motor.B.setSpeed(90);
//        Motor.C.setAcceleration(2000);
//        Motor.B.setAcceleration(2000);
        
        //Declaration de Behaviors
		Stop bStop = new Stop(cs);
    	Calibrate bCalibrate = new Calibrate(cs);
    	FollowLine bFollowLine = new FollowLine(cs);
        
		if(Button.waitForAnyPress() == Button.ID_ENTER){
    		
			Behavior[] bArray = {bCalibrate, bStop}; // du moins prioritaire au plus prioritaire
    		Arbitrator arby = new Arbitrator(bArray);
    		bStop.setArbitrator(arby);
    		arby.go();
        }
		else {
			
			Behavior[] bArray = {bFollowLine, bStop}; // du moins prioritaire au plus prioritaire
    		Arbitrator arby = new Arbitrator(bArray);
    		bStop.setArbitrator(arby);
    		arby.go();
		}
	}

}