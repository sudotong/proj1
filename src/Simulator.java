import java.util.*;

public class Simulator extends Thread
{
	private int currentSec = 0;
	private int currentMSec = 0;

	//List of GroundVehicle
	protected List<GroundVehicle> groundVehicleList;
	public int numControlToUpdate = 0;
	public int numVehicleToUpdate = 0;
	
	private int boxNumber=1; //what box we're writing in

	private DisplayClient displayClient;


	public Simulator(DisplayClient displayClient){
		groundVehicleList = new ArrayList<GroundVehicle>();	

		if(displayClient ==null){
			throw new IllegalArgumentException("Invalid Display client object");
		}
		this.displayClient = displayClient;
	}

	public synchronized int getCurrentSec() {
		int sec=currentSec;
		return sec;
	}

	public synchronized int getCurrentMSec() {
		int mSec=currentMSec;
		return mSec;
	}

	public void advanceClock() {
		currentMSec += 10;
		if (currentMSec >= 1e3) {
			currentMSec -= 1e3;
			currentSec ++;
		}
	}

	public synchronized void addGroundVehicle(GroundVehicle gv){
		groundVehicleList.add(gv);
		//		System.out.printf("---------Adding Ground Vehicle-----------\n");
		for(int i=0;i < groundVehicleList.size(); i++){
			GroundVehicle mgv = groundVehicleList.get(i);
			double position[] = mgv.getPosition();
			//			System.out.printf("%d : %f,%f,%f \n", mgv.getVehicleID(),
			//			position[0], position[1], position[2]);
		}
		numVehicleToUpdate++;
		numControlToUpdate++;
	}

	public void run()
	{
		int lastUpdateSec = currentSec;
		int lastUpdateMSec = currentMSec;

		displayClient.clear();
		double gvX[] = new double[groundVehicleList.size()];
		double gvY[] = new double[groundVehicleList.size()];
		double gvTheta[] = new double[groundVehicleList.size()];
		displayClient.traceOn();

		while (currentSec < 100) {

			int deltaSec = currentSec - lastUpdateSec;
			int deltaMSec = currentMSec - lastUpdateMSec;

			if (deltaMSec < 0) {
				deltaMSec += 1e3;
				deltaSec -= 1;
			}

			// Update display
			for(int i=0;i < groundVehicleList.size(); i++){
				GroundVehicle currVehicle = groundVehicleList.get(i);
				double [] position = currVehicle.getPosition();	
				double [] velocity = currVehicle.getVelocity();	
				gvX[i] = position[0];
				gvY[i] = position[1];
				gvTheta[i] = position[2];
			}
			displayClient.update(groundVehicleList.size(),gvX,gvY,gvTheta);

			// Advance the clock
			lastUpdateSec = currentSec;
			lastUpdateMSec = currentMSec;
			synchronized(this) {
				advanceClock();
				notifyAll();
			}

			// Wait while everything is updated
			synchronized(this) {
				try {
					while (numVehicleToUpdate > 0 || numControlToUpdate > 0) {
						wait();

					}
				} catch (java.lang.InterruptedException e) {
					System.err.printf("Interrupted " + e);
				}
				numControlToUpdate = groundVehicleList.size();
				numVehicleToUpdate = groundVehicleList.size();
				notifyAll();
			}

		}
		displayClient.traceOff();
	}

	public static void main (String [] args) throws InterruptedException
	{
		if (args.length <=1) {
			System.err.println("Not enough arguments given. /n"
					+ "Please input words and then ip address");
			System.exit(-1);
		}
	
		int numberOfArguments= args.length;
		
		//Create display client with ip address
		String host = args[numberOfArguments-1];
		DisplayClient dpClient = new DisplayClient(host);
		
		//create simulator with displayclient
		Simulator sim = new Simulator(dpClient);
		
		//get number of words
		int numberOfWords=numberOfArguments-1;
		
		Letters letterGetter= new Letters();
		
		for (int i=0; i<numberOfWords; i++){
			String word= args[i];
			for (int j = 0; i < word.length(); j++){
			    char let = word.charAt(j); 
			    char letterChar=Character.toUpperCase(let);
			    String letter= letterChar;
			    ArrayList<Instruction> letterInstruction= letterGetter.get(letter);
		}
		
		

		sim.start();
	}
}
