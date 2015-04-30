package proj1;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Simulator implements Runnable{
	
	private static DisplayClient server = null;
	private static List<GroundVehicle> vehicles = Collections.synchronizedList(new ArrayList<GroundVehicle>()); /* Shared resource */
	public CountDownLatch latch = new CountDownLatch(vehicles.size()); /* Shared resource */
	public CountDownLatch vehicleLatch = new CountDownLatch(vehicles.size());
	
	private static GroundVehicle g;
	protected NumberFormat format = new DecimalFormat("#####.##");
	
	public double time = 0;
	public double time_ms = 0;
	private final double endingTime = 100.0;
	private boolean realtime = false;
	
	final double increment_s = 0;
	final double increment_ms = 100; //used to be .1 which is 100 microseconds
	
	private Object thread = null;
	public Simulator(){
		return;
	}
	
	private void addGroundVehicle(GroundVehicle gv){
		/* Conditional critical region */
		vehicles.add(gv);
		gv.addSimulator(this);
	}
	
	public void addVehicle(GroundVehicle gv){
		/* Conditional critical region */
		if (vehicles.size() < 20){
			this.addGroundVehicle(gv);
			// TODO do we need this?
//			FollowingController fc = new FollowingController(this,gv,g);
//			new Thread(gv).start();
//			new Thread(fc).start();
		}
	}
	
	/**
	 * Returns the seconds component of the current Simulator clock
	 */
	public double getCurrentSec(){
		return Double.valueOf(this.time);
	}
	
	public double getSimulatorSec(){
		return this.getCurrentSec() + this.getCurrentMSec()/1000;
	}
	
	/**
	 * Returns the milliseconds seconds component of the current Simulator clock
	 * @return
	 */
	public double getCurrentMSec() {
		return Double.valueOf(this.time_ms);
	}
	
	public void addThread(Object t){
		if (t instanceof javax.realtime.RealtimeThread){
			this.thread = t;
		}
	}
	
	public void isRealtime(boolean realtime){
		this.realtime = realtime;
	}
	/**
	 * Sets the Simulator clock to be 0 seconds, 0 milliseconds.
	 * Updates the state of the GroundVehicle and applies controls to it as needed
	 * Print to standard out the current time of the simulator, the current x, y position of 
	 * the GroundVehicle and its orientation.
	 * Continues to increment the Simulator clock by 10 milliseconds
	 */
	public void run(){
		System.out.println("Simulator started");
		synchronized(this){
			this.time = 0;
			this.time_ms = 0;
		}
		server.clear();
		server.traceOn();
		
//		double old_t = -1; //sec
		double firstTime = this.getRealTimeSeconds();  //sec
//		double t = realtime ? this.getRealTimeSeconds() : ((double) this.getCurrentSec() + ((double) this.getCurrentMSec())/1000); //sec  
		double lastTime = this.getRealTimeSeconds();; //sec
		double totalElapsed = 0; //sec
		while (totalElapsed < this.endingTime){ //sec
			double nowTime = this.getRealTimeSeconds(); //msec
			double elapsed = nowTime-lastTime; //sec
			totalElapsed = nowTime-firstTime; //sec
			if (elapsed < (this.increment_ms/1000 + this.increment_s)){ //sec
				if (!realtime){
					try{
						Thread.sleep(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					if (this.thread != null){
						((javax.realtime.RealtimeThread) this.thread).waitForNextPeriod();
					} else {
						System.err.println("No valid thread to wait for.");
					}
				}
			} else {
				//if vehicles & control are done updating, update the time
				synchronized(this){
					this.time += elapsed;
//					t = (this.time + (this.time_ms)/1000); //sec
//					System.out.println("t: "+(this.time+(this.time_ms/1000)));
				}
				if (lastTime != nowTime){
					updateServer();
				}
				lastTime = nowTime;
			}
		}
	}
	
	public static void main(String argv[]){
		if (argv.length <= 1) {
		      System.err.println("Usage: Simulator <number of vehicles> <hostname>\n"+
					 "where <hostname> is where DisplayServer is running and <number of vehicles> is between 1 and 20 (inclusive)");
		      System.exit(-1);
		    }
		Double numVehicles = Double.parseDouble(argv[0]);
		if (numVehicles < 1 || numVehicles > 20){
			System.err.println("Usage: Simulator <number of vehicles> <hostname>\n"+
					 "where <hostname> is where DisplayServer is running and <number of vehicles> is between 1 and 20 (inclusive)");
		      System.exit(-1);
		}
	    String host = argv[1];
	    server = new DisplayClient(host);
		
		Simulator mySim = new Simulator();
		
		double x_init = 100*Math.random(); //center of board
		double y_init = 100*Math.random(); //bottom of center of inscribed circle
		double angle_init = 2*Math.PI*Math.random()-Math.PI; //first side direction based on degree of polynomial
		double speed_init = 5.0*Math.random()+5; 
		double omega_init = Math.PI/2*Math.random()-Math.PI/4;
		
		g = new GroundVehicle(new double[]{x_init,y_init,Math.PI},speed_init*Math.cos(angle_init),speed_init*Math.sin(angle_init),omega_init);
		g.addSimulator(mySim);
		CircleController rc = new CircleController(mySim,g);
		mySim.addGroundVehicle(g);
		new Thread(g).start();
		new Thread(rc).start();
		
		mySim.run();
	}
	public void setServer(DisplayClient serv){
		server = serv;
	}
	private void updateServer(){
		double gvX[] = new double[vehicles.size()];
	    double gvY[] = new double[vehicles.size()];
	    double gvTheta[] = new double[vehicles.size()];
	    for (int i = 0; i < vehicles.size(); i++) {
	    	double[] pose = vehicles.get(i).getPosition();
	    	gvX[i] = pose[0];
	    	gvY[i] = pose[1];
	    	gvTheta[i] = pose[2];
	    }
	    server.update(vehicles.size(), gvX, gvY, gvTheta);
	    
	    int numVehicles = vehicles.size();
	    StringBuffer message = new StringBuffer();
	    message.append(""+(this.time+(this.time_ms/1000)));
	    message.append(" ");
	    for (int i = 0; i < numVehicles; i++) {
	      message.append(format.format(gvX[i])+" "+format.format(gvY[i])+" "+
			     format.format(gvTheta[i])+" ");
	    }
	    message.append(""+AsyncHandler.totalDeadlines);
	    message.append(" ");
	    message.append(""+AsyncHandler.totalOverruns);
	    System.out.println(message);
	    
	}
	
	private double getRealTimeSeconds(){ // TODO
		return realtime ? (double) javax.realtime.Clock.getRealtimeClock().getTime().getMilliseconds()/1000.0 : (double) System.currentTimeMillis()/1000.0;
		// ClockReference
		// javax.realtime.Clock rtclock = javax.realtime.Clock.getRealtimeClock();
		// javax.realtime.AbsoluteTime startTime = rtclock.getTime();
		// startTime.getMilliseconds();
	}
	
}
