import java.lang.IllegalArgumentException;
import java.util.Random;

public class GroundVehicle extends Thread
{
	private double _x, _y, _theta;
	private double _dx, _dy, _dtheta;

	private static int totalNumVehicles = 0;
	private int vehicleID;

	private Simulator _s = null;

	private int _lastCheckedTime = 0;
	private int _lastCheckedMTime = 0;

	private Random r;


	public GroundVehicle (double pose[], double s, double omega)
	{
		if (pose.length != 3)
			throw new IllegalArgumentException("newPos must be of length 3");

		synchronized (GroundVehicle.class) {
			vehicleID = totalNumVehicles;
			totalNumVehicles++;
		}

		_x = pose[0]; 
		_y = pose[1]; 
		_theta = pose[2];

		_dx = s * Math.cos(_theta);
		_dy = s * Math.sin(_theta);
		_dtheta = omega;

		clampPosition();
		clampVelocity();

		r = new Random();
	}

	public void addSimulator(Simulator sim)
	{
		_s = sim;
	}

	public int getVehicleID()
	{
		return vehicleID;
	}

	private void clampPosition() {
		_x = Math.min(Math.max(_x,0),200);
		_y = Math.min(Math.max(_y,0),100);
		_theta = Math.min(Math.max(_theta, -Math.PI), Math.PI);
		if (_theta - Math.PI == 0 || Math.abs(_theta - Math.PI) < 1e-6)
			_theta = -Math.PI;
	}

	private void clampVelocity() {

		double velMagnitude = Math.sqrt(_dx*_dx+_dy*_dy);
		if (velMagnitude > 10.0) {
			_dx = 10.0 * _dx/velMagnitude;
			_dy = 10.0 * _dy/velMagnitude;
		}

		if (velMagnitude < 0.0) {
			/* Same logic as above. */ 

			_dx = 0.0;
			_dy = 0.0;
		}

		_dtheta = Math.min(Math.max(_dtheta, -Math.PI), Math.PI);	//changed to allow faster turns	
	}

	public synchronized double [] getPosition() {
		double[] position = new double[3];
		position[0] = _x;
		position[1] = _y;
		position[2] = _theta;

		return position;

	}

	public synchronized double [] getVelocity() {
		double[] velocity = new double[3];
		velocity[0] = _dx;
		velocity[1] = _dy;
		velocity[2] = _dtheta;

		return velocity;

	}

	public synchronized void setPosition(double[] newPos) {
		if (newPos.length != 3)
			throw new IllegalArgumentException("newPos must be of length 3");      

		_x = newPos[0];
		_y = newPos[1];
		_theta = newPos[2];

		clampPosition();
	}

	public synchronized void setVelocity(double[] newVel) {
		if (newVel.length != 3)
			throw new IllegalArgumentException("newVel must be of length 3");      

		_dx = newVel[0];
		_dy = newVel[1];
		_dtheta = newVel[2];		

		clampVelocity();
	}

	public synchronized void controlVehicle(Control c) {
		_dx = c.getSpeed() * Math.cos(_theta);
		_dy = c.getSpeed() * Math.sin(_theta);
		_dtheta = c.getRotVel();

		clampVelocity();
	}

	public void run()
	{
		int currentTime = 0;
		int currentMTime = 0;

		while(currentTime < 100.0){
			synchronized(_s){
				currentTime = _s.getCurrentSec();
				currentMTime = _s.getCurrentMSec();

				while(_lastCheckedTime == currentTime && _lastCheckedMTime == currentMTime){
					try{
						_s.wait();
						currentTime = _s.getCurrentSec();
						currentMTime = _s.getCurrentMSec();
					}
					catch(java.lang.InterruptedException e){
						System.err.printf("Interupted " + e);
					}
				}

				_s.notifyAll();
			}


			advanceNoiseFree(currentTime - _lastCheckedTime, 
					currentMTime - _lastCheckedMTime);

			_lastCheckedTime = currentTime;
			_lastCheckedMTime = currentMTime;

			synchronized(_s){
				if(_s.numVehicleToUpdate == 0) {
					System.err.println("ERROR: No of vehicles to update already 0\n");
					System.exit(-1);
				}

				_s.numVehicleToUpdate--;
				_s.notifyAll();
			}	
		}

	}

	public static double normalizeAngle(double theta)
	{
		double rtheta = ((theta - Math.PI) % (2 * Math.PI));
		if (rtheta < 0) {	// Note that % in java is remainder, not modulo.
			rtheta += 2*Math.PI;
		}
		return rtheta - Math.PI;
	}

	public synchronized void advance(int sec, int msec)
	{
		double t = sec + msec * 1e-3;

		double[] newPose = new double[3];
		double errc = Math.sqrt(0.2) * r.nextGaussian();
		double errd = Math.sqrt(0.1) * r.nextGaussian();

		newPose[0] = _x + _dx * t + errd * Math.cos(_theta) - errc * Math.sin(_theta);
		newPose[1] = _y + _dy * t + errd * Math.sin(_theta) + errc * Math.cos(_theta);
		newPose[2] = _theta + _dtheta * t;
		newPose[2] = normalizeAngle(newPose[2]);

		double[] newVel = new double[3];
		double s = Math.sqrt(Math.pow(_dx, 2) + Math.pow(_dy, 2));
		newVel[0] = s * Math.cos(_theta);
		newVel[1] = s * Math.sin(_theta);
		newVel[2] = _dtheta;

		setPosition(newPose);
		setVelocity(newVel);
	}    

	public synchronized void advanceNoiseFree(int sec, int msec)
	{
		double t = sec + msec * 1e-3;
		double s = Math.sqrt( _dx * _dx + _dy * _dy );

		if (Math.abs(_dtheta) > 1e-3) { 
			double r = s/_dtheta;

			double xc = _x - r * Math.sin(_theta);
			double yc = _y + r * Math.cos(_theta);

			_theta = _theta + _dtheta * t;

			double rtheta = ((_theta - Math.PI) % (2 * Math.PI));
			if (rtheta < 0) {	// Note that % in java is remainder, not modulo.
				rtheta += 2*Math.PI;
			}
			_theta = rtheta - Math.PI;

			// Update    
			_x = xc + r * Math.sin(_theta);
			_y = yc - r * Math.cos(_theta);
			_dx = s * Math.cos(_theta);
			_dy = s * Math.sin(_theta);

		} else {			// Straight motion. No change in theta.
			_x = _x + _dx * t;
			_y = _y + _dy * t;
		}

		clampPosition();
		clampVelocity();
	}

	
}
