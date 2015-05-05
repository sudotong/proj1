
public class Instruction {
	
	public static final String CIRCLE = "CIRCLE";
	public static final String LINE = "LINE";
	private double[] start = new double[2];
	private double[] end = new double[2];
	private double rotVel = 0;
	private double startTheta = 0;
	private String type = null;
	
	public Instruction(String type, double[] start, double[] end, double rotVel,double startTheta) { 
		if (type.toUpperCase().equals(this.CIRCLE)){
			this.type = this.CIRCLE;
			this.startTheta = startTheta;
		} else if (type.toUpperCase().equals(this.LINE)){
			this.type = this.LINE;
			this.startTheta = Math.atan2((end[1] - start[1]), (end[0]-start[0]));
		}
		this.start = start;
		this.end = end;
		this.rotVel = rotVel;
		
	}
	
	public Instruction(String type, double[] start, double[] end, double rotVel) { 
		if (type.toUpperCase().equals(this.CIRCLE)){
			this.type = this.CIRCLE;
			this.startTheta = Double.NEGATIVE_INFINITY;
		} else if (type.toUpperCase().equals(this.LINE)){
			this.type = this.LINE;
			this.startTheta = Math.atan2((end[1] - start[1]), (end[0]-start[0]));
		}
		this.start = start;
		this.end = end;
		this.rotVel = rotVel;
		
	}

	public double[] getStart() {
		return start.clone();
	}
	public double[] getEnd() {
		return end.clone();
	}
	public double getRotVel() {
		return Double.valueOf(rotVel);
	}
	public String getType() {
		return type+"";
	}
	
	public double getStartTheta() {
		return Double.valueOf(startTheta);
	}

}
