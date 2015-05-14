import org.junit.*;

public class TestGroundVehicle {	
	
	
  @Test
    public void testConstructor() {
    double [] pose = {1, 2, 3};
    double s=5;
    double w=-0.1;
    GroundVehicle gv = new GroundVehicle(pose, s,w);
    double [] newPose = gv.getPosition();
    Assert.assertEquals(pose[0], newPose[0], 1e-6);
    Assert.assertEquals(pose[1], newPose[1], 1e-6);
    Assert.assertEquals(pose[2], newPose[2], 1e-6);

    double [] newVel = gv.getVelocity();
    Assert.assertEquals(s, Math.sqrt(newVel[1]*newVel[1]+newVel[0]*newVel[0]), 1e-2);
    Assert.assertEquals(w, newVel[2], 1e-6);
  }

  @Test(expected=IllegalArgumentException.class)
    public void testTooManyArgumentsInConstructor() {
    // Too many arguments in pose constructor 
    double [] pose = {0, 0, 0, 0};
      new GroundVehicle(pose, 0, 0);
  }

  @Test(expected=IllegalArgumentException.class)
    public void testTooFewArgumentsInConstructor() {
    // Too few arguments in pose constructor 
    double [] pose = {0};
    new GroundVehicle(pose,  0, 0);
  }

  @Test(expected=IllegalArgumentException.class)
    public void testTooManyArgumentsSetPosition() {
    // Too many arguments in setPosition 
    double [] pose = {0, 0, 0};
    GroundVehicle gv = new GroundVehicle(pose, 0, 0);
    double [] newPose = {0, 0, 0, 0};
    gv.setPosition(newPose);
  }

  @Test(expected=IllegalArgumentException.class)
    public void testTooFewArgumentsSetPosition() {
    // Too few arguments in setPosition 
    double [] pose = {0, 0, 0};
    GroundVehicle gv = new GroundVehicle(pose, 0, 0);
    double [] newPose = {0};
    gv.setPosition(newPose);
  }

  @Test(expected=IllegalArgumentException.class)
    public void testTooManyArgumentsSetVelocity() {
    // Too many arguments in setVelocity 
    double [] pose = {0, 0, 0};
    GroundVehicle gv = new GroundVehicle( pose, 0, 0);
    double [] newVel = {0, 0, 0, 0};
    gv.setVelocity(newVel);
  }

  @Test(expected=IllegalArgumentException.class)
    public void testTooFewArgumentsSetVelocity() {
    // Too few arguments in setVelocity
    double [] pose = {0, 0, 0};
    GroundVehicle gv = new GroundVehicle(pose, 0, 0);
    double [] newVel = {0};
    gv.setVelocity(newVel);
  }

  // Test get/set Position/Velocity at all legal position bounds

  @Test
    public void testGetSetPositionValid() {
    double [] pose = {1, 2, 3};
    GroundVehicle gv = new GroundVehicle(pose, 5,0);
    double [] newPose = gv.getPosition();
    Assert.assertEquals(pose[0], newPose[0], 1e-6);
    Assert.assertEquals(pose[1], newPose[1], 1e-6);
    Assert.assertEquals(pose[2], newPose[2], 1e-6);

    double [] newVel = gv.getVelocity();
   

    // First test getPosition and setPosition at legal bounds

    pose[0] = 0; 
    gv.setPosition(pose);
    newPose = gv.getPosition();
    Assert.assertEquals(pose[0], newPose[0], 1e-6);

    pose[0] = 99; 
    gv.setPosition(pose);
    newPose = gv.getPosition();
    Assert.assertEquals(pose[0], newPose[0], 1e-6);

    pose[1] = 0; 
    gv.setPosition(pose);
    newPose = gv.getPosition();
    Assert.assertEquals(pose[1], newPose[1], 1e-6);

    pose[1] = 99; 
    gv.setPosition(pose);
    newPose = gv.getPosition();
    Assert.assertEquals(pose[1], newPose[1], 1e-6);

    pose[2] = -Math.PI; 
    gv.setPosition(pose);
    newPose = gv.getPosition();
    Assert.assertEquals(pose[2], newPose[2], 1e-6);

    pose[2] = Math.toRadians(179); 
    gv.setPosition(pose);
    newPose = gv.getPosition();
    Assert.assertEquals(pose[2], newPose[2], 1e-6);

    // Test getVelocity and setVelocity at all legal position bounds

    double [] vel = gv.getVelocity();
    
    vel[0] = 5; 
    vel[1] = 0; 
    gv.setVelocity(vel);
    newVel = gv.getVelocity();
    Assert.assertEquals(vel[0], newVel[0], 1e-6);

    vel[0] = 10; 
    vel[1] = 0; 
    gv.setVelocity(vel);
    newVel = gv.getVelocity();
    Assert.assertEquals(vel[0], newVel[0], 1e-6);

    vel[0] = 0; 
    vel[1] = 5; 
    gv.setVelocity(vel);
    newVel = gv.getVelocity();
    Assert.assertEquals(vel[1], newVel[1], 1e-6);

    vel[0] = 0; 
    vel[1] = 10; 
    gv.setVelocity(vel);
    newVel = gv.getVelocity();
    Assert.assertEquals(vel[1], newVel[1], 1e-6);

    vel[2] = -Math.PI/4.0; 
    gv.setVelocity(vel);
    newVel = gv.getVelocity();
    Assert.assertEquals(vel[2], newVel[2], 1e-6);

    vel[2] = -Math.PI/4.0;
    gv.setVelocity(vel);
    newVel = gv.getVelocity();
    Assert.assertEquals(vel[2], newVel[2], 1e-6);    
  }

