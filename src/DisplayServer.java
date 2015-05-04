import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

public class DisplayServer extends JPanel implements KeyListener {
	private static int historySkip = 5;
	private static final long serialVersionUID = 1l;

	protected double gvX [], gvY[], gvTheta[];
	protected int numVehicles = 0;
	protected int shapeX[], shapeY[];
	protected JFrame frame;
	protected NumberFormat format = new DecimalFormat("#####.##");
	protected String myHostname; 
	protected Color[] my_colors = new Color[] {Color.black,Color.blue,Color.cyan,
			Color.green, Color.magenta, 
			Color.orange, Color.pink,
			Color.red, Color.yellow,
			Color.darkGray};
	public class History {
		History() {
			myX = new double[100000];
			myY = new double[100000];
			myNumPoints = 0;
			loopHistory = 0;
			trueHistoryLength = 0;
		}
		public double [] myX;
		public double [] myY;
		int myNumPoints;
		int trueHistoryLength;
		int loopHistory;
	}

	History [] histories;
	boolean trace = false;

	public synchronized void clear() {
		if (histories !=null){
			for (int i = 0; i < histories.length; i++) {
				histories[i].myNumPoints = 0;
				histories[i].loopHistory = 0;
				histories[i].trueHistoryLength = 0;
			}
		}
	}

	public synchronized void resetHistories(int numVehicles) {
		histories = new History[numVehicles];
		for (int i = 0; i < numVehicles; i++)
			histories[i] = new History();
	}


	public class MessageListener extends Thread {
		public BufferedReader my_client; 
		public DisplayServer my_display;
		public MessageListener(Socket client, DisplayServer display) {
			my_display = display; 
			try {
				my_client = new BufferedReader
						(new InputStreamReader(client.getInputStream()));
			}
			catch (IOException e) {
				System.err.println("Very weird IOException in creating the BufferedReader");
				System.err.println(e);
				System.exit(-1);
			}
		}
		public void run() {
			try {
				while (true) {
					String message = my_client.readLine();
					if (message == null){
						System.out.println("EOF reached!");
						return; //EOF reached	
					}

					StringTokenizer st = new StringTokenizer(message);
					String tok = st.nextToken();	  
					if (tok.equals("clear")) {
						my_display.clear();
					}
					else if (tok.equals("traceon")) {
						synchronized (my_display) {
							my_display.trace = true;
						}
					} else if (tok.equals("traceoff")) {
						synchronized (my_display) {
							my_display.trace = false;
						}
					} else if (tok.equals("close")){
						return;
					} else {
						synchronized (my_display) {
							if (my_display.numVehicles != Integer.parseInt(tok)) {
								my_display.numVehicles = Integer.parseInt(tok);
								my_display.gvX = new double[my_display.numVehicles];
								my_display.gvY = new double[my_display.numVehicles];
								my_display.gvTheta = new double[my_display.numVehicles];
								my_display.resetHistories(numVehicles);
							}
							for (int i = 0; i < my_display.numVehicles; i++) {
								tok = st.nextToken();
								my_display.gvX[i] = Double.parseDouble(tok);
								tok = st.nextToken();
								my_display.gvY[i] = Double.parseDouble(tok);
								tok = st.nextToken();
								my_display.gvTheta[i] = Double.parseDouble(tok);
								if (trace) {
									if (histories[i].trueHistoryLength % historySkip == 0){


										int n;
										if (histories[i].myNumPoints == histories[i].myX.length) {
											n = 0;                                                                    
											histories[i].myNumPoints = 0;
											histories[i].loopHistory = 1;
										} else {
											n = histories[i].myNumPoints;
											histories[i].myNumPoints++;
										}
										histories[i].myX[n] = my_display.gvX[i];
										histories[i].myY[n] = my_display.gvY[i];
									}
									histories[i].trueHistoryLength++;
								} 
							} 
						}  
					}
					my_display.repaint();
				}
			}
			catch (IOException e) {
			}
			return; 
		}
	}

