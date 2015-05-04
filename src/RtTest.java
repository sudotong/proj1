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
		}
	}	
}