  // Test get/set Position and Velocity at illegal position bounds

  @Test
    public void testGetSetPositionInvalid(){
    double [] pose = {1, 2, 3};
    GroundVehicle gv = new GroundVehicle(pose, 0,0);
    double [] newPose = gv.getPosition();

    // Test getPosition and setPosition at illegal bounds. Since all bounds
    // violations get clamped to legal limits, we can test all three
    // dimensions of position at once. 

    pose[0] = -1; 
    pose[1] = -1; 
    pose[2] = -2*Math.PI; 
    gv.setPosition(pose);
    newPose = gv.getPosition();
    Assert.assertEquals(0, newPose[0], 1e-6);
    Assert.assertEquals(0, newPose[1], 1e-6);
    Assert.assertEquals(-Math.PI, newPose[2], 1e-6);

    pose[0] = 101; 
    pose[1] = 101; 
    pose[2] = Math.PI; 
    gv.setPosition(pose);
    newPose = gv.getPosition();
    Assert.assertEquals(100, newPose[0], 1);
    Assert.assertEquals(100, newPose[1], 1e-6);
    Assert.assertEquals(-Math.PI, newPose[2], 1e-6);
    
    // Test getVelocity and setVelocity at illegal bounds. Since all bounds
    // violations get clamped to legal limits, we can test all three
    // dimensions of velocity at once.
    double [] vel = gv.getVelocity();
    vel[0] = 0.5; 
    vel[1] = 0; 
    vel[2] = -Math.PI; 
    gv.setVelocity(vel);
    double [] newVel = gv.getVelocity();
    Assert.assertEquals(1.0, newVel[0], 0.5);
    Assert.assertEquals(0, newVel[1], 1e-6);
    Assert.assertEquals(-Math.PI, newVel[2], 1e-6);

    vel[0] = 0; 
    vel[1] = 20; 
    vel[2] = Math.PI; 
    gv.setVelocity(vel);
    newVel = gv.getVelocity();

    Assert.assertEquals(0, newVel[0], 1e-6);
    Assert.assertEquals(10, newVel[1], 1e-6);
    Assert.assertEquals(Math.PI, newVel[2], 1e-6);


  }

	
  @Test
    public void testControlVehicle() {
    double [] pose = {0, 0, 0};
    
    GroundVehicle gv = new GroundVehicle(pose, 5,0);

    // Acceleration in x

    Control c = new Control(5,0);
    gv.controlVehicle(c);
    
    double [] newVel = gv.getVelocity();
    Assert.assertEquals(5, newVel[0], 1e-6);
    Assert.assertEquals(0, newVel[1], 1e-6);
    Assert.assertEquals(0, newVel[2], 1e-6);

    // Acceleration in y
    
    pose[0] = 0;
    pose[1] = 0;
    pose[2] = Math.PI/2;
    gv.setPosition(pose);
    double [] vel = {10, 0, 0};
    gv.setVelocity(vel);

    c = new Control(0, 10);
    gv.controlVehicle(c);
    newVel = gv.getVelocity(); 
    System.out.println(newVel[1]);
    Assert.assertEquals(0, newVel[0], 1e-6);
    Assert.assertEquals(0, newVel[1], 1e-6);
    Assert.assertEquals(Math.PI, newVel[2], 1e-6);

    // Acceleration at PI/4 from 5m/s to 10 m/s.
    
    vel[0] = Math.sqrt(12.5);
    vel[1] = Math.sqrt(12.5);
    vel[2] = Math.PI/4;
    gv.setVelocity(vel);
    c = new Control(10, 0);
    gv.controlVehicle(c);
    
    newVel = gv.getVelocity();    
    Assert.assertEquals(10, Math.sqrt(newVel[0]*newVel[0]+newVel[1]*newVel[1]), 1e-6);

    // Rotational acceleration in x

    vel[2] = 0;
    gv.setVelocity(vel);
    c = new Control(5, Math.PI/8);
    gv.controlVehicle(c);
    
    newVel = gv.getVelocity();
    Assert.assertEquals(Math.PI/8, newVel[2], 1e-6);
  }

