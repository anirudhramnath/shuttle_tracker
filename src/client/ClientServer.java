package client;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientServer{
	
	public ClientServer() {}

	public static void main(String args[]) throws RemoteException, AlreadyBoundException, NotBoundException {

		ClientImpl clientImpl = new ClientImpl();

        // Get local registry instance
        Registry registry = LocateRegistry.getRegistry(1099);
       
        String[] boundNames = registry.list();
        
        // Checking if the name clientRemote is already bound, if it is we are unbinding it
        for(String name : boundNames){
        	if(name.equals("clientRemote")){
        		registry.unbind("clientRemote");
        		break;
        	}
        }
        
        registry.bind("clientRemote", clientImpl); 
        
        System.out.println("Server ready");
        clientImpl.doClientFunction();
	}

}