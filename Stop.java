
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Stop implements Behavior {
	
	private EV3ColorSensor color;
	private Arbitrator arby;
	
	public Stop(EV3ColorSensor cs){
		this.color = cs;
	}
	
	public void setArbitrator(Arbitrator ar){
		this.arby = ar;
	}
	
	@Override
	public boolean takeControl() { 
		return Button.ESCAPE.isDown();
	}

	@Override
	public void suppress() {
		
	}

	@Override
	public void action() {
		LCD.clear();
		LCD.drawString("EndProcess", 0, 1);
		LCD.drawString("GoodBye", 0, 2);
		LCD.refresh();
		Motor.B.stop(true);
		Motor.C.stop(true);
		color.close();
		arby.stop();
		LCD.clear();
		System.exit(0);
	}
}