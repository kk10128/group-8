import javax.swing.JFrame;
import javax.swing.JPanel;

public class LaserTagGame extends JFrame {

  public static void main(String[] args) {
  	new LaserTagGame();
  }
  
  public LaserTagGame() {
  	// Show Splash Screen
  	setContentPane(new JPanel()); //TODO Make splash screen here
  	setBounds(50, 50, 600, 400);
  	setVisible(true);
  	setTitle("Splash Screen");
  	
  	// Sleep while showing splash screen
  	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
  	
  	// Show entry terminal screen
  	setContentPane(new EntryTerminal());
  	setBounds(50, 50, 600, 400);
  	setVisible(true);
  	setTitle("Entry Terminal");
  }

}
