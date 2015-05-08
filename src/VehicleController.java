import java.lang.IllegalArgumentException;
import java.util.ArrayList;

public class VehicleController extends Thread
{
	private Simulator sim;
	protected GroundVehicle gv;

	private int _lastCheckedTime = 0;
	private int _lastCheckedMTime = 0;

	protected static int totalNumControllers = 0;
	protected int controllerID = 0;
	private ArrayList<GroundVehicle> vehicles;
	private static final double THRESHOLD=0.2;


	public VehicleController(Simulator s, GroundVehicle v) throws IllegalArgumentException
	{
		if (s == null) {
			throw new IllegalArgumentException("No simulator specified.");
		}
		if (v == null) {
			throw new IllegalArgumentException("No vehicle specified.");
		}
		sim = s;
		gv = v;

		vehicles= new ArrayList<GroundVehicle>();

		synchronized (VehicleController.class) {
			controllerID = totalNumControllers;
			totalNumControllers++;
		}
	}

	public void run()
	{
		int currentTime = 0;
		int currentMTime = 0;

		while(currentTime < 100.0) {

			synchronized(sim) {
				currentTime = sim.getCurrentSec();
				currentMTime = sim.getCurrentMSec();

				while (_lastCheckedTime == currentTime && _lastCheckedMTime == currentMTime) {
					try {

						sim.wait(); // Wait for the simulator to notify
						currentTime = sim.getCurrentSec();
						currentMTime = sim.getCurrentMSec();
					} catch (java.lang.InterruptedException e) {
						System.err.printf("Interrupted " + e);
						System.exit(0);
					}
				}

				sim.notifyAll();
			}


			// Generate a new control
			Control nextControl = this.getControl();

			if (nextControl != null) {
				gv.controlVehicle(nextControl); 

			}

			//update the time of the last control
			_lastCheckedTime = currentTime;
			_lastCheckedMTime = currentMTime;

			synchronized(sim){
				if(sim.numControlToUpdate == 0 ) {
					//this should not already be zero - something is wrong
					System.err.println("ERROR: No of controllers to update already 0.\n");
					System.exit(-1);
				}
				sim.numControlToUpdate--;
				sim.notifyAll();
			}
		}
	}



	public Control getControl(){
		Control nextControl = null;
		nextControl= new Control(5,0);
		return nextControl;
	}

	public void setVehicles(ArrayList<GroundVehicle> vc){
		vehicles=vc;
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

	public boolean isCollision(){
		boolean collision=false;
		double[] myLocation= gv.getPosition();
		double myX=myLocation[0];
		double myY=myLocation[1];
		int myID= gv.getVehicleID();
		for (int i=0; i<vehicles.size(); i++){
			if (vehicles.get(i).getId()!=myID){
				double[] refPos=vehicles.get(i).getPosition();
				double refX=refPos[0];
				double refY=refPos[1];
				double distance= Math.sqrt(Math.pow(refX-myX, 2)+ Math.pow(refY-myY, 2));
				if (distance<=this.THRESHOLD){
					collision=true;
				}
				
			}
		}
		return collision;
	}
}