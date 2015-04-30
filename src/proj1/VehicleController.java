package proj1;
import java.util.concurrent.CountDownLatch;


public class VehicleController implements Runnable {

	public Simulator s;
	public GroundVehicle v;
	
	public int num_sides = 5;	
	private boolean stopTurning = true;
	private double startedTurning = 0;
	private int turnsMade = 0;
	
	private boolean realtime = false;
	private Object thread = null;
	public VehicleController(Simulator s, GroundVehicle v){
		this.s = s;
		this.v = v;
	}	
	
	public void run(){
		System.out.println("Controller started");
		double t = s.getSimulatorSec();
		double old_t = -1;
		
		while(true){
			// check if simulator is ready to let the vehicle make a new controller
			t = s.getSimulatorSec();
			if (t == old_t){
				try {
					if (!realtime){//!realtime){
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
			} else {
				double[] state = v.getPosition();
				Control c = this.getControl(t,state);
				v.controlVehicle(c);
				old_t = t;
			}
		}
	}
	
	public void addThread(Object t){
//		if (t instanceof javax.realtime.RealtimeThread){
			this.thread = t;
//		}
	}
	/**
	 * Changes the number of sides of the polygon in question
	 */
	public int setNumSides(int n){
		if (!(n < 3|| n > 10)){
			this.num_sides = n;
		}
		return Integer.valueOf(this.num_sides);
	}
	
	public Control getControl(double t, double[] state){
		if (t<0) return null;
		double n = Integer.valueOf(this.num_sides);
		double r = 50; // circle of diameter 50
		double sideLength = Math.sqrt(Math.pow(r, 2)*(2 - 2*Math.cos(Math.PI/n))); // formula from http://mathcentral.uregina.ca/QQ/database/QQ.09.06/s/dj1.html
		double turnAngle = Math.PI - (Math.PI*(n-2))/n; // exterior angle of a polygon forumula
		
		double speed = 6.0;
		double rot_vel = Math.PI/4;
		
		double t_per_side = sideLength/(speed);
		double t_to_turn = turnAngle/rot_vel; 
		
		boolean notFirstSide = t/t_per_side > 0.5; // if the current time isnt past the time for the first side, we're on first side
		boolean itsTimeToStartTurning = t%((this.turnsMade+1)*t_per_side - t_to_turn) < .05; //.05 is threshold value
		if (notFirstSide &&  (itsTimeToStartTurning || !this.stopTurning) ){
			if (this.startedTurning == 0){
				//System.out.println("Started turning. "+t+" .. need to turn "+Math.toDegrees(turnAngle)+" degrees. Will take "+t_to_turn+" seconds");
				this.startedTurning = t;
				this.stopTurning = false;
			} else if (t>=(this.startedTurning + t_to_turn)){
				this.startedTurning = 0;
				this.stopTurning = true;
				this.turnsMade += 1;
				//System.out.println("Stopped turning. "+t);
			}
			return new Control(speed,rot_vel); //control that is rotating if we're supposed to turn
		} else {
			return new Control(speed,0); //control that is not rotating if we're supposed to go straight
		}
	}
	
	public void isRealtime(boolean realtime){
		this.realtime = realtime;
	}
	
}
