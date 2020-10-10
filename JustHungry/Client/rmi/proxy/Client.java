package rmi.proxy;

import java.net.*;
import java.io.*;
 

public class Client {
	
	 public static int getSocketPort() {
			String socketPort = "";
			java.util.Properties configProps = new java.util.Properties();
			try {
				configProps.load(new java.io.FileInputStream("config.properties"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			socketPort = (String) configProps.get("Client_Port");
			return Integer.valueOf(socketPort);
	} 
 
	 public static void main(String[] args) {
	        String hostname = "127.0.0.1";
	        //int port = 33334;
	        int port = getSocketPort();
	 
	        try 
	        	{
	        	Socket socket = new Socket(hostname, port);
	 
	        	DataInputStream din = new DataInputStream(socket.getInputStream());  
	        	DataOutputStream dout = new DataOutputStream(socket.getOutputStream());  
	        	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
	        	
	        	BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	            writer.write("You are now connected to the server.");
	            
	            System.out.println("Do you want to continue/stop?");

	            String str="";  
	            while(!str.equals("stop")){  
		            str = br.readLine();  
		            dout.writeUTF(str);  //Request sent to the server
		            //System.out.println("Server says: "+ din.readUTF());  //Response received from server
		            String response = din.readUTF();
		            System.out.println("Server says: " + response);
		            
		            if (response.equals("Order placed") || response.equals("Order retrieved") || response.equals("unknown command")) {
		            	System.out.println("Do you want to continue/stop?");
		            }
	            }  
	            
	            din.close();
	            dout.close();
	            socket.close();
	            //System.out.println("You have connected to the server.");
	            System.out.println("Session finished.");
	 
	        } catch (Exception ex) {
	 
	            System.out.println("I/O error: " + ex.getMessage());
	        }
	    }
}