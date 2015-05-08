
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
		double[] currentPos = this.gv.getPosition();
		if (Math.abs(currentPos[0] - endGV[0]) < this.threshold && Math.abs(currentPos[1] - endGV[1]) < this.threshold) {
			return new Control(0,0); //stop
		}
		return c;
	}



}
