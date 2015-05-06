import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class CircleController extends VehicleController{
	double[] startGV;
	double[] endGV;
	double rotVel;
	final double threshold = .5; 
	
	public CircleController(double[] startGV, double[] endGV, double rotVel, Simulator s, GroundVehicle v){
		super(s,v);
		this.startGV=startGV;
		this.endGV=endGV;
		this.rotVel=rotVel;
	}	
	
	public Control getControl(int sec, int msec){
		Control c = new Control(10,this.rotVel);
		double[] currentPos = this.gv.getPosition();
		if (Math.abs(currentPos[0] - endGV[0]) < this.threshold && Math.abs(currentPos[1] - endGV[1]) < this.threshold) {
			return new Control(0,0); //stop
		}
		return c;
	}

	/**
	 * Takes as input an angle and returns the angle in the range [-pi,pi)
	 */
	private double normalizeAngle(double angle){
	    double newAngle = Double.valueOf(angle);
	    if (Math.abs(newAngle) > 100){ //http://stackoverflow.com/a/2323034/4203904
	    	newAngle = newAngle%(2*Math.PI);
	    	newAngle = (newAngle + 2*Math.PI) % 2*Math.PI;  
	    	if (newAngle >= Math.PI){
	    		newAngle -= 2*Math.PI;
	    	}
	    } else {
	    	while (newAngle < -1*Math.PI) newAngle += 2*Math.PI;
		    while (newAngle >= Math.PI) newAngle -= 2*Math.PI;
	    }
	    return newAngle;
	    
	}	
	
}