	public DisplayServer (String hostname) {
		myHostname = hostname;
		shapeX = new int[1];
		shapeY = new int[1];

		//Vehicle Shape

		shapeX[0] = 10;  shapeY[0] = 0;


		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				startGraphics();
			}
		});
	}

	public void startGraphics()
	{
		JFrame.setDefaultLookAndFeelDecorated(true);

		frame = new JFrame("Concurrent Typewriter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = frame.getContentPane();
		//container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		container.setLayout(new BorderLayout());

		setOpaque(true);   
		setFocusable(true);
		setMinimumSize(new Dimension(1000,500));
		setPreferredSize(new Dimension(1000,500));
		addKeyListener(this);
		container.add(this,BorderLayout.WEST);
		setVisible(true);

		frame.pack();
		frame.setVisible(true);    
	}

	public void keyPressed(KeyEvent e) { }

	public void keyReleased(KeyEvent e) { }

	public void keyTyped(KeyEvent e)
	{
		switch (e.getKeyChar()) {
		case 'q':
		case 'Q':
			System.exit(0);
		}
	}

	protected synchronized void drawVehicles(Graphics g) {
		g.setColor(Color.black);

		// This chunk of code just translate and rotates the shape.

		for (int j = 0; j < numVehicles; j++) {
			if (j < my_colors.length){
				g.setColor(my_colors[j]);
			}else{
				g.setColor(my_colors[my_colors.length-1]);
			}
			int drawX[] = new int[1];
			int drawY[] = new int[1];


			double x = gvX[j]*5;
			double y = gvY[j]*5;
			double th = gvTheta[j];
			drawX[0] = (int)(x+Math.cos(th)*shapeX[0]+Math.sin(th)*shapeY[0]);
			drawY[0] = (int)(y+Math.sin( th)*shapeX[0]-Math.cos(th)*shapeY[0]);
			drawY[0] = 500- drawY[0];

			g.drawPolygon(drawX, drawY, 1);
		}
	}

	protected synchronized void drawHistories(Graphics g) {
		g.setColor(Color.black);

		// This chunk of code just translate and rotates the shape.

		for (int j = 0; j < numVehicles; j++) {
			if (j < my_colors.length){
				g.setColor(my_colors[j]);
			}else{
				g.setColor(my_colors[my_colors.length-1]);
			}
			int drawX[]; int drawY[];
			if (histories[j].loopHistory == 0){
				drawX = new int[histories[j].myNumPoints];
				drawY = new int[histories[j].myNumPoints];
			}
			else{

				drawX = new int[histories[j].myX.length];
				drawY = new int[histories[j].myY.length];
			}
			for (int i = 0; i < drawX.length;i++){
				// We scale the x and y by 5, since the bounds on X and Y are 100x100
				// but our windows is 500x500.

				double x = histories[j].myX[i]*5;
				double y = histories[j].myY[i]*5;
				drawX[i] = (int)(x);
				drawY[i] = 500- (int)y;
			}
			g.drawPolygon(drawX, drawY, drawX.length);
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g); //paints the background and image

		Rectangle bounds = this.getBounds();
		g.setColor(Color.white);
		g.fillRect(0, 0, bounds.width, bounds.height);

		g.setColor(Color.black);
		g.drawString("Display running on "+myHostname, 830,495);
		if (trace) 
			drawHistories(g);
		drawVehicles(g);
	}

	protected void addClient(Socket client) {
		MessageListener l = new MessageListener(client, this);
		l.start();
	}

	public static void main(String [] argv) {
		try {
			ServerSocket s = new ServerSocket(5065);
			s.setReuseAddress(true);      
			if (!s.isBound())
				System.exit(-1);
			String address = GeneralInetAddress.getLocalHost().getHostAddress();
			DisplayServer d = new DisplayServer(address);
			do {
				Socket client = s.accept();
				d.addClient(client);
			} while (true);
		} 
		catch (IOException e) {
			System.err.println("I couldn't create a new socket.\n"+
					"You probably are already running DisplayServer.\n");
			System.err.println(e);
			System.exit(-1);
		}
	}

}
