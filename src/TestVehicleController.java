import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.*;

public class TestVehicleController {

	/*DisplayServer must be running for these tests to work
	 */

	public void testConstructor() {
		GroundVehicle gv= new GroundVehicle(new double[]{50,50,0}, 5,0);
		DisplayClient dp= new DisplayClient("localhost");
		Simulator sim = new Simulator(dp);
		VehicleController vc= new VehicleController(sim, gv);
		assertEquals(vc.getControl(),null);
	}

	//test setVehicles works by adding a vehicle in the same position and checking if there is a collision
		@Test
		public void testSetVehicles() {
			GroundVehicle gv= new GroundVehicle(new double[]{50,50,0}, 5,0);
			DisplayClient dp= new DisplayClient("localhost");
			Simulator sim = new Simulator(dp);
			VehicleController vc= new VehicleController(sim, gv);

			ArrayList<GroundVehicle> myList = new ArrayList<GroundVehicle>();
			GroundVehicle gv1= new GroundVehicle(new double[]{50,50,0}, 5,0);
			myList.add(gv1);
			assertEquals(vc.isCollision(), false);
			vc.setVehicles(myList);
			assertEquals(vc.isCollision(), true);

		}

    //test collisionControl works by making sure the control is null if there aren't many collisions, and that
    //the control is non-null (e.g. stop moving) when there are lots of collisions (>200)
        @Test
        public void testCollisionControl() {
            GroundVehicle gv= new GroundVehicle(new double[]{50,50,0}, 5,0);
            DisplayClient dp= new DisplayClient("localhost");
            Simulator sim = new Simulator(dp);
            VehicleController vc= new VehicleController(sim, gv);

            ArrayList<GroundVehicle> myList = new ArrayList<GroundVehicle>();
            GroundVehicle gv1= new GroundVehicle(new double[]{50,50,0}, 5,0);
            myList.add(gv1);
            vc.setVehicles(myList);
            Control c = vc.collisionControl();
            assertEquals(c.getRotVel(), 0, 1e-9);
			assertEquals(c.getSpeed(),0, 1e-9);
            for (int i=0;i<200;i++){
                vc.isCollision();
            }
            assertEquals(vc.collisionControl(),null);


        }


	public static void main(String[] args){
		org.junit.runner.JUnitCore.main(TestVehicleController.class.getName());
	}

}
