
//import javax.realtime.*;

public class RtTest{

	private static DisplayClient server = null;
	private static GroundVehicle g;
	
	public static void main(String argv[]){
		boolean realtime = false;
		if (argv.length < 2) {
		      System.err.println("Not enough arguments. Usage: RtTest -realtime <IP> [realtime]\n"+
					 "where <IP> is where DisplayServer is running");
		      System.exit(-1);
	    }		
		String host = argv[1];
	    	    
	    if (argv.length == 3){
	    	String rt2 = argv[2];
	    	if (rt2.toLowerCase().equals("realtime")){
	    		realtime = true;	    		
	    	} else {
	    		System.err.println("Invalid label. Usage: RtTest -realtime <IP> [realtime]\n"+
					 "where <IP> is where DisplayServer is running");
	    		System.exit(-1);
	    	}
	    } else if (argv.length > 3){
    		System.err.println("Too many args. Usage: RtTest -realtime <IP> [realtime]\n"+
					 "where <IP> is where DisplayServer is running");
    		System.exit(-1);
	    }
	    if (!realtime){
	    	System.out.println("Running non realtime version");
	    } else {
	    	System.out.println("Running realtime version");
	    }
	    server = new DisplayClient(host);
	    
	    
		Simulator mySim = new Simulator();
//		double x_init = 100*Math.random(); //center of board
//		double y_init = 100*Math.random(); //bottom of center of inscribed circle
//		double angle_init = 2*Math.PI*Math.random()-Math.PI; //first side direction based on degree of polynomial
//		double speed_init = 5.0*Math.random()+5; 
//		double omega_init = Math.PI/2*Math.random()-Math.PI/4;
		
		double x_init = 50;
		double y_init = 75;
		double angle_init = -1*Math.PI;
		double speed_init = 10;
		double omega_init = speed_init/25.0;
		
		g = new GroundVehicle(new double[]{x_init,y_init,Math.PI},speed_init*Math.cos(angle_init),speed_init*Math.sin(angle_init),omega_init);
		g.addSimulator(mySim);
		CircleController cc = new CircleController(mySim,g);
		cc.isRealtime(realtime);
		mySim.isRealtime(realtime);
		g.isRealtime(realtime);
		mySim.addVehicle(g);
		mySim.setServer(server);
		
		if (!realtime){
			new Thread(g).start();
			new Thread(cc).start();			
			mySim.run();
		} else {
//			int cost = 10;
//			
//			int nanos = 0;
//			long g_period = 3; // milliseconds
//			long cc_period = 5;
//			long s_period = 10; 
//			int min_priority = PriorityScheduler.instance().getMinPriority();
//			
//			System.out.println("Setting realtime params");
//			SchedulingParameters sched = new PriorityParameters(min_priority + 10);
//			RelativeTime r1 = new RelativeTime(g_period,nanos); 
//			RelativeTime r2 = new RelativeTime(cc_period,nanos);
//			RelativeTime r3 = new RelativeTime(s_period,nanos);
//			
//			AsyncHandler a11 = new AsyncHandler("overrun","groundvehicle");
//			AsyncHandler a12 = new AsyncHandler("overrun","groundvehicle");
//			PeriodicParameters p1 = new PeriodicParameters(
//					null, 									  //start time
//					r1, 									  //period
//					new RelativeTime(cost,nanos), 			  //cost
//					null, 									  //deadline, null if same as period
//					a11, //handler code obj for cost overrun (new javax.realtime.AsyncEventHandler())
//					a12); //handler code for missed deadlines //assign max priority to deadline TODO
//
//			AsyncHandler a21 = new AsyncHandler("overrun","controller");
//			AsyncHandler a22 = new AsyncHandler("deadline","controller"); 
//			PeriodicParameters p2 = new PeriodicParameters(
//					null, 									  //start time
//					r2, 									  //period
//					new RelativeTime(cost,nanos),  			  //cost
//					null, 									  //deadline, null if same as period
//					a21, //handler code obj for cost overrun
//					a22); //handler code for missed deadlines //assign max priority to deadline TODO
//			
//			AsyncHandler a31 = new AsyncHandler("overrun","simulator");
//			AsyncHandler a32 = new AsyncHandler("deadline","simulator");
//			PeriodicParameters p3 = new PeriodicParameters(
//					null, 									  //start time
//					r3, 									  //period
//					new RelativeTime(cost,nanos), 			  //cost
//					null, 									  //deadline, null if same as period
//					a31,  //handler code obj for cost overrun
//					a32);  //handler code for missed deadlines //assign max priority to deadline TODO
//			
//			System.out.println("Creating realtime threads");
//			RealtimeThread t1 = new RealtimeThread(sched, p1, null, null, null, g);
//			g.addThread(t1);
//			a11.addThread(t1);
//			a12.addThread(t1);
//						
//			RealtimeThread t2 = new RealtimeThread(sched, p2, null, null, null, cc);
//			cc.addThread(t2);
//			a21.addThread(t2);
//			a22.addThread(t2);
//						
//			RealtimeThread t3 = new RealtimeThread(sched, p3, null, null, null, mySim);
//			mySim.addThread(t3);
//			a31.addThread(t3);
//			a32.addThread(t3);
//			
//			System.out.println("Starting realtime threads");
//			t1.start();
//			t2.start();
//			t3.start();
		}
	}	
}
