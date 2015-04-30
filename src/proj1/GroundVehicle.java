package proj1;
import java.util.Random;
import java.util.concurrent.CountDownLatch;


public class GroundVehicle implements Runnable{

	private double[] pose;
	private double[] poseDerivative;
	private Simulator s = null;
	private double[] lastDerivative = null;
	CountDownLatch vehicleLatch; /* Shared resource */
	private boolean realtime = false;
	
	double lastTime_s = 0;
	double lastTime_ms = 0;
	double lastTime = 0;
	
	private Object thread = null;
	
	
	GroundVehicle(double[] pose, double dx, double dy, double dtheta) {
		//pose[0],pose[1] between 0 and 100. pose[2] between -pi and pi
		this.setPosition(new double[]{pose[0],pose[1],pose[2]});
//		this.setVelocity(this.convertToPoseDeriv(this.normalizeAngle(pose[2]), s, omega));
		this.setVelocity(new double[]{dx,dy,dtheta});
	}
	
	/**
	 * This method returns an array of 3 doubles, corresponding to the position and orientation of the vehicle.
	 */
	public double[] getPosition(){
		synchronized(this.pose){
			double[] toReturn = new double[]{0,0,0};
			toReturn[0] = this.pose[0];
			toReturn[1] = this.pose[1];
			toReturn[2] = this.pose[2];
			return toReturn;
		}
	}
	
	// Code adopted from Prof. Julie Shah -- piazza.com/class/i6zcdkbvj8h3m8?cid=11
	public void addSimulator(Simulator sim){
	    this.s = sim; 
    }

	/**
	 * This method returns an array of 3 doubles, corresponding to the 
	 * linear and angular velocities of the vehicle.
	 */
	public double[] getVelocity(){
		double[] toReturn = new double[]{0,0,0};
		toReturn[0] = this.poseDerivative[0];
		toReturn[1] = this.poseDerivative[1];
		toReturn[2] = this.poseDerivative[2];
		return toReturn;
	}
	
	/**
	 * Updates the position of the vehicle.
	 * @param pos pos[0],pos[1] between 0 and 100. pos[2] between -pi and pi
	 */
	public void setPosition(double[] pos){ 
		double pose_x = Math.min(Math.max(pos[0], 0),100);
		double pose_y = Math.min(Math.max(pos[1], 0),100);
		double pose_theta = this.normalizeAngle(pos[2]); 
		
		if (this.pose != null){
			synchronized(this.pose){
				this.pose = new double[]{pose_x,pose_y,pose_theta};
			}
		} else {
			this.pose = new double[]{pose_x,pose_y,pose_theta};
		}
	}

	/**
	 * s must be between 5 and 10 (rad/s)
	 * omega must be between -pi and pi (rad/s)
	 * @param vel Array of doubles in the order [x_dot,y_dot,theta_dot]
	 */
	public void setVelocity(double[] vel){
		double x_dot = vel[0];
		double y_dot = vel[1];
		double theta_dot = this.normalizeAngle(vel[2]);
		
		double s = Math.sqrt(Math.pow(x_dot, 2) + Math.pow(y_dot, 2));
		while (s < 5){
			//System.out.println("s: " + Double.toString(s));
			x_dot = x_dot*1.1;
			y_dot = y_dot*1.1;
			s = Math.sqrt(Math.pow(x_dot, 2) + Math.pow(y_dot, 2));
		}
		while (s > 10){
			//System.out.println("s: " + Double.toString(s));
			x_dot = x_dot*.9;
			y_dot = y_dot*.9;
			s = Math.sqrt(Math.pow(x_dot, 2) + Math.pow(y_dot, 2));
		}
		
		theta_dot = Math.min(Math.max(theta_dot, -1*Math.PI/4),Math.PI/4);
		this.poseDerivative = new double[]{x_dot,y_dot,theta_dot};
	}
	
	/**
	 * This method modifies the internal velocities according to the specified forward speed and rotational velocity
	 * @param c Control object which specifies forward speed and rot velocity
	 */
	public void controlVehicle(Control c){
		double s_new = c.getSpeed();
		double omega_new = c.getRotVel();
		double theta_current = this.getPosition()[2];
		double[] vel_new = this.convertToPoseDeriv(theta_current, s_new, omega_new);
		this.setVelocity(vel_new);
	}
	
	public void addThread(Object t){
//		if (t instanceof javax.realtime.RealtimeThread){
		this.thread = t;
//		}
	}
	
