import static org.junit.Assert.assertEquals;

import org.junit.*;

public class TestInstruction {

    @org.junit.Test
	public void testConstructor() {
		String type = "CIRCLE";
		double[] start = new double[]{0,0};
		double[] end = new double[]{1,1};
		double rotVel = 0;
		double startTheta = 0;
		Instruction i = new Instruction(type, start, end, rotVel,startTheta);
		Instruction i2 = new Instruction(type, start, end, rotVel);
        assertEquals(i.getRotVel(),0,1e-9);
        assertEquals(i.getStartTheta(),0,1e-9);
        assertEquals(i2.getStartTheta(),Double.NEGATIVE_INFINITY); //need a start theta if its a circle
	}


	public static void main(String[] args){
		org.junit.runner.JUnitCore.main(TestInstruction.class.getName());
	}

}
