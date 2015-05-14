import static org.junit.Assert.*;

public class TestSimulator {
	/*Requires that DisplayServer is running!
	 * 
	 */

	@org.junit.Test
	public void constructor() {
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);
		assertTrue(sim.getCurrentSec() == 0);
		assertTrue(sim.getCurrentMSec() == 0);
	}

	/**
	 * Constructs a Simulator Object and tests if the value returned by the
	 * function "getCurrentMsec()" in the Simulator class is 0 before method
	 * "run()" is called.
	 */
	@org.junit.Test
	public void getMsecTest() {
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);
		assertEquals(0, sim.getCurrentMSec(), 1e-9);
	}

	/**
	 * Constructs a Simulator Object and tests if the value returned by the
	 * function "getCurrentSec()" in the Simulator class is 0 before method
	 * "run()" is called.
	 */
	@org.junit.Test
	public void getsecTest() {
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);
		assertEquals(0, sim.getCurrentSec(), 1e-9);
	}

	/**
	 * Tests if the "increaseTime()" updates the simulator clock increasing it
	 * by 100 milliseconds.
	 */
	@org.junit.Test
	public void increaseTimeTest() {
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);
		sim.advanceClock();

		// Test if clock is increased by deltaT milliseconds when is called.
		double actualTime = sim.getCurrentSec() + sim.getCurrentMSec() / 1000.0;
		double expectedTime = 10 / 1000.0;
		assertEquals(expectedTime, actualTime, 1e-9);
	}

	/**
	 * Test if variable sec is increased by 1 and Msec = 0 when calling
	 * "increaseTime()" makes the variable Msec = 1000
	 */
	@org.junit.Test
	public void increaseTimeTest2() {
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);

		for (int i = 0; i <(int)( (double)1000) / 10; i++) {
			sim.advanceClock();
		}

		assertTrue(sim.getCurrentSec() == 1);
		assertTrue(sim.getCurrentMSec() == 0);
	}

	/**
	 * This method tests if the addVehicle method adds correctly the
	 * GroundVehicles to the list.
	 */
	@org.junit.Test
	public void testAddVehicle() {
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);
		double[] pos = { 0, 0, 0 };
		GroundVehicle gv = new GroundVehicle(pos, 1, 0);
		GroundVehicle gv2 = new GroundVehicle(pos, 1, 1);
		sim.addGroundVehicle(gv);
		sim.addGroundVehicle(gv2);
		assertEquals(gv, sim.groundVehicleList.get(0));
		assertEquals(gv2, sim.groundVehicleList.get(1));
	}

	//test that constructor throws error on null display client
	@org.junit.Test(expected=IllegalArgumentException.class)
	public void testConstructor() {
		DisplayClient dc= null;
		@SuppressWarnings("unused")
		Simulator sim = new Simulator(dc);
	}

	//test that addGroundVehicle increments counters
	@org.junit.Test
	public void testAddVehicle2() {
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);
		double[] pos = { 0, 0, 0 };
		GroundVehicle gv = new GroundVehicle(pos, 1, 0);
		GroundVehicle gv2 = new GroundVehicle(pos, 1, 1);
		sim.addGroundVehicle(gv);
		sim.addGroundVehicle(gv2);
		assertEquals(sim.numControlToUpdate, 2, 1e-9);
		assertEquals(sim.numVehicleToUpdate, 2, 1e-9);
		assertEquals(sim.numVehiclesNotStopped, 2, 1e-9);
	}

	//test that stoppedVehicle reduces count of vehicles not stopped
	@org.junit.Test
	public void testStoppedVehicle() {
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);
		double[] pos = { 0, 0, 0 };
		GroundVehicle gv = new GroundVehicle(pos, 1, 0);
		sim.addGroundVehicle(gv);
		sim.stoppedVehicle();
		assertEquals(sim.numVehiclesNotStopped, 0, 1e-9);
	}

	//test get box coords
	@org.junit.Test(expected=IllegalArgumentException.class)
	public void testGetBoxCoords() {
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);
		@SuppressWarnings("unused")
		double[] test=sim.getBoxCoords(0);
	}
	@org.junit.Test(expected=IllegalArgumentException.class)
	public void testGetBoxCoords2() {
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);
		@SuppressWarnings("unused")
		double[] test=sim.getBoxCoords(101);
	}

	//test newWord
	@org.junit.Test(expected=IllegalArgumentException.class)
	public void testNewWord() {
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);
		for(int i=1; i<6; i++){
			sim.newWord();
		}
	}

	public static void main(String[] args){
		org.junit.runner.JUnitCore.main(TestSimulator.class.getName());
	}


}