	public void run(){
		//need to wait until we get the signal, then update our state.
		System.out.println("Vehicle started");
		while(true){					
			double t = s.getSimulatorSec();
			double increment = 0;
			if(t != this.lastTime){								
				// Simulator is ready to let the vehicle update its state
				increment = t - this.lastTime;				
				this.advance(increment, 0);
				this.lastTime = t;
			} else {
				try {
					if (!realtime){
						Thread.sleep(0);
					} else {
						if (this.thread != null){
//							((javax.realtime.RealtimeThread) this.thread).waitForNextPeriod();
						} else {
							System.err.println("No valid thread to wait for.");
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * This method changes the vehicle internal state by computing the appropriate kinematic and dynamic change that
	 * would occur after time t, where t is given in two arguments: seconds and milliseconds. 
	 */
	@SuppressWarnings("unused")
	public void updateState(int sec, int msec){
		updateState((double) sec, (double) msec);
	}
	
	public void updateState(double sec, double msec){
		if (sec < 0 || msec < 0) return;
//		double t = (double) sec + ((double) msec)/1000;
		double t = sec + msec/1000;
		if (t <= 0) return;
		
		double[] vel = this.getVelocity();
		double[] pos = this.getPosition();
		
		double new_x = pos[0] + vel[0]*t;
		double new_y = pos[1] + vel[1]*t;
		double new_theta = normalizeAngle(pos[2] + vel[2]*t);
		
		if (this.lastDerivative != null) {
			double accel_x = (vel[0] - this.lastDerivative[0])/t;
			double accel_y = (vel[1] - this.lastDerivative[1])/t;
			double accel_theta = (vel[2] - this.lastDerivative[2])/t;
			
			new_x += .5*accel_x*Math.pow(t, 2);
			new_y += .5*accel_y*Math.pow(t, 2);
			new_theta += .5*accel_theta*Math.pow(t,2);
		}
		this.lastDerivative = vel;
		
		double s = Math.sqrt(Math.pow(vel[0], 2) + Math.pow(vel[1], 2));
		
		double new_xdot = s*Math.cos(new_theta);
		double new_ydot = s*Math.sin(new_theta);
		double new_thetadot = vel[2];
		
		this.setPosition(new double[]{new_x,new_y,new_theta});
		this.setVelocity(new double[]{new_xdot,new_ydot,new_thetadot});
	}
	
	/**
	 * This method changes the vehicle internal state by computing the appropriate kinematic and dynamic change that
	 * would occur after time t, where t is given in two arguments: seconds and milliseconds. But with added noise. 
	 */
	@SuppressWarnings("unused")
	public void advance(int sec, int msec){
		advance((double) sec, (double) msec);
	}
	
	public void advance(double sec, double msec){
		if (sec < 0 || msec < 0) return;
		double t = sec + msec/1000;
		if (t <= 0) return;
		
//		Random r = new java.util.Random();
//		double errD = r.nextGaussian()*.01;
//		double errC = r.nextGaussian()*.02;
		
		
		double[] vel = this.getVelocity();
		double[] pos = this.getPosition();
		
		double new_x = pos[0] + vel[0]*t;
		double new_y = pos[1] + vel[1]*t;
		double new_theta = normalizeAngle(pos[2] + vel[2]*t);
		
//		new_x += errD*Math.cos(pos[2]) - errC*Math.sin(pos[2]);
//		new_y += errD*Math.sin(pos[2]) + errC*Math.cos(pos[2]);
		
		if (this.lastDerivative != null) {
			double accel_x = (vel[0] - this.lastDerivative[0])/t;
			double accel_y = (vel[1] - this.lastDerivative[1])/t;
			double accel_theta = (vel[2] - this.lastDerivative[2])/t;
			
			new_x += .5*accel_x*Math.pow(t, 2);
			new_y += .5*accel_y*Math.pow(t, 2);
			new_theta += .5*accel_theta*Math.pow(t,2);
		}
		this.lastDerivative = vel;
		
		double s = Math.sqrt(Math.pow(vel[0], 2) + Math.pow(vel[1], 2));
		
		double new_xdot = s*Math.cos(new_theta);
		double new_ydot = s*Math.sin(new_theta);
		double new_thetadot = vel[2];
		
		this.setPosition(new double[]{new_x,new_y,new_theta});
		this.setVelocity(new double[]{new_xdot,new_ydot,new_thetadot});
	}
	
	
	/**
	 * Returns the pose derivative (double[3]) given forward speed and rotational velocity
	 * @param s Forward Speed (m/s) must be between 5 and 10
	 * @param omega Rotational Velocity (rad/s) must be between 5 and 10
	 * @return Returns the pose derivative
	 */
	private double[] convertToPoseDeriv(double theta, double s, double omega){
		s = Math.min(Math.max(s, 5),10);
		omega = this.normalizeAngle(omega);
		
		double x_dot = s*Math.cos(theta);
		double y_dot = s*Math.sin(theta);
		double theta_dot = Math.min(Math.max(omega, -1*Math.PI/4),Math.PI/4);
		
		return new double[]{x_dot,y_dot,theta_dot};
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
	public void isRealtime(boolean realtime){
		this.realtime = realtime;
	}
}
