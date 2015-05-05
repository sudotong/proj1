import java.util.ArrayList;

public class Letters {

	// BOTTOM LEFT IS (0,0)
	// TOP LEFT IS (0,yBoxSize)
	// TOP RIGHT IS (xBoxSize,yBoxSize)

	private final double xBoxSize;
	private final double yBoxSize;

	public Letters(){
		this.xBoxSize = 20;
		this.yBoxSize = 20;
		return;
	}
	
	public Letters(double xSize, double ySize){
		if (xSize > 0 && ySize > 0){
			this.xBoxSize = xSize;
			this.yBoxSize = ySize;
		} else {
			this.xBoxSize = 20;
			this.yBoxSize = 20;
		}
		return;
	}
	
	

	public ArrayList<Instruction> get(char letter){
		//		if (letter.length() > 1){
		//			System.err.println("Must pass in letter of length 1");
		//			System.exit(0);
		//		}
		ArrayList<Instruction> alist = new ArrayList<Instruction>();
		switch(letter){
		case 'A':
			double xstart = 1;
			double ystart = 2;

			Instruction cornerLine = new Instruction(Instruction.LINE,new double[]{xstart,ystart},new double[]{xstart,ystart},0);

			Instruction rightline = new Instruction(Instruction.LINE,new double[]{xstart,ystart},new double[]{xstart,ystart},0);
			Instruction centerline = new Instruction(Instruction.LINE,new double[]{xstart,ystart},new double[]{xstart,ystart},0);
			alist.add(cornerLine);
			alist.add(rightline);
			alist.add(centerline);
			break;
		case 'B':
			break;
		case 'C':
			break;
		case 'D':
			break;

		default:
			break;
		}
		return alist;
	}
}
