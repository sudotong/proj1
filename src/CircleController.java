public class CircleController extends VehicleController{
	private double[] startGV;
	private double[] endGV;
	private double rotVel;
	private final double threshold = 0.2;
	private boolean skipFirstMatch = false;
	private int skipcount=0;
	private boolean stopped=false;

	public CircleController(double[] startGV, double[] endGV, double rotVel, Simulator s, GroundVehicle v){
		super(s,v);
		this.startGV=startGV;
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
			//System.out.println(distance);
			//System.out.println(currentPos[0] +" "+ (currentPos[1]-80));
			if (distance<this.threshold) {
				if (!skipFirstMatch){
					c= new Control(0,0); //stop
					stopped=true;
				} 
				else {
					skipcount++;
					if (skipcount>2){skipFirstMatch = false;}
				}
			}

		}

		return c;
	}


	public Control collisionControl(){
		int min=50;
		int max=150;
		int threshold= min + (int)(Math.random() * ((max - min) + 1));
		Control c=null;
		if (this.numCollisions< threshold){
			c= new Control(0,0);
		}
		return c;
	}

}
