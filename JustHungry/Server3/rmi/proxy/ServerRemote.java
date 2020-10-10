package rmi.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;
 
public interface ServerRemote extends Remote {
    public String getUsername() throws RemoteException;
    public String getServerIP(int x) throws RemoteException;
    public int getServerPort(int x) throws RemoteException;
    
    public String listOptions() throws RemoteException;
    public String listNormalOptions() throws RemoteException;
    public String listVegetarianOptions() throws RemoteException;
    public String listOrders() throws RemoteException;
    
    public String countryFromPostCode(String postCode) throws RemoteException;
    public String parishFromPostCode(String postCode) throws RemoteException;
    public String adminDistrictFromPostCode(String postCode) throws RemoteException;
    
    public void addOrder(String postCode, String foodItem) throws RemoteException;
    public void addOrderUpdate(String postCode, String foodItem) throws RemoteException;
    public void removeOrder(String postCode) throws RemoteException;
    public void removeOrderUpdate(String postCode) throws RemoteException;
    
    public String isAlive() throws RemoteException;
    	
}