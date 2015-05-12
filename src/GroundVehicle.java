import java.lang.IllegalArgumentException;

public class GroundVehicle extends Thread
{
	private double x, y, theta;
	private double xdot, ydot, thetadot;

	private static int totalNumVehicles = 0;
	private int vehicleID;

	private Simulator sim = null;

	private int lastCheckedTime = 0;
	private int lastCheckedMTime = 0;

	
	public GroundVehicle (double pose[], double s, double omega)
	{
		if (pose.length != 3)
			throw new IllegalArgumentException("newPos must be of length 3");

		synchronized (GroundVehicle.class) {
			vehicleID = totalNumVehicles;
			totalNumVehicles++;
		}

		x = pose[0]; 
		y = pose[1]; 
		theta = pose[2];

		xdot = s * Math.cos(theta);
		ydot = s * Math.sin(theta);
		thetadot = omega;

		clampPosition();
		clampVelocity();

	}

	public void addSimulator(Simulator sim)
	{
		this.sim = sim;
	}

	public int getVehicleID()
	{
		return vehicleID;
	}

	private void clampPosition() {
		x = Math.min(Math.max(x,0),200);
		y = Math.min(Math.max(y,0),100);
		theta = Math.min(Math.max(theta, -Math.PI), Math.PI);
		if (theta - Math.PI == 0 || Math.abs(theta - Math.PI) < 1e-6)
			theta = -Math.PI;
	}

	private void clampVelocity() {

		double velMagnitude = Math.sqrt(xdot*xdot+ydot*ydot);
		if (velMagnitude > 10.0) {
			xdot = 10.0 * xdot/velMagnitude;
			ydot = 10.0 * ydot/velMagnitude;
		}

		if (velMagnitude < 0.0) {
			/* Same logic as above. */ 

			xdot = 0.0;
			ydot = 0.0;
		}

		thetadot = Math.min(Math.max(thetadot, -Math.PI), Math.PI);	//changed to allow faster turns	
	}

	public synchronized double [] getPosition() {
		double[] position = new double[3];
		position[0] = x;
		position[1] = y;
		position[2] = theta;

		return position;

	}

	public synchronized double [] getVelocity() {
		double[] velocity = new double[3];
		velocity[0] = xdot;
		velocity[1] = ydot;
		velocity[2] = thetadot;

		return velocity;

	}

	public synchronized void setPosition(double[] newPos) {
		if (newPos.length != 3)
			throw new IllegalArgumentException("newPos must be of length 3");      

		x = newPos[0];
		y = newPos[1];
		theta = newPos[2];

		clampPosition();
	}

	public synchronized void setVelocity(double[] newVel) {
		if (newVel.length != 3)
			throw new IllegalArgumentException("newVel must be of length 3");      

		xdot = newVel[0];
		ydot = newVel[1];
		thetadot = newVel[2];		

		clampVelocity();
	}

	public synchronized void controlVehicle(Control c) {
		xdot = c.getSpeed() * Math.cos(theta);
		ydot = c.getSpeed() * Math.sin(theta);
		thetadot = c.getRotVel();

		clampVelocity();
	}

	public void run()
	{
		int currentTime = 0;
		int currentMTime = 0;

		while(currentTime < 100.0){
			synchronized(sim){
				currentTime = sim.getCurrentSec();
				currentMTime = sim.getCurrentMSec();

				while(lastCheckedTime == currentTime && lastCheckedMTime == currentMTime){
					try{
						sim.wait();
						currentTime = sim.getCurrentSec();
						currentMTime = sim.getCurrentMSec();
					}
					catch(java.lang.InterruptedException e){
						System.err.printf("Interupted " + e);
					}
				}

				sim.notifyAll();
			}


			advance(currentTime - lastCheckedTime, 
					currentMTime - lastCheckedMTime);

			lastCheckedTime = currentTime;
			lastCheckedMTime = currentMTime;

			synchronized(sim){
				if(sim.numVehicleToUpdate == 0) {
					System.err.println("ERROR: No of vehicles to update already 0\n");
					System.exit(-1);
				}

				sim.numVehicleToUpdate--;
				sim.notifyAll();
			}	
		}

	}

	public synchronized void advance(int sec, int msec)
	{
		double t = sec + msec * 1e-3;
		double s = Math.sqrt( xdot * xdot + ydot * ydot );

		if (Math.abs(thetadot) > 1e-3) { 
			double r = s/thetadot;

			double xc = x - r * Math.sin(theta);
			double yc = y + r * Math.cos(theta);

			theta = theta + thetadot * t;

			double rtheta = ((theta - Math.PI) % (2 * Math.PI));
			if (rtheta < 0) {	// Note that % in java is remainder, not modulo.
				rtheta += 2*Math.PI;
			}
			theta = rtheta - Math.PI;

			// Update    
			x = xc + r * Math.sin(theta);
			y = yc - r * Math.cos(theta);
			xdot = s * Math.cos(theta);
			ydot = s * Math.sin(theta);

		} else {			// Straight motion. No change in theta.
			x = x + xdot * t;
			y = y + ydot * t;
		}

		clampPosition();
		clampVelocity();
	}

	
}
