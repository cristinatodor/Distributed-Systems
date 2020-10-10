package rmi.proxy;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;
 
public class Server3 extends UnicastRemoteObject implements ServerRemote {
	private ArrayList <String> options;
	private ArrayList <String> normalOptions;
    private ArrayList <String> vegetarianOptions;
	private StringBuilder sb;
	private Hashtable <String, String> orders;
	
    protected Server3() throws RemoteException { 
    	super();
    	
    	options = new ArrayList();
		options.add("normal");
		options.add("vegetarian");
		
		normalOptions = new ArrayList();
		normalOptions.add("Fajitas");
		normalOptions.add("Steak");
		normalOptions.add("Fish&Chips");
		normalOptions.add("Pasta");
		
		vegetarianOptions = new ArrayList(); 
		vegetarianOptions.add("Risotto");
		vegetarianOptions.add("Falafel");
		vegetarianOptions.add("Mac&Cheese");
		vegetarianOptions.add("Noodles");
		
		orders = new Hashtable();
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

    public String isAlive() throws RemoteException {
    	return "true";
    }
    
    public String getUsername() throws RemoteException {
        return System.getProperty("user.name");
    }
    
    public String listOptions() throws RemoteException {
    	sb = new StringBuilder();
    	
    	for (String opt : options) {
    	    sb.append(opt);
    	    sb.append("\t");
    	}
    	
    	return sb.toString();
    }
    
    public String listNormalOptions() throws RemoteException {
    	sb = new StringBuilder();
    	
    	for (String opt : normalOptions) {
    	    sb.append(opt);
    	    sb.append("\t");
    	}
    	
    	return sb.toString();
    }
    
    public String listVegetarianOptions() throws RemoteException{
    	sb = new StringBuilder();
    	
    	for (String opt : vegetarianOptions) {
    	    sb.append(opt);
    	    sb.append("\t");
    	}
    	
    	return sb.toString();
    }
    
    public String listOrders() throws RemoteException {
    	return orders.toString();
    }
    
    public String countryFromPostCode(String postCode) throws RemoteException {
    	AddressFinder af =   new AddressFinder();
    	String country =  af.getCountry(postCode);
    	
    	return country;
    }
    
    public String parishFromPostCode(String postCode) throws RemoteException {
    	AddressFinder af =   new AddressFinder();
    	String parish =  af.getParish(postCode);
    	
    	return parish;
    }
    
    public String adminDistrictFromPostCode(String postCode) throws RemoteException {
    	AddressFinder af =   new AddressFinder();
    	String adminDistrict =  af.getAdminDistrict(postCode);
    	
    	return adminDistrict;
    }
    
    public void addOrder(String postCode, String foodItem) throws RemoteException {
    	orders.put(postCode, foodItem);
    	
    	try {
    		ServerRemote serverRemote1 = (ServerRemote) Naming.lookup("rmi://" + this.getServerIP(1) + ":" + this.getServerPort(1) + "/RemoteAccess");
    		serverRemote1.addOrderUpdate(postCode, foodItem);
    		System.out.println("Server1 successfully updated");
    		
    	} catch (Exception e) {
			//e.printStackTrace();
    		System.out.println("Server1 unavailable for adding order");
		}
    	

    	try {
    		ServerRemote serverRemote2 = (ServerRemote) Naming.lookup("rmi://" + this.getServerIP(2) + ":" + this.getServerPort(2) + "/RemoteAccess");
    		serverRemote2.addOrderUpdate(postCode, foodItem);
    		System.out.println("Server2 successfully updated");
    		
    	} catch (Exception e) {
			//e.printStackTrace();
    		System.out.println("Server2 unavailable for adding order");
		}
    	
    }
    public void addOrderUpdate(String postCode, String foodItem) throws RemoteException {
    	orders.put(postCode, foodItem);
    }
    
    public void removeOrder(String postCode) throws RemoteException {
    	orders.remove(postCode);
    	
    	try {
    		ServerRemote serverRemote1 = (ServerRemote) Naming.lookup("rmi://" + this.getServerIP(1) + ":" + this.getServerPort(1) + "/RemoteAccess");
    		serverRemote1.removeOrderUpdate(postCode);
    		System.out.println("Server1 successfully updated");
    		
    	} catch (Exception e) {
			//e.printStackTrace();
    		System.out.println("Server1 unavailable for removing order");
		}
    	
    	try {    		
    		ServerRemote serverRemote2 = (ServerRemote) Naming.lookup("rmi://" + this.getServerIP(2) + ":" + this.getServerPort(2) + "/RemoteAccess");
    		serverRemote2.removeOrderUpdate(postCode);
    		System.out.println("Server2 successfully updated");
    		
    	} catch (Exception e) {
			//e.printStackTrace();
    		System.out.println("Server2 unavailable for removing order");
		}
    	
    }
    
    public void removeOrderUpdate(String postCode) throws RemoteException {
    	orders.remove(postCode);
    }
 
    public static void main (String[] args) {
        try {
            Server3 localServer = new Server3();
		    // Get registry
		    Registry registry = LocateRegistry.getRegistry(localServer.getServerIP(3), localServer.getServerPort(3));
            registry.rebind("RemoteAccess", localServer);
            System.out.println("Server started: " + localServer.getServerPort(3));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}