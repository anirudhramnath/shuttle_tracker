package office;
        
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
        
public class OfficeServer {
        
    public OfficeServer() {}
 
    public static void main(String args[]) throws RemoteException, AlreadyBoundException, NotBoundException {
        
       
            OfficeImpl officeImpl = new OfficeImpl();

            // Get local registry instance
            Registry registry = LocateRegistry.getRegistry(1099);
           
            String[] boundNames = registry.list();
            
           // Checking if the name officeRemote is already bound, if it is we are unbinding it
            
            for(String name : boundNames){
            	if(name.equals("officeRemote")){
            		registry.unbind("officeRemote");
            		break;
            	}
            }
            
            registry.bind("officeRemote", officeImpl); 

            System.out.println("Server ready");
        
    }
}