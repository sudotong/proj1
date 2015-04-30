package proj1;

public class Instruction {
	
	public static final String CIRCLE = "CIRCLE";
	public static final String LINE = "LINE";
	private double[] start = new double[2];
	private double[] end = new double[2];
	private double rotVel = 0;
	
	private String type = null;
	
	public Instruction(String type, double[] start, double[] end, double rotVel) { 
		if (type.toUpperCase().equals(this.CIRCLE)){
			this.type = this.CIRCLE;
		} else if (type.toUpperCase().equals(this.LINE)){
			this.type = this.LINE;
		}
		this.start = start;
		this.end = end;
		this.rotVel = rotVel;
	}
	
}
