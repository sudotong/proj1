package proj1;

public class Control {
	
	final private double s;
	final private double omega;
	
	Control(double s, double omega){
		this.s = Math.min(Math.max(s, 5),10);
		this.omega = Math.min(Math.max(omega,-1*Math.PI),Math.PI);
	}
	
	double getSpeed(){
		return Double.valueOf(s);
	}

	double getRotVel(){		
		return Double.valueOf(this.omega);
	}
}
