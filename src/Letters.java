import java.util.ArrayList;

public class Letters {

	// BOTTOM LEFT IS (0,0)
	// TOP LEFT IS (0,yBoxSize)
	// TOP RIGHT IS (xBoxSize,yBoxSize)

	private final double xBoxSize;
	private final double yBoxSize;
	private final double vehicleSpeed = 10; // make sure this is true

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
		Instruction leftLine = null;
		Instruction rightLine = null;
		Instruction topLine = null;
		Instruction bottomLine = null;
		Instruction centerLine = null;
		Instruction topBubble = null;
		Instruction bottomBubble = null;
		
		final double xleft = this.xBoxSize*1/4;
		final double yleft_start = this.yBoxSize;
		final double yleft_end = 0;
		leftLine = new Instruction(Instruction.LINE,new double[]{xleft,yleft_start},new double[]{xleft,yleft_end},0);
		
		switch(letter){
		case 'A':
			double xleft_startA = this.xBoxSize*1/4;
			double xright_startA = this.xBoxSize*3/4;
			double ystartA = 0;
			double xtopA = this.xBoxSize/2;
			double ytopA = this.yBoxSize;
			double ymiddleA = this.yBoxSize/2;
			double xmiddle_startA = this.getPointInSlope(xleft_startA,ystartA,xtopA,ytopA,ymiddleA,"x");
			double xmiddle_endA = this.getPointInSlope(xright_startA,ystartA,xtopA,ytopA,ymiddleA,"x");					

			leftLine = new Instruction(Instruction.LINE,new double[]{xleft_startA,ystartA},new double[]{xtopA,ytopA},0);
			rightLine = new Instruction(Instruction.LINE,new double[]{xright_startA,ystartA},new double[]{xtopA,ytopA},0);
			centerLine = new Instruction(Instruction.LINE,new double[]{xmiddle_startA,ymiddleA},new double[]{xmiddle_endA,ymiddleA},0);
			alist.add(leftLine);
			alist.add(rightLine);
			alist.add(centerLine);
			break;
		case 'B':
			double xleftB = this.xBoxSize*1/4;
			double yleft_startB = this.yBoxSize;
			double yleft_endB = 0;
			double ymiddleB = this.yBoxSize/2;
			double bubbleRadiusB = (this.xBoxSize*3/4 - xleftB)/2;
			double rotVelB = -1*this.vehicleSpeed/bubbleRadiusB; //counterclockwise is positive
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftB,yleft_startB},new double[]{xleftB,yleft_endB},0);
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{xleftB,yleft_startB},new double[]{xleftB,ymiddleB},rotVelB);
			bottomBubble = new Instruction(Instruction.CIRCLE,new double[]{xleftB,ymiddleB},new double[]{xleftB,yleft_endB},rotVelB);
			alist.add(leftLine);
			alist.add(topBubble);
			alist.add(bottomBubble);
			break;
		case 'C':
			double xC = this.xBoxSize/2;
			double ystartC = this.yBoxSize;
			double yendC = 0; // TODO make look more like a C
			double radiusC = this.xBoxSize/2;
			double rotVelC = this.vehicleSpeed/radiusC;
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{xC,ystartC},new double[]{xC,yendC},rotVelC);
			alist.add(topBubble);
			break;
		case 'D':
			double radiusD = this.xBoxSize/2;
			double rotVelD = this.vehicleSpeed/radiusD;
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{xleft,yleft_start},new double[]{xleft,yleft_end},rotVelD);
			alist.add(leftLine);
			alist.add(topBubble);
			break;
		case 'E':
			double xleftE = this.xBoxSize*1/4;
			double ytopE = this.yBoxSize*7/8;
			double ycenterE = this.yBoxSize/2;
			double ybottomE = this.yBoxSize*1/8;
			double xendE = this.xBoxSize*3/4;
			
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftE,ytopE},new double[]{xleftE,ybottomE},0);
			topLine = new Instruction(Instruction.LINE,new double[]{xleftE,ytopE},new double[]{xendE,ytopE},0);
			centerLine = new Instruction(Instruction.LINE,new double[]{xleftE,ycenterE},new double[]{xendE,ycenterE},0);
			bottomLine = new Instruction(Instruction.LINE,new double[]{xleftE,ybottomE},new double[]{xendE,ybottomE},0);			
			alist.add(leftLine);
			alist.add(topLine);
			alist.add(centerLine);
			alist.add(bottomLine);
			break;
		case 'V':
			double xleft_startV = this.xBoxSize*1/4;
			double xright_startV = this.xBoxSize*3/4;
			double ystartV = this.yBoxSize;
			double xbottomV = this.xBoxSize/2;
			double ybottomV = 0;
			leftLine = new Instruction(Instruction.LINE,new double[]{xleft_startV,ystartV}, new double[]{xbottomV,ybottomV},0);
			rightLine = new Instruction(Instruction.LINE,new double[]{xright_startV,ystartV}, new double[]{xbottomV,ybottomV},0);
			alist.add(leftLine);
			alist.add(rightLine);
			break;	
			
		default:
			break;
		}
		return alist;
	}
	
	private double getPointInSlope(double xstart,double ystart,double xend,double yend,double intercept,String type){
		double point;
		double slope = (yend-ystart)/(xend-xstart);
		double b = ystart - slope*xstart;
		if (type.equals("x")){ // want to get x value at intercept
			point = (intercept - b)/slope;
		} else if (type.equals("y")){ // want to get y value at intercept
			point = slope*intercept + b; 
		} else {
			return Double.NEGATIVE_INFINITY;
		}		
		return point;
	}
}
