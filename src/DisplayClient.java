import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DisplayClient  {
  PrintWriter output; 
  protected NumberFormat format = new DecimalFormat("#####.##");

  public DisplayClient(String host) {
    InetAddress address;
    try {
      address = InetAddress.getByName(host);
      Socket server = new Socket(host, 5065);
      output = new PrintWriter(server.getOutputStream());
    }
    catch (UnknownHostException e) {
      System.err.println("I can't find a host called "+host+". Are you sure you got the name right?");
      System.err.println(e);
      System.exit(-1);
    }
    catch (IOException e) {
      System.err.println("I can't connect to the DisplayServer running on "+host+".\n");
      System.err.println("Did you remember to start the DisplayServer?");
      System.err.println(e);
      System.exit(-1);
    }
  }

  public void close(){
	  output.println("close");
	  output.flush();
  }
  public void clear() {
    output.println("clear");
  }

  public void traceOn() {
    output.println("traceon");
  }

  public void traceOff() {
    output.println("traceoff");
  }

  public void update(int numVehicles, double gvX[], double gvY[], double gvTheta[])
  {
    StringBuffer message = new StringBuffer();
    message.append(numVehicles);
    message.append(" ");
    for (int i = 0; i < numVehicles; i++) {
      message.append(format.format(gvX[i])+" "+format.format(gvY[i])+" "+
		     format.format(gvTheta[i])+" ");
    }
    output.println(message);
    output.flush();
  }

}
