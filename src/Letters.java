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
		Instruction centerLine2 = null;
		Instruction topBubble = null;
		Instruction bottomBubble = null;
		double startTheta;
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
			startTheta = Math.PI*0;
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftB,yleft_startB},new double[]{xleftB,yleft_endB},0);
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{xleftB,yleft_startB},new double[]{xleftB,ymiddleB},rotVelB,startTheta);
			bottomBubble = new Instruction(Instruction.CIRCLE,new double[]{xleftB,ymiddleB},new double[]{xleftB,yleft_endB},rotVelB,startTheta);
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
			startTheta = Math.PI*-1;
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{xC,ystartC},new double[]{xC,yendC},rotVelC,startTheta);
			alist.add(topBubble);
			break;
		case 'D':
			double radiusD = this.xBoxSize/2;
			double rotVelD = this.vehicleSpeed/radiusD;
			startTheta = Math.PI*0;
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{xleft,yleft_start},new double[]{xleft,yleft_end},rotVelD,startTheta);
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
		case 'F':
			double xleftF = this.xBoxSize*1/4;
			double ytopF = this.yBoxSize*7/8;
			double ycenterF = this.yBoxSize/2;
			double ybottomF = this.yBoxSize*1/8;
			double xendF = this.xBoxSize*3/4;
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftF,ytopF},new double[]{xleftF,ybottomF},0);
			topLine = new Instruction(Instruction.LINE,new double[]{xleftF,ytopF},new double[]{xendF,ytopF},0);
			centerLine = new Instruction(Instruction.LINE,new double[]{xleftF,ycenterF},new double[]{xendF,ycenterF},0);
			alist.add(leftLine);
			alist.add(topLine);
			alist.add(centerLine);
			break;
		case 'G':
			double xstartG = this.xBoxSize/2;
			double ystartG = this.yBoxSize;
			double bubbleRadiusG = this.xBoxSize/2;
			double xendG = this.xBoxSize*3/4;
			double yendG = this.yBoxSize/2;
			double rotVelG = this.vehicleSpeed/bubbleRadiusG;
			startTheta = Math.PI*-1;
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{xstartG,ystartG},new double[]{xendG,yendG},rotVelG,startTheta);
			centerLine = new Instruction(Instruction.LINE,new double[]{xstartG,yendG},new double[]{xendG,yendG},0);
			alist.add(topBubble);
			alist.add(centerLine);
			break;
		case 'H':
			double xleftH = this.xBoxSize*1/4;
			double ytopH = this.yBoxSize*7/8;
			double ycenterH = this.yBoxSize/2;
			double ybottomH = this.yBoxSize*1/8;
			double xrightH = this.xBoxSize*3/4;
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftH,ytopH},new double[]{xleftH,ybottomH},0);
			centerLine = new Instruction(Instruction.LINE,new double[]{xleftH,ycenterH},new double[]{xrightH,ycenterH},0);
			rightLine = new Instruction(Instruction.LINE,new double[]{xrightH,ytopH},new double[]{xrightH,ybottomH},0);
			alist.add(leftLine);
			alist.add(centerLine);
			alist.add(rightLine);
			break;
		case 'I':
			double xleftI = this.xBoxSize*1/4;
			double ytopI = this.yBoxSize*7/8;
			double xcenterI = this.xBoxSize/2;
			double ybottomI = this.yBoxSize*1/8;
			double xrightI = this.xBoxSize*3/4;
			topLine = new Instruction(Instruction.LINE,new double[]{xleftI,ytopI},new double[]{xrightI,ytopI},0);
			centerLine = new Instruction(Instruction.LINE,new double[]{xcenterI,ytopI},new double[]{xcenterI,ybottomI},0);
			bottomLine = new Instruction(Instruction.LINE,new double[]{xleftI,ybottomI},new double[]{xrightI,ybottomI},0);
			alist.add(topLine);
			alist.add(centerLine);
			alist.add(bottomLine); 
			break;
		case 'J':
			double xleftJ = this.xBoxSize*1/4;
			double ytopJ = this.yBoxSize*7/8;
			double xcenterJ = this.xBoxSize/2;
			double yendJ = this.yBoxSize*1/8;
			double xrightJ = this.xBoxSize*3/4;			
			double radiusJ = this.yBoxSize*1/8;
			double xendJ = xcenterJ - 2*radiusJ;
			double rotVelJ = -1*this.vehicleSpeed/radiusJ;
			startTheta = Math.PI*-1/2;
			centerLine = new Instruction(Instruction.LINE,new double[]{xcenterJ,ytopJ},new double[]{xcenterJ,yendJ},0);
			topLine = new Instruction(Instruction.LINE,new double[]{xleftJ,ytopJ},new double[]{xrightJ,ytopJ},0);
			bottomBubble = new Instruction(Instruction.CIRCLE, new double[]{xcenterJ,yendJ},new double[]{xendJ,yendJ},rotVelJ,startTheta);
			alist.add(centerLine);
			alist.add(topLine);
			alist.add(bottomBubble);
			break;
		case 'K':
			double xleftK = this.xBoxSize*1/4;
			double ytopK = this.yBoxSize;
			double yendK = 0;
			double ymiddleK = this.yBoxSize/2;
			double xrightK = this.xBoxSize*3/4;
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftK,ytopK},new double[]{xleftK,yendK},0);
			topLine = new Instruction(Instruction.LINE,new double[]{xleftK,ymiddleK}, new double[]{xrightK,ytopK},0);
			rightLine = new Instruction(Instruction.LINE,new double[]{xleftK,ymiddleK}, new double[]{xrightK,yendK},0);
			alist.add(leftLine);
			alist.add(topLine);
			alist.add(rightLine);
			break;
		case 'L':
			double xleftL = this.xBoxSize*1/4;
			double ytopL = this.yBoxSize*7/8;
			double ybottomL = this.yBoxSize*1/8;
			double xendL = this.xBoxSize*3/4;
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftL,ytopL},new double[]{xleftL,ybottomL},0);
			bottomLine = new Instruction(Instruction.LINE,new double[]{xleftL,ybottomL},new double[]{xendL,ybottomL},0);			
			alist.add(leftLine);
			alist.add(bottomLine);
			break;
		case 'M':
			double xleftM = this.xBoxSize*1/4;
			double ytopM = this.yBoxSize*7/8;
			double xcenterM = this.xBoxSize/2;
			double ybottomM = this.yBoxSize*1/8;
			double xrightM = this.xBoxSize*3/4;
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftM,ytopM},new double[]{xleftM,ybottomM},0);
			centerLine = new Instruction(Instruction.LINE,new double[]{xleftM,ytopM},new double[]{xcenterM,ybottomM},0);
			centerLine2 = new Instruction(Instruction.LINE,new double[]{xrightM,ytopM},new double[]{xcenterM,ybottomM},0);
			rightLine = new Instruction(Instruction.LINE,new double[]{xrightM,ytopM},new double[]{xrightM,ybottomM},0);
			alist.add(leftLine);
			alist.add(centerLine);
			alist.add(centerLine2);
			alist.add(rightLine);
			break;
		case 'N':
			double xleftN = this.xBoxSize*1/4;
			double ytopN = this.yBoxSize*7/8;
			double ybottomN = this.yBoxSize*1/8;
			double xrightN = this.xBoxSize*3/4;
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftN,ytopN},new double[]{xleftN,ybottomN},0);
			centerLine = new Instruction(Instruction.LINE,new double[]{xleftN,ytopN},new double[]{xrightN,ybottomN},0);
			rightLine = new Instruction(Instruction.LINE,new double[]{xrightN,ytopN},new double[]{xrightN,ybottomN},0);
			alist.add(leftLine);
			alist.add(centerLine);
			alist.add(rightLine);
			break;
		case 'O':
			double xO = this.xBoxSize/2;
			double yO = this.yBoxSize;
			double bubbleRadiusO = this.xBoxSize/2;
			double rotVelO = -1*this.vehicleSpeed/bubbleRadiusO;
			startTheta = Math.PI*0;
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{xO,yO},new double[]{xO,yO},rotVelO,startTheta);
			alist.add(topBubble);
			break;
		case 'P':
			double xleftP = this.xBoxSize*1/4;
			double yleft_startP = this.yBoxSize;
			double yleft_endP = 0;
			double ymiddleP = this.yBoxSize/2;
			double bubbleRadiusP = (this.xBoxSize*3/4 - xleftP)/2;
			double rotVelP = -1*this.vehicleSpeed/bubbleRadiusP; //counterclockwise is positive
			startTheta = Math.PI*0;
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftP,yleft_startP},new double[]{xleftP,yleft_endP},0);
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{xleftP,yleft_startP},new double[]{xleftP,ymiddleP},rotVelP,startTheta);
			alist.add(leftLine);
			alist.add(topBubble);
			break;
		case 'Q':
			double xQ = this.xBoxSize/2;			
			double yQ = this.yBoxSize;
			double ymiddleQ = this.yBoxSize/2;
			double yendQ = 0;
			double xrightQ = this.xBoxSize;
			double bubbleRadiusQ = this.xBoxSize/2;
			double rotVelQ = -1*this.vehicleSpeed/bubbleRadiusQ;
			startTheta = Math.PI*0;
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{xQ,yQ},new double[]{xQ,yQ},rotVelQ,startTheta);
			rightLine = new Instruction(Instruction.LINE,new double[]{xQ,ymiddleQ}, new double[]{xrightQ,yendQ},0);
			alist.add(topBubble);
			alist.add(rightLine);
			break;
		case 'R':
			double xleftR = this.xBoxSize*1/4;
			double yleft_startR = this.yBoxSize;
			double yendR = 0;
			double ymiddleR = this.yBoxSize/2;
			double xrightR = this.xBoxSize*3/4;
			double bubbleRadiusR = (this.xBoxSize*3/4 - xleftR)/2;
			double rotVelR = -1*this.vehicleSpeed/bubbleRadiusR; //counterclockwise is positive
			startTheta = Math.PI*0;
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftR,yleft_startR},new double[]{xleftR,yendR},0);
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{xleftR,yleft_startR},new double[]{xleftR,ymiddleR},rotVelR,startTheta);
			rightLine = new Instruction(Instruction.LINE,new double[]{xleftR,ymiddleR}, new double[]{xrightR,yendR},0);
			alist.add(leftLine);
			alist.add(topBubble);
			alist.add(rightLine);
			break;
		case 'S':
			double xleftS = this.xBoxSize*1/4;
			double xmiddleS = this.xBoxSize/2;
			double ymiddleS = this.yBoxSize/2;
			double yupS = this.yBoxSize*3/4;
			double ydownS = this.yBoxSize*1/4;
			double xrightS = this.xBoxSize*3/4;
			double bubbleRadiusS = this.yBoxSize/8;
			double startTheta1 = Math.PI*-1;
			double startTheta2 = Math.PI*0;
			double rotVelS = -1*this.vehicleSpeed/bubbleRadiusS; //counterclockwise is positive
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{xmiddleS,ymiddleS},new double[]{xrightS,yupS},rotVelS,startTheta1);
			bottomBubble = new Instruction(Instruction.CIRCLE,new double[]{xmiddleS,ymiddleS},new double[]{xleftS,ydownS},rotVelS,startTheta2);
			alist.add(topBubble);
			alist.add(bottomBubble);
			break;
		case 'T':
			double xleftT = this.xBoxSize*1/4;
			double ytopT = this.yBoxSize*7/8;
			double xcenterT = this.xBoxSize/2;
			double ybottomT = this.yBoxSize*1/8;
			double xrightT = this.xBoxSize*3/4;
			centerLine = new Instruction(Instruction.LINE,new double[]{xcenterT,ytopT},new double[]{xcenterT,ybottomT},0);
			topLine = new Instruction(Instruction.LINE,new double[]{xleftT,ytopT},new double[]{xrightT,ytopT},0);
			alist.add(centerLine);
			alist.add(topLine); 
			break;
		case 'U':
			double xleftU = this.xBoxSize*1/4;
			double ytopU = this.yBoxSize*7/8;
			double xcenterU = this.xBoxSize/2;
			double yendU = this.yBoxSize*1/8;
			double xrightU = this.xBoxSize*3/4;			
			double radiusU = this.yBoxSize*1/8;
			double xendU = xcenterU - 2*radiusU;
			double rotVelU = this.vehicleSpeed/radiusU;
			startTheta = Math.PI*-1/2;
			rightLine = new Instruction(Instruction.LINE,new double[]{xrightU,ytopU},new double[]{xrightU,yendU},0);
			bottomBubble = new Instruction(Instruction.CIRCLE, new double[]{xleftU,yendU},new double[]{xendU,yendU},rotVelU,startTheta);
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftU,ytopU},new double[]{xleftU,yendU},0);
			alist.add(rightLine);
			alist.add(bottomBubble);
			alist.add(leftLine);
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
		case 'W':
			double xleftW = this.xBoxSize*1/4;
			double ytopW = this.yBoxSize*7/8;
			double xcenterW = this.xBoxSize/2;
			double ybottomW = this.yBoxSize*1/8;
			double xrightW = this.xBoxSize*3/4;
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftW,ytopW},new double[]{xleftW,ybottomW},0);
			centerLine = new Instruction(Instruction.LINE,new double[]{xleftW,ybottomW},new double[]{xcenterW,ytopW},0);
			centerLine2 = new Instruction(Instruction.LINE,new double[]{xrightW,ybottomW},new double[]{xcenterW,ytopW},0);
			rightLine = new Instruction(Instruction.LINE,new double[]{xrightW,ytopW},new double[]{xrightW,ybottomW},0);
			alist.add(leftLine);
			alist.add(centerLine);
			alist.add(centerLine2);
			alist.add(rightLine);
			break;
		case 'X':
			double xleftX = this.xBoxSize*1/4;
			double ytopX = this.yBoxSize;
			double ybottomX = 0;
			double xrightX = this.xBoxSize*3/4;
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftX,ytopX},new double[]{xrightX,ybottomX},0);
			rightLine = new Instruction(Instruction.LINE,new double[]{xrightX,ytopX},new double[]{xleftX,ybottomX},0);
			alist.add(leftLine);
			alist.add(rightLine);
			break;
		case 'Y':
			double xleftY = this.xBoxSize*1/4;
			double ytopY = this.yBoxSize;
			double xcenterY = this.xBoxSize/2;
			double ycenterY = this.yBoxSize/2;
			double ybottomY = 0;
			double xrightY = this.xBoxSize*3/4;
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftY,ytopY},new double[]{xcenterY,ycenterY},0);
			rightLine = new Instruction(Instruction.LINE,new double[]{xrightY,ytopY},new double[]{xcenterY,ycenterY},0);
			centerLine = new Instruction(Instruction.LINE,new double[]{xcenterY,ycenterY},new double[]{xcenterY,ybottomY},0);
			alist.add(leftLine);
			alist.add(rightLine);
			alist.add(centerLine);
			break;
		case 'Z':
			double xleftZ = this.xBoxSize*1/4;
			double ytopZ = this.yBoxSize*7/8;
			double ybottomZ = this.yBoxSize*1/8;
			double xrightZ = this.xBoxSize*3/4;
			topLine = new Instruction(Instruction.LINE,new double[]{xleftZ,ytopZ},new double[]{xrightZ,ytopZ},0);
			centerLine = new Instruction(Instruction.LINE,new double[]{xrightZ,ytopZ},new double[]{xleftZ,ybottomZ},0);
			bottomLine = new Instruction(Instruction.LINE,new double[]{xleftZ,ybottomZ},new double[]{xrightZ,ybottomZ},0);
			alist.add(topLine);
			alist.add(centerLine);
			alist.add(bottomLine); 
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
