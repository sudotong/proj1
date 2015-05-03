import java.lang.IllegalArgumentException;

public class VehicleController extends Thread
{
	private Simulator sim;
	protected GroundVehicle gv;

	private int _lastCheckedTime = 0;
	private int _lastCheckedMTime = 0;

	protected static int totalNumControllers = 0;
	protected int controllerID = 0;



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
			Control nextControl = this.getControl(currentTime, currentMTime);

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



	public Control getControl(int sec, int msec)
	{
		double controlTime = sec+msec*1E-3;

		Control nextControl = null;
		
		nextControl= new Control(5,0);

		return nextControl;
	}

}