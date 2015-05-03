
public class LineController extends VehicleController{
	private double[] startGV;
	private double[] endGV;


	public LineController(double[] startGV, double[] endGV, GroundVehicle gv, Simulator sim) {
		super(sim,gv);
		this.startGV=startGV;
		this.endGV=endGV;
	}

}
