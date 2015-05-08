public class CircleController extends VehicleController{
	double[] startGV;
	double[] endGV;
	double rotVel;
	final double threshold = .5;
	private boolean skipFirstMatch = false;
	
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
		Control c = new Control(10,this.rotVel);
		double[] currentPos = this.gv.getPosition();
		if (Math.abs(currentPos[0] - endGV[0]) < this.threshold && Math.abs(currentPos[1] - endGV[1]) < this.threshold) {
			if (!skipFirstMatch){
				return new Control(0,0); //stop
			} else {
				if (Math.abs(currentPos[0] - endGV[0]) < this.threshold && Math.abs(currentPos[1] - endGV[1]) > this.threshold){
					skipFirstMatch = false;
				}
			}
		}
		return c;
	}

}
