import java.util.*;

public class Simulator extends Thread
{
	private int currentSec = 0;
	private int currentMSec = 0;

	//List of GroundVehicle
	protected List<GroundVehicle> groundVehicleList;
	public int numControlToUpdate = 0;
	public int numVehicleToUpdate = 0;

	private static int boxNumber=1; //what box we're writing in

	private DisplayClient displayClient;


	public Simulator(DisplayClient displayClient){
		groundVehicleList = new ArrayList<GroundVehicle>();	
		boxNumber=1;

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

	public void newWord(){
		if (boxNumber<=20){
			boxNumber=21;
		}
		else if (boxNumber<=40 && boxNumber>=21){
			boxNumber=41;
		}
		else if (boxNumber<=60 && boxNumber>=41){
			boxNumber=61;
		}
		else if (boxNumber<=80 && boxNumber>=61){
			boxNumber=81;
		}
		else if (boxNumber<=100 && boxNumber>=81){
			throw new IllegalArgumentException("too many words");
		}

	}

	public double[] getBoxCoords(int box){
		double[] ret= new double[2];
		double x=0;
		double y=0;
		int g=0; //count by 20s
		//find y value
		if (box<21)
			y=80;
		else if (box<41 && box>20){
			y=60;
			g=1;}
		else if (box<61 && box>40){
			y=40;
			g=2;}
		else if (box<81 && box>60){
			y=20;
			g=3;}
		else if (box>=81) {
			y=0; 
			g=4;
		}

		//find x value
		x=((box-g*20)-1)*20;

		ret[0]=x;
		ret[1]=y;
		return ret;
	}

	public synchronized void addGroundVehicle(GroundVehicle gv){
		groundVehicleList.add(gv);
		numVehicleToUpdate++;
		numControlToUpdate++;
	}

	public void run()
	{
		displayClient.clear();
		double gvX[] = new double[groundVehicleList.size()];
		double gvY[] = new double[groundVehicleList.size()];
		double gvTheta[] = new double[groundVehicleList.size()];
		displayClient.traceOn();

		while (currentSec < 100) {
			// Update display
			for(int i=0;i < groundVehicleList.size(); i++){
				GroundVehicle currVehicle = groundVehicleList.get(i);
				double [] position = currVehicle.getPosition();	
				gvX[i] = position[0];
				gvY[i] = position[1];
				gvTheta[i] = position[2];
			}
			displayClient.update(groundVehicleList.size(),gvX,gvY,gvTheta);

			// Advance the clock
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

	public static void main (String [] args) throws InterruptedException{

		if (args.length <=1) {
			System.err.println("Not enough arguments given. \n"
					+ "Please input words and then ip address");
			System.exit(-1);
		}
		else if (args.length>6){
			System.err.println("Too mant arguments given. \n"
					+ "Please input no more than 5 words and then ip address");
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

		ArrayList<GroundVehicle> allVehicles= new ArrayList<GroundVehicle>();
		ArrayList<VehicleController> allControllers= new ArrayList<VehicleController>();

		//find words longer than 10 characters and deal with them
		for (int i=numberOfWords-1; i>=0; i--){
			String word= args[i];
			if (word.length()>20){System.err.println("You entered a word with more than 20 characters /n"
					+ "there is not enough space to write it");
			System.exit(-1);}
			if (word.length()>10){
				if (i==4 || numberOfWords==5){System.err.println("You entered a word with more than 10 characters /n"
						+ "there is not enough space to write it");
				System.exit(-1);}
				args[i]=word.substring(0, Math.min(word.length(), 9));
				args[i]=args[i].concat("-");
				args[i+1]=word.substring(9,word.length());
				numberOfWords=args.length-1;
			}
		}
		
		//iterate through words
		for (int i=0; i<numberOfWords; i++){
			String word= args[i];

			//iterate through letters
			for (int j = 0; j < word.length(); j++){
				char let = word.charAt(j); 
				char letter=Character.toUpperCase(let);
				ArrayList<Instruction> letterInstructionArray= letterGetter.get(letter);
				int numberOfGVs= letterInstructionArray.size();
				double[] boxCoords=sim.getBoxCoords(boxNumber);
				ArrayList<GroundVehicle> gvsInBox= new ArrayList<GroundVehicle>(); 
				ArrayList<VehicleController> controllersInBox= new ArrayList<VehicleController>();

				//iterate through vehicles for each letter
				for (int k=0; k<numberOfGVs; k++){
					Instruction instruct= letterInstructionArray.get(k);
					//get info out of instructions and calculate end and start points 
					String type=instruct.getType();
					double[] start= instruct.getStart();
					double[] end=instruct.getEnd();
					double rotVel=instruct.getRotVel();
					double startTheta= instruct.getStartTheta();
					double[] startGV=new double[2];
					double[] endGV=new double[2];
					startGV[0]= boxCoords[0]+start[0];
					startGV[1]= boxCoords[1]+start[1];
					endGV[0]= boxCoords[0]+end[0];
					endGV[1]= boxCoords[1]+end[1];

					double s=10; //max speed for all vehicles- may need to adjust for circles

					double[] pose={startGV[0], startGV[1],startTheta};

					GroundVehicle gv= new GroundVehicle(pose, s, rotVel);
					//add each vehicle to an arraylist of vehicles in that box
					gvsInBox.add(gv);
					//add each vehicle to an arraylist of all vehicles
					allVehicles.add(gv);
					sim.addGroundVehicle(gv);
					gv.addSimulator(sim);

					if (type.equals("CIRCLE")){
						CircleController c= new CircleController(startGV, endGV, rotVel, sim, gv);
						controllersInBox.add(c);
						allControllers.add(c);
					}
					else if (type.equalsIgnoreCase("LINE")){
						LineController l= new LineController(startGV, endGV, gv, sim);
						controllersInBox.add(l);
						allControllers.add(l);
					}
					//tell each controller about the other vehicles in the box
					for (int m=0; m<controllersInBox.size(); m++){
						controllersInBox.get(m).setVehicles(gvsInBox);
					}
				}

				boxNumber++;
			}
			if(i!=4){
				sim.newWord();}
		}

		//start all threads
		for (int i=0; i<allVehicles.size(); i++){
			allVehicles.get(i).start();
			allControllers.get(i).start();
		}
		sim.start();
	}
}