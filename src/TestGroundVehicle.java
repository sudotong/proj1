import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

@SuppressWarnings("deprecation")
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
    double dx = 5, dy = 0, dt = 0;
    GroundVehicle gv = new GroundVehicle(pose, 0,0);
    double [] newPose = gv.getPosition();
    Assert.assertEquals(pose[0], newPose[0], 1e-6);
    Assert.assertEquals(pose[1], newPose[1], 1e-6);
    Assert.assertEquals(pose[2], newPose[2], 1e-6);

    double [] newVel = gv.getVelocity();
    Assert.assertEquals(dx, newVel[0], 1e-6);
    Assert.assertEquals(dy, newVel[1], 1e-6);
    Assert.assertEquals(dt, newVel[2], 1e-6);

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
    Assert.assertEquals(-Math.PI/4, newVel[2], 1e-6);

    vel[0] = 0; 
    vel[1] = 20; 
    vel[2] = Math.PI; 
    gv.setVelocity(vel);
    newVel = gv.getVelocity();

    Assert.assertEquals(0, newVel[0], 1e-6);
    Assert.assertEquals(10, newVel[1], 1e-6);
    Assert.assertEquals(Math.PI/4, newVel[2], 1e-6);


  }

  // controlVehicle and updateState are tricky to test. You have to use your
  // judgement as to how to test these. Typically what happens is that as you
  // develop, you discover edge cases that need to be added. 
	
  @Test
    public void testControlVehicle() {
    double [] pose = {0, 0, 0};
    
    GroundVehicle gv = new GroundVehicle(pose, 5,0);

    // Acceleration in x

    Control c = new Control(0, 10);
    gv.controlVehicle(c);
    
    double [] newVel = gv.getVelocity();
    
    Assert.assertEquals(0, newVel[0], 1e-6);
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
    Assert.assertEquals(0, newVel[0], 1e-6);
    Assert.assertEquals(10, newVel[1], 1e-6);
    Assert.assertEquals(0, newVel[2], 1e-6);

    // Acceleration at PI/4 from 5m/s to 10 m/s.
    
    vel[0] = Math.sqrt(12.5);
    vel[1] = Math.sqrt(12.5);
    vel[2] = Math.PI/4;
    gv.setVelocity(vel);
    c = new Control(0, 10);
    gv.controlVehicle(c);
    
    newVel = gv.getVelocity();    
    Assert.assertEquals(10, Math.sqrt(newVel[0]*newVel[0]+newVel[1]*newVel[1]), 1e-6);

    // Rotational acceleration in x

    vel[2] = 0;
    gv.setVelocity(vel);
    c = new Control(Math.PI/8, 5);
    gv.controlVehicle(c);
    
    newVel = gv.getVelocity();
    Assert.assertEquals(Math.PI/8, newVel[2], 1e-6);
  }

  @Test
    public void testAdvance() {
    double [] pose = {0, 0, 0};
    double dx = 5, dy = 0, dt = 0;
    GroundVehicle gv = new GroundVehicle(pose, 0,0);

    // Straight-line motion along x

    gv.advance(1, 0);
    
    double [] newPose = gv.getPosition();
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
  
  public static void main(String[] args){
    JUnitCore.main(TestGroundVehicle.class.getName());
  }
}
