public class CircleController extends VehicleController{
	private double[] endGV;
	private double rotVel;
	private final double threshold = 0.2;
	private boolean skipFirstMatch = false;
	private int skipcount=0;
	private boolean stopped=false;

	public CircleController(double[] startGV, double[] endGV, double rotVel, Simulator s, GroundVehicle v) throws IllegalArgumentException{
		super(s,v);
		if(startGV==null){ throw new IllegalArgumentException("Null startGV array passed to CircleController");
		}
		if(endGV==null) { throw new IllegalArgumentException("Null endGV array passed to CircleController");
		}
		if(rotVel==0) { throw new IllegalArgumentException("Zero rotational velocity passed to CircleController");
		}
		this.endGV=endGV;
		this.rotVel=rotVel;
		if (startGV[0] == endGV[0] && startGV[1] == endGV[1]){
			skipFirstMatch = true;
		}
	}	

	public Control getControl(){
		if (stopped){
			return new Control(0,0);
		}
		Control c= null;
		if (this.isCollision()){
			c= this.collisionControl();
		}
		if (c==null){
			double[] currentPos = this.gv.getPosition();
			c = new Control(10,this.rotVel);
			double myX=currentPos[0];
			double myY=currentPos[1];
			double distance=Math.sqrt(Math.pow(myX-endGV[0],2)+Math.pow(myY-endGV[1],2));
			if (distance<this.threshold) {
				if (!skipFirstMatch){
					c= new Control(0,0); //stop
					stopped=true;
					sim.stoppedVehicle();
				} 
				else {
					skipcount++;
					if (skipcount>2){skipFirstMatch = false;}
				}
			}
		}
		return c;
	}
}
