package proj1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Letters {
	
	double xBoxSize = 20;
	double yBoxSize = 20;
	
	public Letters(String letter){
		return;
	}
	
	public static ArrayList<Instruction> get(String letter){
		if (letter.length() > 1){
			System.err.println("Must pass in letter of length 1");
			System.exit(0);
		}
		ArrayList<Instruction> alist = new ArrayList<Instruction>();
		switch(letter.toUpperCase()){
		case "A":
			double xstart = 1;
			double ystart = 2;
			Instruction leftline = new Instruction(Instruction.LINE,new double[]{xstart,ystart},new double[]{xstart,ystart},0);
			Instruction rightline = new Instruction(Instruction.LINE,new double[]{xstart,ystart},new double[]{xstart,ystart},0);
			Instruction centerline = new Instruction(Instruction.LINE,new double[]{xstart,ystart},new double[]{xstart,ystart},0);
			alist.add(leftline);
			alist.add(rightline);
			alist.add(centerline);
			break;
		case "B":
			break;
		case "C":
			break;
		case "D":
			break;
			
		default:
			break;
		}
		
		
		
		
		return alist;
	}
}