  @Test
    public void testAdvance() {
    double [] pose = {0, 0, 0};
    GroundVehicle gv = new GroundVehicle(pose, 5,0);

    // Straight-line motion along x

    gv.advance(1, 0);
    
    double [] newPose = gv.getPosition();
    System.out.println(newPose[0]);
    Assert.assertEquals(5, newPose[0], 0.2);
    Assert.assertEquals(0, newPose[1], 0.2);
    Assert.assertEquals(0, newPose[2], 0.2);

    // Straight-line motion along y

    pose[0] = 0;
    pose[1] = 0;
    pose[2] = 0;
    gv.setPosition(pose);
    double [] vel = {0, 5, 0};
    gv.setVelocity(vel);

    gv.advance(1, 0);
    
    newPose = gv.getPosition();
    Assert.assertEquals(0, newPose[0], 0.2);
    Assert.assertEquals(5, newPose[1], 0.2);
    Assert.assertEquals(0, newPose[2], 0.2);

    // Straight-line motion along PI/4
    
    pose[0] = 0;
    pose[1] = 0;
    pose[2] = 0;
    gv.setPosition(pose);

    // Set vehicle moving at 5 m/s along PI/4

    vel[0] = Math.sqrt(12.5);
    vel[1] = Math.sqrt(12.5);
    vel[2] = 0;
    gv.setVelocity(vel);
    double [] newVel = gv.getVelocity();
    Assert.assertEquals(vel[0], newVel[0], 0.2);
    Assert.assertEquals(vel[1], newVel[1], 0.2);
    Assert.assertEquals(vel[2], newVel[2], 0.2);

    gv.advance(1, 0);
    
    newPose = gv.getPosition();
    Assert.assertEquals(Math.sqrt(12.5), newPose[0], 0.2);
    Assert.assertEquals(Math.sqrt(12.5), newPose[1], 0.2);
    Assert.assertEquals(0, newPose[2], 0.2);

    // Rotational motion

    pose[0] = 0;
    pose[1] = 0;
    pose[2] = 0;
    gv.setPosition(pose);

    vel[0] = Math.sqrt(5);
    vel[1] = Math.sqrt(5);
    vel[2] = Math.PI/8;
    gv.setVelocity(vel);

    gv.advance(1, 0);
    
    newPose = gv.getPosition();
    Assert.assertEquals(Math.PI/8, newPose[2], 0.2);
  }
  
  //public static void main(String[] args){
  //  JUnitCore.main(TestGroundVehicle.class.getName());
  //}

