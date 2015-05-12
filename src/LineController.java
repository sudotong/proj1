public class LineController extends VehicleController{
	private double[] endGV;
	private boolean stopped=false;

	private double threshold = .1;

	public LineController (double[] endGV, GroundVehicle gv, Simulator sim) throws IllegalArgumentException{
		super(sim,gv);
		if (endGV==null){
			throw new IllegalArgumentException("Null GroundVehicle Passed to LineController");
		}
		this.endGV=endGV;
	}

	public Control getControl(){
		if (stopped){
			return new Control(0,0);
		}
		Control c = null;
		if (this.isCollision()){
			c=this.collisionControl();

		}
		if (c==null){
			c= new Control(5,0);
			double[] currentPos = this.gv.getPosition();
			if (Math.abs(currentPos[0] - endGV[0]) < this.threshold && Math.abs(currentPos[1] - endGV[1]) < this.threshold) {
				c= new Control(0,0); //stop
				stopped=true;
				sim.stoppedVehicle();
			}
		}
		return c;
	}

}
