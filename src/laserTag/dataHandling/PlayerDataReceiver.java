package laserTag.dataHandling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PlayerDataReceiver {
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

  public void start(int port) throws IOException {
    
    // Create sockets
    serverSocket = new ServerSocket(port);
    clientSocket = serverSocket.accept();
    
    // Create IO for sockets
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    
    // Read data continuously from client
    String inputLine;
    while ((inputLine = in.readLine()) != null) {
      if ("close".equals(inputLine)) {
          out.println("closing connection");
          break;
      }
      System.out.println(inputLine);
      out.println("OK");
    }
    stop();
  }

  public void stop() throws IOException {
    in.close();
    out.close();
    clientSocket.close();
    serverSocket.close();
  }
  
  public static void main(String[] args) {
    PlayerDataReceiver server = new PlayerDataReceiver();
    
    // Start server socket
    while(true) {
      try {
        server.start(8686);
      } catch (IOException e) {
        System.out.println("Unable to start server socket.");
        e.printStackTrace();
      }
    }
  }
}