  @Test
  public void testClampVelocity() {
	  double[] pose = { 5,5,0 };
		GroundVehicle gv = new GroundVehicle(pose, 0, 0);

		// Boundary conditions
		double[] newVel = { Math.sqrt(10), Math.sqrt(10), +Math.PI / 4 };
		gv.setVelocity(newVel);
		Assert.assertEquals(newVel[0], gv.getVelocity()[0], 1e-9);
		Assert.assertEquals(newVel[1], gv.getVelocity()[1], 1e-9);
		Assert.assertEquals(newVel[2], gv.getVelocity()[2], 1e-9);

		// Boundary conditions
		double[] newVel2 = { 0, 10, -Math.PI / 4 };
		gv.setVelocity(newVel2);
		
		Assert.assertEquals(newVel2[0], gv.getVelocity()[0], 1e-9);
		Assert.assertEquals(newVel2[1], gv.getVelocity()[1], 1e-9);
		Assert.assertEquals(newVel2[2], gv.getVelocity()[2], 1e-9);
		// All Parameters Above Lower boundary conditions
		double[] newVel3 = { 12, 16, 5 * Math.PI };
		double[] expectedNewVel3 = { 6, 8, +Math.PI };
		gv.setVelocity(newVel3);
		Assert.assertEquals(expectedNewVel3[0], gv.getVelocity()[0], 1e-9);
		Assert.assertEquals(expectedNewVel3[1], gv.getVelocity()[1], 1e-9);
		Assert.assertEquals(expectedNewVel3[2], gv.getVelocity()[2], 1e-9);
		// All Parameters Above Upper boundary conditions
		double[] newVel4 = { 7, 19, -7.6 * Math.PI };
		double vel = Math.sqrt(7 * 7 + 19 * 19);
		double[] expectedNewVel4 = { 7 * 10 / vel, 19 * 10 / vel, -Math.PI };
		gv.setVelocity(newVel4);
		Assert.assertEquals(expectedNewVel4[0], gv.getVelocity()[0], 1e-9);
		Assert.assertEquals(expectedNewVel4[1], gv.getVelocity()[1], 1e-9);
		Assert.assertEquals(expectedNewVel4[2], gv.getVelocity()[2], 1e-9);
  }
  @Test
  public void testClampPosition() {
	  double[] pose = { 5,5,0 };
		GroundVehicle gv = new GroundVehicle(pose, 0, 0);

		// Lower boundary conditions
		double[] newPosLower = { 0, 0, -Math.PI };
		gv.setPosition(newPosLower);
		Assert.assertEquals(newPosLower[0], gv.getPosition()[0], 1e-9);
		Assert.assertEquals(newPosLower[1], gv.getPosition()[1], 1e-9);
		Assert.assertEquals(newPosLower[2], gv.getPosition()[2], 1e-9);

		

		// Upper boundary conditio2ns
		double[] newPosUpper = { 200, 100, -Math.PI };
		gv.setPosition(newPosUpper);
		Assert.assertEquals(newPosUpper[0], gv.getPosition()[0], 1e-9);
		Assert.assertEquals(newPosUpper[1], gv.getPosition()[1], 1e-9);
		Assert.assertEquals(newPosUpper[2], gv.getPosition()[2], 1e-9);
		
		double[] newPos1 = { -40, -60, -5 * Math.PI };
		gv.setPosition(newPos1);
		Assert.assertEquals(newPosLower[0], gv.getPosition()[0], 1e-9);
		Assert.assertEquals(newPosLower[1], gv.getPosition()[1], 1e-9);
		Assert.assertEquals(newPosLower[2], gv.getPosition()[2], 1e-9);
		
		// All Parameters Above Upper boundary conditions
		double[] newPos2 = { 500, 200, 2 * Math.PI };
		gv.setPosition(newPos2);
		double[] expectedPos2 = { 200, 100, -Math.PI };
		Assert.assertEquals(expectedPos2[0], gv.getPosition()[0], 1e-9);
		Assert.assertEquals(expectedPos2[1], gv.getPosition()[1], 1e-9);
		Assert.assertEquals(expectedPos2[2], gv.getPosition()[2], 1e-9);
		// One Parameter Below Lower boundary conditions,

		double[] newPos3 = { -13.5, 50, 0 };
		double[] expectedPos3 = { 0.0, 50.0, 0.0 };
		gv.setPosition(newPos3);
		Assert.assertEquals(expectedPos3[0], gv.getPosition()[0], 1e-9);
		Assert.assertEquals(expectedPos3[1], gv.getPosition()[1], 1e-9);
		Assert.assertEquals(expectedPos3[2], gv.getPosition()[2], 1e-9);
		
		double[] newPos4 = { 51, -20, 1 };
		double[] expectedPos4 = { 51, 0, 1 };
		gv.setPosition(newPos4);
		Assert.assertEquals(expectedPos4[0], gv.getPosition()[0], 1e-9);
		Assert.assertEquals(expectedPos4[1], gv.getPosition()[1], 1e-9);
		Assert.assertEquals(expectedPos4[2], gv.getPosition()[2], 1e-9);

		double[] newPos5 = { 50, 50, -5 * Math.PI };
		double[] expectedPos5 = { 50, 50, -Math.PI };
		gv.setPosition(newPos5);
		Assert.assertEquals(expectedPos5[0], gv.getPosition()[0], 1e-9);
		Assert.assertEquals(expectedPos5[1], gv.getPosition()[1], 1e-9);
		Assert.assertEquals(expectedPos5[2], gv.getPosition()[2], 1e-9);
		// One Parameter above boundary conditions

		double[] newPos6 = { 400, 30, 1 };
		double[] expectedPos6 = { 200, 30, 1 };
		gv.setPosition(newPos6);
		Assert.assertEquals(expectedPos6[0], gv.getPosition()[0], 1e-9);
		Assert.assertEquals(expectedPos6[1], gv.getPosition()[1], 1e-9);
		Assert.assertEquals(expectedPos6[2], gv.getPosition()[2], 1e-9);
		
		double[] newPos7 = { 70.5, 600, -1 };
		double[] expectedPos7 = { 70.5, 100, -1 };
		gv.setPosition(newPos7);
		Assert.assertEquals(expectedPos7[0], gv.getPosition()[0], 1e-9);
		Assert.assertEquals(expectedPos7[1], gv.getPosition()[1], 1e-9);
		Assert.assertEquals(expectedPos7[2], gv.getPosition()[2], 1e-9);
		double[] newPos8 = { 50, 50, 8.5 * Math.PI };
		double[] expectedPos8 = { 50, 50, - Math.PI };
		gv.setPosition(newPos8);
		Assert.assertEquals(expectedPos8[0], gv.getPosition()[0], 1e-9);
		Assert.assertEquals(expectedPos8[1], gv.getPosition()[1], 1e-9);
		Assert.assertEquals(expectedPos8[2], gv.getPosition()[2], 1e-9);
  }
  
}
