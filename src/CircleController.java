import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class CircleController extends VehicleController{
	double[] startGV;
	double[] endGV;
	double rotVel;
	
	
	public CircleController(double[] startGV, double[] endGV, double rotVel, Simulator s, GroundVehicle v){
		super(s,v);
		this.startGV=startGV;
		this.endGV=endGV;
		this.rotVel=rotVel;
	}	
	
	public Control getControl(double t, double[] state){
		if ( t < 0 ) return null;
		/*Conditional critical region*/
		double myX = state[0];
		double myY = state[1];
		double myT = state[2];
		
		double toX = 50.0;
		double toY = 50.0;
		double deltaY = toY - myY;
		double deltaX = toX - myX;
		double dist = Math.sqrt(Math.pow(deltaY, 2)+Math.pow(deltaX, 2));
		
		
		double speed = 7.0;
		double circleRadius = 25.0;
		double max_speed = Math.PI/4;
		double max_dist = 50*Math.sqrt(2);
		double a = max_speed/(max_dist - circleRadius);
		double rot_vel = (dist - circleRadius)*a + speed/circleRadius; // max pi/4
		
		double b = 1/(max_dist - circleRadius);
		double angleTo = Math.atan2(deltaY, deltaX); //-pi to pi
		double deltaAngle = normalizeAngle(angleTo - myT);
		
		if(0 <= deltaAngle && deltaAngle < Math.PI){
			speed = (dist - circleRadius)*b + 7.0;
			rot_vel = (dist - circleRadius)*a + speed/circleRadius; // max pi/4
		} else {
			speed = (dist - circleRadius)*-1*b + 7.0;
			rot_vel = (dist - circleRadius)*-1*a + speed/circleRadius; // max pi/4
		}
//		System.out.println("dist to center: "+dist);
//		
		
		return new Control(speed, rot_vel);
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
