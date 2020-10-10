package rmi.proxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.rmi.Naming;

public class FrontEnd {
    public static void main(String[] args) {
        FrontEnd CFE = new FrontEnd();
                
        CFE.startSocket();
    }
    
    public String getServerIP(int x) {
		String serverIP = "";
		java.util.Properties configProps = new java.util.Properties();
		try {
			configProps.load(new java.io.FileInputStream("config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		serverIP = (String) configProps.get("Server" + x + "_IP");
		return serverIP;
	}

    public int getServerPort(int x) {
		String serverPort = "";
		java.util.Properties configProps = new java.util.Properties();
		try {
			configProps.load(new java.io.FileInputStream("config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		serverPort = (String) configProps.get("Server" + x + "_Port");
		return Integer.valueOf(serverPort);
	} 

    public int getSocketPort() {
		String socketPort = "";
		java.util.Properties configProps = new java.util.Properties();
		try {
			configProps.load(new java.io.FileInputStream("config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		socketPort = (String) configProps.get("Socket_Port");
		return Integer.valueOf(socketPort);
	} 
      
      private void startSocket() {
          //int port = 33333;
    	  int port = getSocketPort();
   
          try {
              System.out.println("FrontEnd is listening on port " + port);
              
              String str="";  
              while(!str.equals("stop")) {
              	ServerSocket serverSocket = new ServerSocket(port);
                  Socket socket = serverSocket.accept();
                  System.out.println("New client connected" + socket.getLocalAddress());
                  new FrontEndThread(socket).start(); //start new Thread for each client
                  serverSocket.close();
              }
              
          } catch (IOException ex) {
              System.out.println("Server exception: " + ex.getMessage());
              ex.printStackTrace();
          }
      }
      
 
}