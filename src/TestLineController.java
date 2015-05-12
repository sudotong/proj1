import static org.junit.Assert.assertEquals;

import org.junit.*;

public class TestLineController {

	/*DisplayServer must be running for these tests to work
	 */
	
	//test null endGV array in constructor
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor() {
		GroundVehicle gv= new GroundVehicle(new double[]{50,50,0}, 5,0);
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);
		double[] endgv2= null;
		LineController lc= new LineController(endgv2, gv, sim);
		
	}
	
	//test getControl always returns zero rotational velocity
		@Test
		public void testGetControl() {
			GroundVehicle gv= new GroundVehicle(new double[]{50,50,0}, 5,0);
			DisplayClient dp= new DisplayClient("localhost");
			Simulator sim = new Simulator(dp);
			double[] endgv2= {80,80};
			LineController lc= new LineController(endgv2, gv, sim);
			Control c=lc.getControl();
			assertEquals(c.getRotVel(), 0, 1e-9);
			
		}
	
	
	public static void main(String[] args){
		org.junit.runner.JUnitCore.main(TestLineController.class.getName());
	}

}
