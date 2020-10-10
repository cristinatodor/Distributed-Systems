package rmi.proxy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class FrontEndThread extends Thread{
	
	
	private Socket socket;
	private ServerRemote serverRemote;
	
	private String currentPostCode;
	private String currentCountry;
	private String currentParish;
	private String currentAdminDistrict;
	private String currentFoodItem;
	private String options;
	private String normalOptions;
	private String vegetarianOptions;

    public FrontEndThread(Socket clientSocket) {
        this.socket = clientSocket;
    }
    
    

    public void run() {
        try {
        	DataInputStream din=new DataInputStream(socket.getInputStream());  
            DataOutputStream dout=new DataOutputStream(socket.getOutputStream());  
                         
            
            String str = "";
            String response = "";
	        while (!str.equals("stop")) {
                str=din.readUTF();  
                System.out.println("Client says: " + str);  
                setServerRemote();   
                response = processCommand(str);
                dout.writeUTF(response);
                dout.flush();
	        }
            socket.close();
            
            
            String orders = serverRemote.listOrders();
            System.out.println("The orders are: " + orders);
            
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }
    
    private void setServerRemote() throws MalformedURLException, RemoteException, NotBoundException {
    	 serverRemote = (ServerRemote) Naming.lookup("rmi://" + this.getServerIP(1) + ":" + this.getServerPort(1) + "/RemoteAccess");        
         try { 
         	if (serverRemote.isAlive().equals(null)) {}
         } catch (Exception e1) {
         		serverRemote = (ServerRemote) Naming.lookup("rmi://" + this.getServerIP(2) + ":" + this.getServerPort(2) + "/RemoteAccess"); 
         		try {
         			if (serverRemote.isAlive().equals(null)) {}
         		} catch (Exception e2) {
         			serverRemote = (ServerRemote) Naming.lookup("rmi://" + this.getServerIP(3) + ":" + this.getServerPort(3) + "/RemoteAccess"); 
         			try {
         				if (serverRemote.isAlive().equals(null)) {};
         			} catch (Exception e3) {
         				System.out.println("Connection to server failed");
         			}
         		}
         }
		
	}



	private String processCommand(String command) {
    	String response = "";
    	
    	
	    if (command.startsWith("PC")) {
	    		currentPostCode = command.substring(3);
	    		getCountry(currentPostCode);
	    		getParish(currentPostCode);
	    		getAdminDistrict(currentPostCode);
	    		response = "The address is: " + currentParish + ", " + currentAdminDistrict + ", " + currentCountry  + "\nDo you want to place/retrieve an order?";
	    }
	    else {
	    	if (command.startsWith("FI")) {
	    		currentFoodItem = command.substring(3);
	    		placeOrder();
	    		response = "Order placed";
	    	} 
	    	else {
			    switch (command) {
			    	case "continue":
			    			response = "Enter postcode (must start with PC): ";
			    			break;
			    		
			    	case "place":
			    		getOptions();
			    		response = "Select option: " + options; 
			    		break;
			    	
			    	case "normal":
			    		getNormalOptions();
			    		response = "Select food item (must start with FI): " + normalOptions;
			    		break;
			    	
			    	case "vegetarian":
			    		getVegetarianOptions();
			    		response = "Select food item (must start with FI): " + vegetarianOptions;
			    		break;
			    		
			    	case "retrieve":
			    		retrieveOrder();
			    		response = "Order retrieved";
			    		break;
			    			
			    	case "stop":
			    		response = "connection terminated";
			    		break;
			    			
			    	default:
			    		response = "unknown command";
			    		break;
			    }
	    	}
	    }
	    return response;
	    	
    }
    
    private void getCountry(String currentPostCode) {
    	try {
    		currentCountry = serverRemote.countryFromPostCode(currentPostCode);
    		
    	} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
    }
    
    private void getParish(String currentPostCode) {
    	try {
    		currentParish = serverRemote.parishFromPostCode(currentPostCode);
    		
    	} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
    }
    
    private void getAdminDistrict(String currentPostCode) {
    	try {
    		currentAdminDistrict = serverRemote.adminDistrictFromPostCode(currentPostCode);
    		
    	} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
    }
    
    private void getOptions(){
    	try {
    		options = serverRemote.listOptions();
    		
    	} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
    }
    
    private void getNormalOptions(){
    	try {
    		normalOptions = serverRemote.listNormalOptions();
    		
    	} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
    }
    
    private void getVegetarianOptions(){
    	try {
    		vegetarianOptions = serverRemote.listVegetarianOptions();
    		
    	} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
    }
    
    private void placeOrder() {
    	try {
    		serverRemote.addOrder(currentPostCode, currentFoodItem);
    	} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
    }
    
    private void retrieveOrder() {
    	try {
    		serverRemote.removeOrder(currentPostCode);
    	} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
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

}
