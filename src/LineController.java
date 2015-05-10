public class LineController extends VehicleController{
	private double[] startGV;
	private double[] endGV;

	private double threshold = .5;

	public LineController(double[] startGV, double[] endGV, GroundVehicle gv, Simulator sim) {
		super(sim,gv);
		this.startGV=startGV;
		this.endGV=endGV;
	}

	public Control getControl(){
		Control c = null;
		if (this.isCollision()){
			c=this.collisionControl();

		}
		if (c==null){
			c= new Control(5,0);
			double[] currentPos = this.gv.getPosition();
			if (Math.abs(currentPos[0] - endGV[0]) < this.threshold && Math.abs(currentPos[1] - endGV[1]) < this.threshold) {
				c= new Control(0,0); //stop
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
