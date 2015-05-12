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
			double rotVelD = -this.vehicleSpeed/radiusD;
			startTheta =0;
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
			centerLine = new Instruction(Instruction.LINE,new double[]{xleftE,ycenterE},new double[]{xendE*3/4,ycenterE},0);
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
			centerLine = new Instruction(Instruction.LINE,new double[]{xleftF,ycenterF},new double[]{xendF*3/4,ycenterF},0);
			alist.add(leftLine);
			alist.add(topLine);
			alist.add(centerLine);
			break;
		case 'G':
			double xstartG = this.xBoxSize/2;
			double ystartG = this.yBoxSize;
			double bubbleRadiusG = this.xBoxSize/2;
			double xendG = this.xBoxSize;
			double yendG = this.yBoxSize/2;
			double xlineendG=this.xBoxSize;
			double rotVelG = this.vehicleSpeed/bubbleRadiusG;
			startTheta = Math.PI*-1;
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{xstartG,ystartG},new double[]{xendG,yendG},rotVelG,startTheta);
			centerLine = new Instruction(Instruction.LINE,new double[]{xstartG,yendG},new double[]{xlineendG,yendG},0);
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
			double xendJ = xcenterJ - 2*radiusJ-1.5;
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
			double ymiddleM=(ytopM+ybottomM)/2;
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftM,ytopM},new double[]{xleftM,ybottomM},0);
			centerLine = new Instruction(Instruction.LINE,new double[]{xleftM,ytopM},new double[]{xcenterM,ymiddleM},0);
			centerLine2 = new Instruction(Instruction.LINE,new double[]{xrightM,ytopM},new double[]{xcenterM,ymiddleM},0);
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
			double xrightR = this.xBoxSize*5/8;
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
			double bubbleRadiusS = this.yBoxSize/4;
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
			double yendU = this.yBoxSize*2/8;
			double xrightU = this.xBoxSize*3/4;			
			double radiusU = this.yBoxSize*2/8;
			double rotVelU = this.vehicleSpeed/radiusU;
			startTheta = Math.PI*-1/2;
			rightLine = new Instruction(Instruction.LINE,new double[]{xrightU,ytopU},new double[]{xrightU,yendU},0);
			bottomBubble = new Instruction(Instruction.CIRCLE, new double[]{xleftU,yendU},new double[]{xrightU,yendU},rotVelU,startTheta);
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
			double ymiddleW=(ybottomW+ytopW)/2;
			leftLine = new Instruction(Instruction.LINE,new double[]{xleftW,ytopW},new double[]{xleftW,ybottomW},0);
			centerLine = new Instruction(Instruction.LINE,new double[]{xleftW,ybottomW},new double[]{xcenterW,ymiddleW},0);
			centerLine2 = new Instruction(Instruction.LINE,new double[]{xrightW,ybottomW},new double[]{xcenterW,ymiddleW},0);
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
                
		case '0':
                double xzero = this.xBoxSize/2;
                double yzero = this.yBoxSize;
                double bubbleRadiuszero = this.xBoxSize/2;
                double rotVelzero = -1*this.vehicleSpeed/bubbleRadiuszero;
                double startThetazero = Math.PI*0;
                topBubble = new Instruction(Instruction.CIRCLE,new double[]{xzero,yzero},new double[]{xzero,yzero},rotVelzero,startThetazero);
                alist.add(topBubble);
			break;
                
		case '1':
                double ytopone = this.yBoxSize*7/8;
                double xcenterone = this.xBoxSize/2;
                double ybottomone = this.yBoxSize*1/8;
                centerLine = new Instruction(Instruction.LINE,new double[]{xcenterone,ytopone},new double[]{xcenterone,ybottomone},0);
                
                alist.add(centerLine);
                bottomLine = new Instruction(Instruction.LINE,new double[]{.45*this.xBoxSize,ybottomone},new double[]{.55*this.xBoxSize,ybottomone},0);
                alist.add(bottomLine);
                topLine=new Instruction(Instruction.LINE,new double[]{xcenterone,ytopone},new double[]{xcenterone*.9,ytopone*.9},0);
                alist.add(topLine);
			break;
                
		case '2':
                double ystarttwo = this.yBoxSize * .6;
                double xstarttwo = this.xBoxSize * 1/6;
                
                double xendtwo = this.xBoxSize*5/6;
                topBubble = new Instruction(Instruction.CIRCLE, new double[]{xstarttwo,ystarttwo}, new double[] {xendtwo, ystarttwo},  -1*this.vehicleSpeed/(this.xBoxSize*4/6/2), Math.PI/2);
                alist.add(topBubble);
                double ystart1two = ystarttwo;
                double xstart1two = this.xBoxSize*5/6;
                double yend1two = this.yBoxSize*1/8;
                double xend1two = this.xBoxSize/6;
                leftLine = new Instruction(Instruction.LINE, new double[]{xstart1two, ystart1two}, new double[]{xend1two, yend1two},0);
                alist.add(leftLine);
                double ystart2two = this.yBoxSize*1/8;;
                double xstart2two=this.xBoxSize/6;
                double xend2two=this.xBoxSize*5/6;
                double yend2two=this.yBoxSize*1/8;;
                bottomLine = new Instruction(Instruction.LINE, new double[]{xstart2two, ystart2two}, new double[]{xend2two, yend2two},0);
                alist.add(bottomLine);
                

			break;
		case '3':
                double xstartthree = this.xBoxSize/4;
                double ystartthree = this.yBoxSize*3/4;
                double xmidthree = this.xBoxSize/2;
                double ymidthree = this.yBoxSize/2;
                double yendthree = this.yBoxSize/4;
                double xendthree = this.xBoxSize/4;
                topBubble = new Instruction(Instruction.CIRCLE, new double[]{xstartthree,ystartthree}, new double[] {xmidthree, ymidthree}, -1*this.vehicleSpeed/(this.xBoxSize/4), Math.PI/2);
                bottomBubble =new Instruction(Instruction.CIRCLE, new double[]{xmidthree,ymidthree}, new double[] {xendthree, yendthree},  -1*this.vehicleSpeed/(this.xBoxSize/4), 0);
                alist.add(topBubble);
                alist.add(bottomBubble);

			break;
		case '4':
                double ytopone4 = this.yBoxSize;
                double xcenterone4 = this.xBoxSize/2;
                double ybottomone4 = 0;
                centerLine = new Instruction(Instruction.LINE,new double[]{xcenterone4,ytopone4},new double[]{xcenterone4,ybottomone4},0);
                
                alist.add(centerLine);
                double xleft4 = 0;
                double yleft4=this.yBoxSize/2;
                leftLine = new Instruction(Instruction.LINE, new double[]{xcenterone4, ytopone4}, new double[]{xleft4,yleft4},0);
                alist.add(leftLine);
                double xright4 = this.xBoxSize;
                bottomLine = new Instruction(Instruction.LINE, new double[]{xleft4, yleft4}, new double[] {xright4*.75,yleft4},0);
                alist.add(bottomLine);

			break;
		case '5':
                double ytop5 =this.yBoxSize* 7/8;
                double xtopright5 = this.xBoxSize*3/4;
                double xtopleft5=this.xBoxSize*1/4;
                double ymid5 = this.yBoxSize/2;
                topLine = new Instruction(Instruction.LINE, new double[] {xtopright5, ytop5}, new double[] {xtopleft5, ytop5},0);
                rightLine=new Instruction(Instruction.LINE, new double[] {xtopleft5, ytop5}, new double[] {xtopleft5, ymid5},0);
                double xend5 = .25*this.xBoxSize;
                double yend5 = .0858*this.yBoxSize;
                bottomBubble = new Instruction(Instruction.CIRCLE, new double[] {xtopleft5, ymid5}, new double[] {xend5, yend5}, -1*this.vehicleSpeed/(this.xBoxSize*.2929), Math.PI/4);
                alist.add(topLine);
                alist.add(rightLine);
                alist.add(bottomBubble);

			break;
		case '6':
			double x6 = this.xBoxSize/2;
			double y_start6 = this.yBoxSize;
			double y_end6 = 0;
			double y_center6 = this.yBoxSize/2;
			double bubbleRadius_top6 = this.xBoxSize/2;
			double bubbleRadius_bottom6 = this.xBoxSize/4;
			double rotVel_top6 = this.vehicleSpeed/bubbleRadius_top6;
			double rotVel_bottom6 = this.vehicleSpeed/bubbleRadius_bottom6;
			startTheta = Math.PI;
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{x6,y_start6},new double[]{x6,y_end6},rotVel_top6,startTheta);
			bottomBubble = new Instruction(Instruction.CIRCLE,new double[]{x6,y_center6},new double[]{x6,y_center6},rotVel_bottom6,startTheta);
			alist.add(topBubble);
			alist.add(bottomBubble);
			break;
		case '7':
			double xleft7 = this.xBoxSize*1/4;
			double ytop7 = this.yBoxSize*7/8;
			double xcenter7 = this.xBoxSize/2;
			double ybottom7 = this.yBoxSize*1/8;
			double xright7 = this.xBoxSize*3/4;
			centerLine = new Instruction(Instruction.LINE,new double[]{xright7,ytop7},new double[]{xcenter7,ybottom7},0);
			topLine = new Instruction(Instruction.LINE,new double[]{xleft7,ytop7},new double[]{xright7,ytop7},0);
			alist.add(centerLine);
			alist.add(topLine); 
			break;
		case '8':
			double xleft8 = this.xBoxSize*1/4;
			double yleft_start8 = this.yBoxSize;
			double ymiddle8 = this.yBoxSize/2;
			double bubbleRadius8 = (this.xBoxSize*3/4 - xleft8)/2;
			double rotVel8 = -1*this.vehicleSpeed/bubbleRadius8; //counterclockwise is positive
			startTheta = Math.PI*0;
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{xleft8,yleft_start8},new double[]{xleft8,yleft_start8},rotVel8,startTheta);
			bottomBubble = new Instruction(Instruction.CIRCLE,new double[]{xleft8,ymiddle8},new double[]{xleft8,ymiddle8},rotVel8,startTheta);
			alist.add(topBubble);
			alist.add(bottomBubble);
			break;
		case '9':
			double xleft9 = this.xBoxSize*1/4;
			double yleft_start9 = this.yBoxSize;
			double bubbleRadius9 = (this.xBoxSize*3/4 - xleft9)/2;
			double rotVel9 = -1*this.vehicleSpeed/bubbleRadius9; //counterclockwise is positive
			double xright9 = bubbleRadius9 + xleft9;
			double yright9 = yleft_start9-bubbleRadius9;
			double yend9 = this.yBoxSize*1/8;
			startTheta = Math.PI*0;
			topBubble = new Instruction(Instruction.CIRCLE,new double[]{xleft9,yleft_start9},new double[]{xleft9,yleft_start9},rotVel9,startTheta);
			centerLine = new Instruction(Instruction.LINE, new double[]{xright9,yright9}, new double[]{xright9, yend9}, 0);
			alist.add(topBubble);
			alist.add(centerLine);
			break;
		case '.':
			double xleft_per = this.xBoxSize*1/2;
			double yleft_start_per = this.yBoxSize*1/3;
			double bubbleRadius_per = this.xBoxSize/8;
			double rotVel_per = -1*this.vehicleSpeed/bubbleRadius_per; //counterclockwise is positive
			startTheta = Math.PI*0;
			bottomBubble = new Instruction(Instruction.CIRCLE,new double[]{xleft_per,yleft_start_per},new double[]{xleft_per,yleft_start_per},rotVel_per,startTheta);
			alist.add(bottomBubble);
			break; 

		case ',': 
			double ymiddle_start_com = this.yBoxSize/3;
			double ymiddle_end_com = this.yBoxSize/16;
			double xmiddle_start_com = this.xBoxSize*9/16;
			double xmiddle_end_com = this.xBoxSize*6/16;					
			centerLine = new Instruction(Instruction.LINE,new double[]{xmiddle_start_com,ymiddle_start_com},new double[]{xmiddle_end_com,ymiddle_end_com},0);
			alist.add(centerLine);
			break; 

		case '-':
			double ymiddle_dash = this.yBoxSize/2;
			double xmiddle_start_dash = this.xBoxSize/4;
			double xmiddle_end_dash = this.xBoxSize*3/4;					
			centerLine = new Instruction(Instruction.LINE,new double[]{xmiddle_start_dash,ymiddle_dash},new double[]{xmiddle_end_dash,ymiddle_dash},0);
			alist.add(centerLine);
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
