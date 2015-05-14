import org.junit.*;


public class TestCircleController {

	/*DisplayServer must be running for these tests to work
	 */
	
	//test null endGV array in constructor
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor() {
		GroundVehicle gv= new GroundVehicle(new double[]{50,50,0}, 5,0);
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);
		double[] endgv2= null;
		double[] startgv2={20,20};
		double rotvel=1;
		@SuppressWarnings("unused")
		CircleController cc= new CircleController(startgv2, endgv2, rotvel, sim, gv);
		
	}
	
	//test null startGV array in constructor
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor2() {
		GroundVehicle gv= new GroundVehicle(new double[]{50,50,0}, 5,0);
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);
		double[] endgv2= {30,30};
		double[] startgv2=null;
		double rotvel=1;
		@SuppressWarnings("unused")
		CircleController cc= new CircleController(startgv2, endgv2, rotvel, sim, gv);
		
	}
	
	//test zero rotVel in constructor
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor3() {
		GroundVehicle gv= new GroundVehicle(new double[]{50,50,0}, 5,0);
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);
		double[] endgv2= {30,30};
		double[] startgv2={20,20};
		double rotvel=0;
		@SuppressWarnings("unused")
		CircleController cc= new CircleController(startgv2, endgv2, rotvel, sim, gv);
		
	}
	
	
	public static void main(String[] args){
		org.junit.runner.JUnitCore.main(TestCircleController.class.getName());
	}
}
