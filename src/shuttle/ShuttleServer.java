package shuttle;
        
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
        
public class ShuttleServer {
        
    public ShuttleServer() {}
 
    public static void main(String args[]) throws RemoteException, AlreadyBoundException, NotBoundException {
        
       
            ShuttleImpl shuttleImpl = new ShuttleImpl();

            // Get local registry instance
            Registry registry = LocateRegistry.getRegistry(1099);
           
            String[] boundNames = registry.list();
            
         // Checking if the name remoteShuttle is already bound, if it is we are unbinding it
            for(String name : boundNames){
            	if(name.equals("remoteShuttle")){
            		registry.unbind("remoteShuttle");
            		break;
            	}
            }
            
            registry.bind("remoteShuttle", shuttleImpl); 

            System.out.println("Server ready");
        
    }
}