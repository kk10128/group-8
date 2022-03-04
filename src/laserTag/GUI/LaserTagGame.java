package laserTag.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LaserTagGame extends JFrame {
  
  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

  public static void main(String[] args) {
  	new LaserTagGame();
  }
  
  public LaserTagGame() {
    // Add window closing listener
    createCloseListener();
    
  	// Show Splash Screen
  	JPanel SplashScreen = new JPanel(new BorderLayout());
  	SplashScreen.add(new JLabel(new ImageIcon("logo.jpg")), BorderLayout.CENTER);
  	setContentPane(SplashScreen);
  	setBounds(50, 50, 643, 410);
  	setVisible(true);
  	setTitle("Splash Screen");
  	
  	// Sleep while showing splash screen
  	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
  	// Add window closing listener
  	
  	// Establish TCP connection to player data receiver
  	try {
      startConnection("localhost", 8686);
    } catch (IOException e) {
      System.out.print("Unable to establish connection to player data receiver");
      e.printStackTrace();
    }
  	
  	// Show entry terminal screen
  	setContentPane(new EntryTerminal(this));
  	setVisible(true);
  	setTitle("Entry Terminal");
  }
  
  /**
   * Creates a socket and attempts to connect to the data receiver.
   */
  public void startConnection(String ip, int port) throws IOException {
    clientSocket = new Socket(ip, port);
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }
  
  /**
   * Sends a message to the data receiver.
   * @param message
   * @return The data receiver's response
   */
  public String sendPlayerData(String message) throws IOException {
    out.println(message);
    String resp = in.readLine();
    return resp;
  }
  
  /**
   * Closes the socket
   */
  public void stopConnection() throws IOException {
    in.close();
    out.close();
    clientSocket.close();
  }
  
  /**
   * Creates a listener that closes the socket when the program is closed.
   * Before closing, a message is sent warning the data receiver that the connection will close.
   */
  public void createCloseListener() {
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        try {
          sendPlayerData("close");
          stopConnection();
          System.exit(0);
        } catch (IOException e1) {
          System.out.println("Unable to close connection with data receiver");
          e1.printStackTrace();
        }
      }
    });
  }

}
